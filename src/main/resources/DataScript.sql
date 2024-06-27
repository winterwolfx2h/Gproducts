insert into public.channel (channel_code, description, name) VALUES (0, 'ALL', 'ALL OF THE ABOVE')
ON CONFLICT (channel_code) DO NOTHING;
alter sequence channel_sequence RESTART with 1;

insert into public.entity (entity_code, description, name, channel_code ) VALUES (0, 'ALL', 'ALL OF THE ABOVE', 0 );
ON CONFLICT (entity_code) DO NOTHING;
alter sequence entity_sequence RESTART with 1;

insert into public.product_price_group (product_price_group_code, description, name) VALUES (0, 'ALL', 'ALL OF THE ABOVE');
ON CONFLICT (product_price_group_code) DO NOTHING;
alter sequence productPriceGroup_sequence RESTART with 1;
