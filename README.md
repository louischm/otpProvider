# Otp Provider
Little otp provider made from scratch with Spring boot, Hibernate and PostgreSQL.

## Requirements

* Jdk 1.8 or later.
* Maven 4
* PostgreSQL 9.5

## Build

### Database

In order to make this project work you'll need to create and setup a postgresql database:

Here is the command line via shell to do it : 
`sudo -u postgres psql
 postgres=# create database otpprovider;
 postgres=# create user otpadmin with encrypted password '0tp4dm1n';
 postgres=# grant all privileges on database otpprovider to otpadmin;`

So you need a database named `otpprovider` with an user `otpadmin` with the password `0tp4dm1n`


### Running the app

Run the app via this command line in the root directory:

`mvn spring-boot:run`