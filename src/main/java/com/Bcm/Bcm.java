package com.Bcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages = {"com.Bcm"}, exclude = {DataSourceAutoConfiguration.class})
@EnableCaching
public class Bcm {

	public static void main(String[] args) {

		SpringApplication.run(Bcm.class, args);
	}

}
