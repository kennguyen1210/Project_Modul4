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

insert into catalogs(catalogName,description,status,createdAt)
values ('Giày Thể Thao','Giày thể thao các loại ',1,now()),
 ('Giày Cao Gót','Giày cao gót các loại',1,now()),
 ('Dép Lê','dép lê, xăng đan các loại',1,now()),
 ('Dép quai hậu','dép quai hậu các loại',1,now()),
 ('Bốt','bốt da, bốt vải các loại',1,now()),
 ('Giày da nam','Giày tây, giày da nam',1,now());

insert into products(productName,description,status,catalogId,gen,imageUrl,unitPrice,stock,created_at)
values ('Giày Nike AZZ','Sản xuất năm 2023',1,1,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',1500000,20,now()),
 ('Giày Cao Gót HM','Sản xuất năm 2023',1,2,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',2000000,25,now()),
 ('Giày Cao Gót Zara Z156','Sản xuất năm 2023',1,2,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',1500000,25,now()),
 ('Giày Cao Gót Uniquino C14F ','Sản xuất năm 2023',1,2,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',1000000,25,now()),
 ('Giày Nike ZIAKZA','Sản xuất năm 2023',1,1,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',1300000,20,now()),
 ('Giày Da Cá Sấu LV','Sản xuất năm 2023',1,6,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',3000000,20,now()),
 ('Giày Da Cá Sấu Coccon','Sản xuất năm 2023',1,6,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',2000000,20,now()),
 ('Dép lê AZAMI','Sản xuất năm 2022',1,3,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',350000,30,now()),
 ('Bốt da đen Lacol','Sản xuất năm 2023',1,5,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',3300000,10,now()),
 ('Dép quai hậu KingDom','Sản xuất năm 2023',1,4,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',1500000,20,now()),
 ('Giày da Caravel','Sản xuất năm 2021',1,6,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',4200000,10,now()),
 ('Giày Addidas M12S','Sản xuất năm 2023',1,1,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',650000,15,now()),
 ('Giày Addidas M154','Sản xuất năm 2023',1,1,1,'https://storage.googleapis.com/download/storage/v1/b/upload-firebase-48185.appspot.com/o/item-11.jpg?generation=1702288546377681&alt=media',750000,20,now()),


insert into users(userName, email, fullName, password, role, status, createdAt, avatar)
values
    ('admin123','admin123@gmail.com',null,'123456',1,1,now(),null);