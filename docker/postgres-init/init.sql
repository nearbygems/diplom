CREATE USER nearbygems WITH ENCRYPTED PASSWORD '123';
CREATE DATABASE diplom WITH OWNER nearbygems;
GRANT ALL PRIVILEGES ON DATABASE diplom TO nearbygems;