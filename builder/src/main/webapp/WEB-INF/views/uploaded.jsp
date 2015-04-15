<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"> 
<head>
    <title>Uploaded File Debug</title>
</head>
<body>
    <h2>Status:</h2>
    	<p th:text="${UploadStatus.status}"></p>
    <br></br>
    <h2>URL:</h2>
    	<p th:text="${UploadStatus.url}"></p>
    	<p th:text="${elementDomain.getContent()}"></p>
    	
   </body>
</html>    