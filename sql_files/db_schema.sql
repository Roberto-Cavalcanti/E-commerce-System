create table shop
(
    id         bigint auto_increment primary key,
    cnpj       varchar(255)   not null,
    evaluation decimal(38, 2) null,
    name       varchar(100)   not null,
    constraint UKhmp7u91lji59hpc1e02q8v0li
        unique (cnpj)
);

create table product
(
    id             bigint auto_increment
        primary key,
    category       enum ('FOOTWEAR', 'GYM', 'JEWELLERY', 'PANTS', 'SHIRTS', 'SHOES', 'SHORTS', 'SPORTS', 'WATCHES') null,
    description    varchar(255)                                                                                     null,
    image          varchar(255)                                                                                     null,
    name           varchar(100)                                                                                     not null,
    starting_price decimal(38, 2)                                                                                   not null,
    shop_id        bigint                                                                                           null,
    constraint FK94hgg8hlqfqfnt3dag950vm7n
        foreign key (shop_id) references shop (id)
);

create table product_variant
(
    id             bigint auto_increment
        primary key,
    quantity       int            not null,
    size           varchar(50)    not null,
    price decimal(38, 2) not null,
    product_id     bigint         not null,
    constraint FKgrbbs9t374m9gg43l6tq1xwdj
        foreign key (product_id) references product (id)
);

create table users
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) not null,
    name     varchar(100) not null,
    password varchar(255) not null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);

create table cart
(
    id      bigint auto_increment
        primary key,
    status  enum ('AGUARDANDO_ENVIO', 'AGUARDANDO_PAGAMENTO', 'EM_ROTA', 'ENTREGUE') not null,
    user_id bigint                                                                   not null,
    constraint FKg5uhi8vpsuy0lgloxk2h4w5o6
        foreign key (user_id) references users (id)
);

create table order_items
(
    id             bigint auto_increment
        primary key,
    quantity       int            not null,
    shipping_price decimal(38, 2) null,
    order_id       bigint         null,
    variant_id     bigint         not null,
    constraint FKb0fe3pykpehuhrhkam7u57va5
        foreign key (variant_id) references product_variant (id),
    constraint FKsx2m213s2xjfw7w1cy89jjceo
        foreign key (order_id) references cart (id)
);

