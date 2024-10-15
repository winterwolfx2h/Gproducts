package com.GestionProduit.Configuration;

import com.GestionProduit.Service.serviceImpl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String ADMIN_ROLE = "ROLE_ADMIN";
  private static final String pr = "/api/produits/**";
  private static final String ct = "/api/categories/**";
  private static final String crt = "/api/cart/**";
  private static final String tx = "/api/tax/**";

  private final CustomUserDetailsService userDetailsService;
  private final JwtRequestFilter jwtRequestFilter;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, pr)
            .permitAll()
            .antMatchers(HttpMethod.GET, ct)
            .permitAll()
            .antMatchers(HttpMethod.GET, crt)
            .permitAll()
            .antMatchers(HttpMethod.GET, tx)
            .permitAll()
            .antMatchers(HttpMethod.POST, pr)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, pr)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, pr)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, ct)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, ct)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, ct)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, crt)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, crt)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, crt)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, tx)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, tx)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, tx)
            .hasRole("ADMIN")
            .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
            .permitAll()
            .anyRequest()
            .authenticated();

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}