<%@ page language="java" contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="multipart/form-data; charset=ISO-8859-1">
	<title>Create Customer Page</title>
	</head>
<body>
<h2>Upload Asset Debugger</h2>
     <form action="#" action="/home/upload" method="POST">
	    <p>Select File to Upload</p>
	    <table id="fileTable">
	        <tbody>
	        	<tr>
	            	<td><input name="uploadFile" type="file"></input></td>
	        	</tr>
	    	</tbody>
	    </table>
	    <br></br>

	    <button type="submit">Upload</button>
	</form>
</body>