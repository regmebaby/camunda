# The camunda bpm repository
This repo currently contains the camunda SQL connector.

##Overview: Camunda SQL Connector
The Camunda SQL Connector is a simple Maven project that produces a .jar file, which contains a camunda bpm 
connector that can be picked up by any camunda runtime, as long as it is within its classpath. 

The goal of the connector is to enable process designers to interact with various DBMSes from BPMN processes, 
without any of the technical details getting in their way. The first release of the connector aims to support
SELECT queries and Stored Procedure(STP) calls. This way, process analysts can access database information by simply
supplying the connector with the appropriate query. The connector executes the query using a JDBC library, 
evaluates the results, serialises them to the best of its abilities and returns a JSON-Formatted representation to the 
process (service) task that invoked it.  

##How to use
###Building
In order to use this connector you first have to build it. To to this, simply clone the repository 
onto your own machine and build with Maven from command line:
```
git clone https://github.com/dribnif/camunda.git
cd camunda-sql-connector
mvn clean install
``` 
###Deployment and dependencies
This will create both the jar file called **target/sql-connector-{VERSION}.jar** as well as a folder **lib/** 
containing all the dependencies of the connector. 

Place *all of these jars* somewhere within the classpath of a camunda runtime.

###JDBC Driver
Depending on the database that 
your SQL-Connector will interact with, you also need the appropriate JDBC driver. *(In order to connect to a current Oracle
DB for example, download the file ojdbc6.jar  from here:
http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html)*

Place this jar in the classpath as well. 

###DB Connection config
The final bit needed to enable the connector, is the DB connection config. 
There are 3 different ways of supplying the connector with this information:

- a properties file 
named **config.properties** that is placed somewhere within the classpath.  Here is an example of its contents, 
which would connect to an Oracle XE listening on port 49161:

```
sql.connector.url=jdbc:oracle:thin:@localhost:49161:xe
sql.connector.username=humphrey
sql.connector.password=test
sql.connector.driver.classname=oracle.jdbc.driver.OracleDriver
```

- a **JNDI DataSource** configured via your servlet container. An example of how to do this in tomcat can be found 
here: [Tomcat JNDI Datasource](http://stackoverflow
.com/questions/9183321/how-to-use-jndi-datasource-provided-by-tomcat-in
-spring) Assuming the JNDI resource is named **jdbc/OracleTestDatabase**, in order to configure the Camunda SQL 
Connector to use this resource at runtime, a parameter has to be passed into the connector. This is called: **sql
.connector.cfg.jndi.name** and would take the name of the resource as value. This can either be hardcoded into the
BPMN process, or could be filled in at runtime e.g. from a user input field. 

```
parameter name: sql.connector.cfg.jndi.name
parameter value: jdbc/OracleTestDatabase
```

- it is also possible to supply the connector with the basic DataSource information completely via connector input 
params. In order to do this, the following input parameters can be passed to the connector from the process definition:

```
db.conf.url=jdbc:oracle:thin:@localhost:49161:xe
db.conf.username=humphrey
db.conf.password=test
db.conf.driver.classname=oracle.jdbc.driver.OracleDriver
```

As is the case with the JNDI config, the values can be either hardcoded into the BPMN process or they can be assigned
 at runtime from user input or various other sources.
 
#### Loading order

The connector will first attempt to use the configuration supplied via input parameters. If this fails, it tries to 
look for the JNDI name parameter in the request. If this also fails, it defaults to the config.properties file. 


###And you're done
Now (re)start the container. In case of a camunda bpm installation bundled with Tomcat, these can be placed in the 
*tomcat/lib* folder.  
  
###Using it
The SQL connector can then be referenced via it's unique **connector id** from any service task of a camunda BPM process. The connector id 
of the camunda connector is 

```
sql-connector
```

And since a picture is worth more than a thousand words, there is also an example BPMN process included with the connector
project, that allows you to see how to call it. The process can be found in the folder */resources* and is called 
*test-process.bpmn*. Simply deploy the process to your camunda runtime and start a new process instance. 

The "configuration magic" happens in the service task called "Connect to DB" where the connector is invoked. It 
expects one of two possible String type 
input parameter configs:
 
* One can invoke the connector with the input param called **select** which returns the query results as a 
JSON parameter called **result**.
* A stored procedure call is also possible, using **two** input parameters for the connector. The first one is the 
name of the stored procedure that is to be invoked. The second one contains all the IN and INOUT parameters of the 
stored procedure, formatted as a JSON array. Here is an invocation example (Connector input parameters in the camunda
 modeller):

Assuming the existence of the STP: EXAMPLE_STP with the Params: X:IN, Y:INOUT, Z:OUT

```
parameter name: stp
parameter value: EXAMPLE_STP
parameter name: params
parameter value: [{"name":"X", "value":"0.5", "dataType:"DOUBLE"}, {"name":"Y", "value":"Some Random String", 
"dataType:"STRING"}]
```
The result will be returned in the output parameter named **result**, just as with the select call.

The Data type can take any value from java.sql.Types. (http://docs.oracle.com/javase/7/docs/api/java/sql/Types.html)

The key names in the JSON array have to be enclosed in double quotes. An empty array also constitutes a valid request.
