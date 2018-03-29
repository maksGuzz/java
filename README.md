# java
My HelloWorld in java

1 clone this repo:<br>
<code>
git clone https://github.com/maksGuzz/java
</code>

2 Prepare MySQL DB and user.<br>
<pre>
$ sudo mysql --password

mysql> create database db_example; -- Create the new database
mysql> create user 'springuser'@'localhost' identified by 'ThePassword'; -- Creates the user
mysql> grant all on db_example.* to 'springuser'@'localhost'; -- Gives all the privileges to the new user on the newly created database
</pre>


3 Compile and run the project using Maven<br>
<code>
./mvnw spring-boot:run
</code>

4 Open URL <code>localhost:8080/</code> in your favorite browser.

5 Enjoy ))
