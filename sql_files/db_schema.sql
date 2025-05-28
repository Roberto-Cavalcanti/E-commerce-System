create table shop
(
    id         bigint auto_increment
        primary key,
    cnpj       varchar(255)   not null,
    evaluation decimal(38, 2) not null,
    name       varchar(100)   not null
);

create table product
(
    id          bigint auto_increment
        primary key,
    category    enum ('CLOTHING', 'FOOTWEAR', 'GYM', 'JEWELLERY', 'PANTS', 'SHIRTS', 'SHOES', 'SHORTS', 'SPORTS', 'WATCHES') null,
    description varchar(255)                                                                                                 null,
    image       varchar(255)                                                                                                 null,
    name        varchar(100)                                                                                                 not null,
    price       decimal(38, 2)                                                                                               not null,
    shop_id     bigint                                                                                                       null,
    constraint FK94hgg8hlqfqfnt3dag950vm7n
        foreign key (shop_id) references shop (id)
);

create table product_variant
(
    id         bigint auto_increment
        primary key,
    size       varchar(50) not null,
    product_id bigint      not null,
    constraint FKgrbbs9t374m9gg43l6tq1xwdj
        foreign key (product_id) references product (id)
);

create table stock
(
    id      bigint auto_increment
        primary key,
    shop_id bigint not null,
    constraint UKk9xq3xywbt5scg2yvfmb1bj5d
        unique (shop_id),
    constraint FKsmxlwrcec4bdo1pf185g6b4xc
        foreign key (shop_id) references shop (id)
);

create table stock_item
(
    id         bigint auto_increment
        primary key,
    quantity   int    not null,
    stock_id   bigint not null,
    variant_id bigint not null,
    constraint UKru8330w62dnh3909utu38sekt
        unique (variant_id),
    constraint FK1p14bepct2q019cw0nj4onbp1
        foreign key (stock_id) references stock (id),
    constraint FK3uclp720o2s8k8ib3y7wsuit8
        foreign key (variant_id) references product_variant (id)
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

create table orders
(
    id             bigint auto_increment
        primary key,
    shipping_price decimal(38, 2)                                                           not null,
    status         enum ('AGUARDANDO_ENVIO', 'AGUARDANDO_PAGAMENTO', 'EM_ROTA', 'ENTREGUE') not null,
    user_id        bigint                                                                   not null,
    constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id) references users (id)
);

create table order_items
(
    id         bigint auto_increment
        primary key,
    quantity   int    not null,
    order_id   bigint null,
    variant_id bigint not null,
    constraint FKb0fe3pykpehuhrhkam7u57va5
        foreign key (variant_id) references product_variant (id),
    constraint FKbioxgbv59vetrxe0ejfubep1w
        foreign key (order_id) references orders (id)
);

