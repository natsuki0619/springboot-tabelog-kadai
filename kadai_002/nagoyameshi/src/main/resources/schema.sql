 CREATE TABLE IF NOT EXISTS category (
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   category_name VARCHAR(50) NOT NULL,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
 );

 CREATE TABLE IF NOT EXISTS shops (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    image_name VARCHAR(255),
    opening VARCHAR(50) NOT NULL,
    closed VARCHAR(50) NOT NULL,
    regular_holiday VARCHAR(255),
    lowest_price INT NOT NULL,
    highest_price INT NOT NULL,
    capacity VARCHAR(255) NOT NULL,
    menu VARCHAR(255) NOT NULL,
    postal_code VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    category_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category (id)
 );

 CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
 );

 CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    furigana VARCHAR(50) NOT NULL,
    postal_code VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    enabled BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles (id)
 );

 CREATE TABLE IF NOT EXISTS verification_tokens (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    token VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
 );

 CREATE TABLE IF NOT EXISTS profiles (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    representative VARCHAR(50) NOT NULL,
    establishment VARCHAR(255) NOT NULL UNIQUE,
    capital VARCHAR(255) NOT NULL,
    business VARCHAR(255) NOT NULL,
    employees VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles (id)
  );

  CREATE TABLE IF NOT EXISTS termsofservice (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sentence VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
 );

 CREATE TABLE IF NOT EXISTS reservations (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shop_id INT NOT NULL,
    user_id INT NOT NULL,
    reservation_date DATE NOT NULL,
    reservation_time TIME NOT NULL,
    number_of_people INT NOT NULL,
    remarks TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (shop_id) REFERENCES shops (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
 );

 CREATE TABLE IF NOT EXISTS reviews (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shop_id INT NOT NULL,
    user_id INT NOT NULL,
    evaluation INT NOT NULL,
    comments VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
 );

 CREATE TABLE IF NOT EXISTS favorite (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shop_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
 );