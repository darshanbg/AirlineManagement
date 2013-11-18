<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ page import="beans.Traveller"%>
	<%@ page import="java.util.List"%>
	<%@ page session="true"%>
	<form action="TravelerSearchServlet" method="POST">
		<table>
			<tr>
				<td>Search traveler by ID</td>
				<td><input type="text" name="travelerID" id="travelerID" value="" /></td>
			</tr>
			<tr>
				<td><button type="submit">Submit</button>
			</tr>
		</table>
		<%
			Traveller t = (Traveller) request.getSession()
					.getAttribute("t");
			if (null != t) {
		%>

		<table>


			<thead>
				<tr>
					<th colspan=5">Traveler</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td id="travelerID">TravelerID</td>
					<td>passportID</td>
					<td>nationality</td>
				</tr>
				<tr>
					<td><%=t.getCustomerId()%></td>
					<td><%=t.getPassportNo()%></td>
					<td><%=t.getNationality()%></td>
				</tr>
			</tbody>
		</table>
		<%
			}
		%>
	</form>
</body>
</html>