# accesslog_to_mysql
﻿The goal is to write a parser in Java that parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration. 

Development
- Enviroment: 
    - Oracle JDK 1.8
    - MySQL 5.6
    
- Build tool: 
    - Apache Maven 3.5.2
  
- Library:
    - Hibernate ORM 5.1.0.Final
    - Log4j 1.2.17
    - C3P0 0.9.5.2
    - MySQL Connector 5.1.38
    - Commons CLI 1.3.1     


Use: How to use?
    
    1. Login to MySQL. Then, create schema.
    
    2. Open file config/hibernate.properties. Then, declare the database account (ip,port,db_name,user,password). The account must have permission to create / edit / drop / select / update / insert / delete table.
       (Java'll create table if not exsist) 
        
    3. Run on command-line
    
        java -cp parse-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.ef.Parser --accesslog=access.log --startDate=2017-01-01.00:00:00 --duration=daily --threshold=500

        note: If the accesslog is found, the old data will be deleted and added new.
 

Note:
    The log file is too large. We'll split log file, then We'll use to thread pool to insert to MySQL.
    
    
    
Deliverables
------------

(1) Java program that can be run from command line
	
    java -cp parse-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.ef.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 

(2) Source Code for the Java program    

(3) MySQL schema used for the log data

(4) SQL queries for SQL test    
