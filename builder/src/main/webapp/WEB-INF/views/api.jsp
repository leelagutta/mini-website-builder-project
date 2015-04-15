<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<title>API</title>
</head>
<body>
<center><h3> Welcom to Mini Weebly</h3> </center>

	<p th:text="${userModel.getFirstName()}"></p>
	
		
</body>
</html>