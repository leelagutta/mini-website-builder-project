# mini-website-builder-project

<h3>Tools to install</h3>
MySQL 5.X (Persistent Store) <br>
-http://dev.mysql.com/downloads/mysql/ <br>
-Ensure MySQL server is running on <b>3306</b><br>

Redis Server (LRU Cache) <br>
-http://redis.io/download <br>
-Ensure Redis is running on port <b>6379</b> on localhost <br>

Maven (Dependency Management/Build tool) <br>
-https://maven.apache.org/download.cgi  <br>

You can optionally use homebrew to install the binaries above

<h3>  Apps: </h3>
Viewer <br>
-purpose of this dynamic server is to serve content to the website customers

 Builder<br>
-purpose of this dynamic server is to enable website customer to build a world class website

<h4>Run viewer</h4>
cd ~/{yourworkspace}/website-builder-project <br>
mvn exec:java -pl viewer <br>

Default port: 9002. You can also set another port by passing command line arguments as port number

<h4>Run builder</h4>
cd ~/{yourworkspace}/website-builder-project <br>
mvn exec:java -pl builder <br>

Default port: 9001. You can also set another port by passing command line arguments as port number

<h4>Run Unit Tests/Buid Applications</h4>
mvn clean test install <br>

<h4> REST API Endpoints</h4>
JSON endpoints, import using <b>POSTMAN:</b> https://www.getpostman.com/collections/580afa5560e1a7414680
<br>
TODO: Implement Swagger for REST endpoint documentation: http://swagger.io/
