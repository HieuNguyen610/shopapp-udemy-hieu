Create database ShopApp;
drop table users;
CREATE TABLE users (
    id SERIAL PRIMARY KEY ,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL ,
    address VARCHAR(200) DEFAULT '',
    password varchar(100) NOT NULL default '',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    is_active BOOLEAN,
    date_of_birth TIMESTAMP,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);

alter table users rename column phone_name to phone_number;


CREATE TABLE tokens (
    id SERIAL PRIMARY KEY ,
    token varchar(255) unique not null ,
    token_type varchar(50) not null ,
    revoked boolean,
    expired boolean,
    expiration_date timestamp,
    user_id int, -- FK users
    foreign key (user_id) references users(id)
);

-- Google and Facebook login
create table social_accounts(
    id serial primary key ,
    provider varchar(20) not null,
    provider_id varchar(50) not null ,
    email varchar(150) not null ,
    name varchar(100) not null ,
    user_id int,
    foreign key (user_id) references users(id)
);

comment on column social_accounts.provider is 'social network provider';
comment on column social_accounts.provider_id is 'social network provider user id';
comment on column social_accounts.email is 'social network user email';

create table categories(
    id serial primary key ,
    name varchar(100) not null default ''
);

create table roles(
                           id serial primary key ,
                           name varchar(100) not null default ''
);

create table users_roles (
    user_id int ,
    role_id int,
    foreign key (user_id) references users(id),
    foreign key (role_id) references roles(id)
);

comment on column categories.name is 'electronic , ...';


create table products (
    id SERIAL PRIMARY KEY ,
    name VARCHAR(350) NOT NULL,
    description text,
    price float NOT NULL CHECK (price >= 0),
    quantity INT NOT NULL,
    url VARCHAR(300) DEFAULT '',
    category_id INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    foreign key (category_id) references categories(id)
);
UPDATE products SET active = TRUE;

comment on column products.name is 'Laptop, Mobile Phone, ...';
comment on column products.description is 'Product description';
comment on column products.price is 'Product price';
comment on column products.quantity is 'Product quantity';
comment on column products.url is 'Product image url';

drop  table product_images;

create table product_images (
    id serial primary key ,
    product_id int,
    foreign key (product_id) references products(id) on delete cascade,
    image_url varchar(300)
);


drop table orders;

create table orders(
    id serial primary key ,
    user_id int,
    foreign key (user_id) references users(id),
    fullname varchar(100) default '',
    email varchar(100) default '',
    phone varchar(20) not null ,
    address varchar(200) default '',
    note varchar(100) default '',
    order_date TIMESTAMP,
    updated_at TIMESTAMP,
    status varchar(20), -- pending, delivering, cancel, done
    total_money float check ( total_money >= 0 ),
    shipping_method varchar(100),
    shipping_address varchar(200),
    shipping_date timestamp,
    tracking_number varchar(100),
    payment_method varchar(100)
);

alter table orders add column active boolean;

-- CREATE TYPE order_status AS ENUM ('pending', 'processing', 'shipped', 'delivered', 'cancelled');
ALTER TABLE orders ALTER COLUMN status TYPE order_status USING status::text::order_status;
ALTER table orders alter column status type varchar;


create table order_details(
    id serial primary key ,
    order_id int,
    foreign key (order_id) references orders (id),
    product_id int,
    foreign key (product_id) references products(id),
    number_of_products int check ( number_of_products > 0 ),
    price float check ( price >= 0 ),
    total_money float check(total_money >= 0),
    color varchar(20) default ''
);


