<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sform"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Pets</title>
</head>
<body>
	<h5 color='red'>${message}</h5>
	<c:if test="${empty pets._id}">
	<h3> Add Pet </h3>
	<sform:form method="post" modelAttribute="pets" action="savePets">
	<sform:hidden path="_id" />
		<table>
			<tr>
				<td>Pet name:</td>
				<td><sform:input path="name" /></td>
				<td><sform:errors path="name" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Pet species:</td>
				<td><sform:input path="species" /></td>
				<td><sform:errors path="species" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Pet breed:</td>
				<td><sform:input path="breed" /></td>
				<td><sform:errors path="breed" cssClass="error" /></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="Save Pet"/></td>
			</tr>
		</table>
	</sform:form>
	</c:if>
	
	<c:if test="${!empty pets._id}">
	<h3> Edit Pet </h3>
	<sform:form method="post" modelAttribute="pets" action="save">
	<sform:hidden path="_id" value = "${_id}"/>
		<table>
			<tr>
				<td>Pet name:</td>
				<td><sform:input path="name" /></td>
				<td><sform:errors path="name" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Pet species:</td>
				<td><sform:input path="species" /></td>
				<td><sform:errors path="species" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Pet breed:</td>
				<td><sform:input path="breed" /></td>
				<td><sform:errors path="breed" cssClass="error" /></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="Update Pet"/></td>
			</tr>
		</table>
	</sform:form>
	</c:if>
	
	<h3> List of Pets </h3>
	<c:if test="${!empty response}">
	<table border='1'>
	<tr>
		<th width="100">Pet id</th>
		<th width="100">Pet name</th>
		<th width="100">Pet species</th>
		<th width="100">Pet breed</th>
		<!-- <th width="100"></th> -->
		<th width="100"></th>
	</tr>
	<c:forEach items="${response}" var="pets">
		<tr>
			<td>${pets._id}</td>
			<td>${pets.name}</td>
			<td>${pets.species}</td>
			<td>${pets.breed}</td>
			<%-- <td><a href="<c:url value='/edit/${pets._id}' />" >Edit</a></td> --%>
			<td><a href="<c:url value='/remove/${pets._id}' />" >Delete</a></td>
		</tr>
	</c:forEach>
	</table>
	</c:if>

</body>
</html>