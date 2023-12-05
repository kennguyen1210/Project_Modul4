create table banner
(
    id       bigint auto_increment
        primary key,
    link     text         null,
    head_1   varchar(100) null,
    head_2   varchar(100) null,
    head_3   varchar(100) null,
    category varchar(50)  null
);

create table catalogs
(
    catalogId   bigint auto_increment
        primary key,
    catalogName varchar(100) null,
    description text         null,
    createdAt   timestamp    null,
    status      tinyint(1)   null
);

create table products
(
    productId   bigint auto_increment
        primary key,
    productName varchar(150)         null,
    catalogId   bigint               null,
    description text                 null,
    unitPrice   double               null,
    stock       int                  null,
    created_at  timestamp            null,
    updated_at  timestamp            null,
    status      tinyint(1) default 1 null,
    imageUrl    text                 null,
    gen         tinyint(1)           null,
    constraint fk_5
        foreign key (catalogId) references catalogs (catalogId)
);

create table users
(
    userId    bigint auto_increment
        primary key,
    userName  varchar(50)  null,
    email     varchar(100) null,
    fullName  text         null,
    password  varchar(50)  null,
    role      tinyint(1)   null,
    status    tinyint(1)   null,
    createdAt timestamp    null,
    updatedAt timestamp    null,
    avatar    text         null,
    constraint email
        unique (email),
    constraint userName
        unique (userName)
);

create table deliverinfo
(
    id          bigint auto_increment
        primary key,
    userId      bigint       null,
    name        varchar(100) null,
    phoneNumber varchar(11)  null,
    address     text         null,
    constraint fk_4
        foreign key (userId) references users (userId)
);

create table favoriteslist
(
    Id        bigint auto_increment
        primary key,
    userId    bigint null,
    productId bigint null,
    constraint fk_6
        foreign key (userId) references users (userId),
    constraint fk_7
        foreign key (productId) references products (productId)
);

create table orders
(
    orderId     bigint                                                       not null
        primary key,
    userId      bigint                                                       null,
    name        varchar(50)                                                  null,
    phoneNumber varchar(11)                                                  null,
    address     text                                                         null,
    type        tinyint(1) default 1                                         null,
    total       double                                                       null,
    orderStatus enum ('WAITING', 'CONFIRM', 'DELIVERY', 'SUCCESS', 'CANCEL') null,
    orderAt     timestamp                                                    null,
    deliverAt   timestamp                                                    null,
    constraint fk_1
        foreign key (userId) references users (userId)
);

create table ordersdetail
(
    productId bigint       not null,
    orderId   bigint       not null,
    name      varchar(150) null,
    unitPrice double       null,
    quantity  int          null,
    primary key (productId, orderId),
    constraint fk_2
        foreign key (productId) references products (productId),
    constraint fk_3
        foreign key (orderId) references orders (orderId)
);


