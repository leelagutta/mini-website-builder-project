<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<title>Login</title>
</head>
<body>
    <h3>Leela Builder:</h3> 
		<ul> 
			<li><a th:href="@{${googleOauthAuthUrl}}" title="Google">Google Login</a></li> 
			<li><a href="">Facebook Login</a></li>
		</ul> 
</body>
</html>