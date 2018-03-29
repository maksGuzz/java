# java
My HelloWorld in java

1 clone this repo:
<code>
git clone https://github.com/maksGuzz/java
</code>

2 Prepare MySQL DB and user.
<code>
$ sudo mysql --password

mysql> create database db_example; -- Create the new database
mysql> create user 'springuser'@'localhost' identified by 'ThePassword'; -- Creates the user
mysql> grant all on db_example.* to 'springuser'@'localhost'; -- Gives all the privileges to the new user on the newly created database
</code>

3 Compile project using Maven
<code>
./mvnw spring-boot:run
</code>
or 
<code>
./mvnw clean package && java -jar target/gs-accessing-data-mysql-0.1.0.jar
</code>
<code>

</code>
<code>

</code>
<code>

</code>
