<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
	<title>Upload Asset Debugger</title>
</head>

<body>  
	<h2>Upload Asset Debugger</h2>
	<form method="POST" th:action="@{/element/upload}" enctype="multipart/form-data">
	 
	    <p>Select File to Upload</p>
	    <table id="fileTable">
	        <tbody>
	        	<tr>
	            	<td><input name="uploadFile" type="file"></input></td>
	        	</tr>
	    	</tbody>
	    </table>
	    <br></br>
	    <input type="submit" value="Upload"></input>
	</form>
</body>
</html>