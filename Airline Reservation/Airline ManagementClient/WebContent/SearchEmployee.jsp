<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ page import="beans.Employee"%>
	<%@ page import="java.util.*"%>
	<%@ page import="java.text.*"%>


	<%@ page session="true"%>
	<form action="LoginServlet" method="POST">
		<table>
			<tr>
				<td>Search Employee by ID</td>
				<td><input type="text" name="empID" id="empID" value="" /></td>
			</tr>
			<tr>
				<td>Search Employee by Work Description</td>
				<td><input type="text" name="description" id="description"
					value=""></td>
			</tr>
			<tr>
				<td>Search Employee by Hired Date</td>
				<td><input type="date" name="hireDate"></td>
			</tr>
			<tr>
				<td><button type="submit" name="Login" value="Search Employee">Search Employee</button>
			</tr>
		</table>
		<%
			List<Object> emparray = (List<Object>) request.getSession()
					.getAttribute("emp");

			if (emparray != null && !emparray.isEmpty()) {
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		%>

		<table border=2>


			<thead>
				<tr>
					<th colspan=5">Employee</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td id="empID">EmployeeID</td>
					<td>UniqueID</td>
					<td>Work Description</td>
					<td>Position</td>
					<td>HireDate</td>

				</tr>
				<%
					for (Object o : emparray) {
							Employee emp = (Employee) o;
							if (emp != null) {
				%>
				<tr>
					<td><%=emp.getEmployeeID()%></td>
					<td><%=emp.getUniqueID()%></td>
					<td><%=emp.getWorkDescription()%></td>
					<td><%=emp.getPosition()%></td>
					<td><%=ft.format(emp.getHireDate().getTime())%></td>
				</tr>

				<%
					}
						}
				%>
			</tbody>
		</table>
		<%
			} else {
		%>
		No data found
		<%
			}
		%>
	</form>
</body>
</html>