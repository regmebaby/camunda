# The camunda bpm repository
This repo currently contains the camunda SQL connector.

####Overview: Camunda SQL Connector
The Camunda SQL Connector is a simple Maven project that produces a .jar file, which contains a camunda bpm 
connector that can be picked up by any camunda runtime, as long as it is within its classpath. 

The goal of the connector is to enable process designers to interact with various DBMSes from BPMN processes, 
without any of the technical details getting in their way. The first release of the connector aims to support
SELECT queries and Stored Procedure(STP) calls. This way, process analysts can access database information by simply
supplying the connector with the appropriate query. The connector executes the query using a JDBC library, 
evaluates the results, serialises them to the best of its abilities and returns a JSON-Formatted representation to the 
process (service) task that invoked it.  

####How to use
#####Building
In order to use this connector you first have to build it. To to this, simply clone the repository 
onto your own machine and build with Maven from command line:
```
git clone https://github.com/dribnif/camunda.git
cd camunda-sql-connector
mvn clean install
``` 
#####Deployment and dependencies
This will create both the jar file called **target/sql-connector-{VERSION}.jar** as well as a folder **lib/** 
containing all the dependencies of the connector. 

Place *all of these jars* somewhere within the classpath of a camunda runtime.

#####JDBC Driver
Depending on the database that 
your SQL-Connector will interact with, you also need the appropriate JDBC driver. *(In order to connect to a current Oracle
DB for example, download the file ojdbc6.jar  from here:
http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html)*

Place this jar in the classpath as well. 

#####DB Connection config
The final bit needed to enable the connector, is the DB connection config. 
Currently this information is supplied via a properties file named **config.properties** that is also placed 
somewhere within the classpath.  Here is an example of its contents, which would connect to an Oracle XE listening 
on port 49161:

```
sql.connector.url=jdbc:oracle:thin:@localhost:49161:xe
sql.connector.username=humphrey
sql.connector.password=test
sql.connector.driver.classname=oracle.jdbc.driver.OracleDriver
```
######And you're done
Now (re)start the container. In case of a camunda bpm installation bundled with Tomcat, these can be placed in the 
*tomcat/lib* folder.  
  
#####Using it
The SQL connector can then be referenced via it's unique **connector id** from any service task of a camunda BPM process. The connector id 
of the camunda connector is 
```
sql-connector
```
And since a picture is worth more than a thousand words, there is also an example BPMN process included with the connector
project, that allows you to see how to call it. The process can be found in the folder */resources* and is called 
*test-process.bpmn*. Simply deploy the process to your camunda runtime and start a new process instance. 

The "configuration magic" happens in the service task called "Connect to DB" where the connector is invoked. It expects the String type 
input parameter called **select** and returns the query results as a parameter called - surprise!!! - **result**.

####How it works

TODO

####Notes regarding the current state of development, implemented features as well as a roadmap

TODO
