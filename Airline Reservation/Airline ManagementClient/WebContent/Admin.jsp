<!DOCTYPE html>
<html lang="en">
<head>
<title>Admin</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">

<%@ page import="beans.Employee"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@page import="servlets.LoginServlet"%>

<%@ page session="true"%>

<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>
<script type="text/javascript" src="js/Myriad_Pro_italic_600.font.js"></script>
<script type="text/javascript" src="js/Myriad_Pro_italic_400.font.js"></script>
<script type="text/javascript" src="js/Myriad_Pro_400.font.js"></script>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>

<script type="text/javascript">
	$(function() {

		$('a#createEmp').click(function() {
			$('div#div1').show();
			$('div#div2').hide();
			$('div#div10').hide();
			var ajaxDisplay = document.getElementById('searchDispEmp');
			ajaxDisplay.innerHTML = '';
			return false;
		});

		$('a#delEmp').click(function() {
			$('div#div2').show();
			$('div#div1').hide();
			$('div#div10').hide();
			var ajaxDisplay = document.getElementById('searchDispEmp');
			ajaxDisplay.innerHTML = '';
			return false;
		});

		$('a#searchEmployee').click(function() {
			$('div#div10').show();
			$('div#div1').hide();
			$('div#div2').hide();
			var ajaxDisplay = document.getElementById('searchDispEmp');
			ajaxDisplay.innerHTML = '';
			return false;
		});

	});
</script>

</head>
<body id="page2">
	<!-- START PAGE SOURCE -->

	<%
		String fName = (String) session.getAttribute("fName");
		boolean signedIn = false;

		if (fName != null)
			signedIn = true;
		else
			response.sendRedirect("Home.jsp");
	%>
	<div class="body1">
		<div class="main">
			<header>
				<div class="wrapper">
					<h1>
						<a href="index.html" id="logo">AirLines</a><span id="slogan">International
							Travel</span>
					</h1>
					<div class="right">
						<nav>
							<ul id="top_nav">
								<li><a href="#"><img src="images/img1.gif" alt=""></a></li>
								<li><a href="#"><img src="images/img2.gif" alt=""></a></li>
								<li class="bg_none"><a href="#"><img
										src="images/img3.gif" alt=""></a></li>
							</ul>
						</nav>
						<nav>
							<ul id="menu">
								<li><a href="Home.jsp">Home</a></li>
						
								<!-- <li><a href="JavaScript:newPopup('Login.jsp');">Login</a></li> -->
								<%
									if (signedIn == false)
									{
								%>
								<li><a href="Login.jsp;">Login</a></li>
								<%
									}
									else
									{
								%>
								<li><a href="MyAccount.jsp">My Account</a></li>
								<li id="menu_active"><a href="Admin.jsp">Admin</a></li>
								<li><a href="Login.jsp;">SignOut</a></li>
								<%
									}
								%>
							</ul>
						</nav>
					</div>
				</div>
			</header>
		</div>
	</div>
	<div class="main">
		<div id="banner">
			<div class="text1">
				COMFORT<span>Guaranteed</span>
				<%
					if (fName != null)
					{
				%>
				<p>
					Welcome
					<%=fName%>
					!
					<%
					}
				%>
				</p>
			</div>
		</div>
	</div>

	<div class="main">
		<section id="content">
			<article class="col1">
				<div class="pad_1">
					<h2>Your Flight Planner</h2>

					<ul id="categories">
						<li style="border-top: 0;"><a href="#" id="createEmp">Create
								New Employee</a></li>
						<li><a href="#" id="delEmp">Delete Employee</a></li>
						<li><a href="#">Delete Customer</a></li>
						<li><a href="#">List All Employees</a></li>
						<li><a href="#">List All Customers</a></li>
						<li><a href="#">List All Reservations</a></li>
						<li><a href="#">List All Flights</a></li>
						<li><a href="#">Update Customer Info</a></li>
						<li><a href="#">Update Employee Info</a></li>
						<li><a href="#" id="searchEmployee">Search Employee</a></li>
						<li><a href="#">Update Customer Info</a></li>


					</ul>
				</div>
			</article>
			<article class="col2 pad_left1">

				<div id="div1">
					<%
						airlineSystem.AirlineServerProxy proxy = new airlineSystem.AirlineServerProxy();
						proxy.setEndpoint("http://localhost:8080/Airline_Management/services/AirlineServer?wsdl");

						String[] stateList = proxy.fetchStateList();
					%>
					<h2>Contact Form</h2>
					<form id="ContactForm" method="post" action="LoginServlet"
						name="form1">
						<div>
							<div class="wrapper">
								<div class="bg">
									<input type="text" class="input" name="firstName" />
								</div>
								First Name:<br />
							</div>

							<div class="wrapper">
								<div class="bg">
									<input type="text" class="input" name="lastName" />
								</div>
								Last Name:<br />
							</div>

							<div class="wrapper">
								<div class="bg">
									<input type="email" class="input" name="email" />
								</div>
								Your E-mail:<br />
							</div>

							<div class="wrapper">
								<div class="bg">
									<input type="password" class="input" name="password" />
								</div>
								Password:<br />
							</div>

							<div class="wrapper">
								<div class="bg">
									<input type="text" class="input" name="address" />
								</div>
								Address:<br />
							</div>

							<div class="wrapper">
								<div class="bg">
									<input type=text class="input" name="city" />
								</div>
								City:<br />
							</div>

							<div class="wrapper">
								<div class="bg">
									<input type="date" class="input" name="DOB" />
								</div>
								Date Of Birth:<br />
							</div>

							<div class="wrapper">
								<div class="bg">
									<input type="text" class="input" name="position" />
								</div>
								Designation:<br />
							</div>

							<div class="wrapper">
								<div class="bg">
									<input type="text" class="input" name="workDescription" />
								</div>
								Work Description:<br />
							</div>


							<div class="wrapper">
								<div class="bg">
									<input type="text" class="input" name="zipCode" />
								</div>
								Zip Code:<br />
							</div>

							<div class="wrapper">
								<div class="bg">
									<span style="display: block; float: left;"> <select
										name="selectedState" id="selectedState"
										style="margin-top: 10px;">
											<%
												for (int i = 0; i < stateList.length; i++)
												{
											%>
											<option value=<%=stateList[i]%>><%=stateList[i]%></option>
											<%
												}
											%>
									</select>
									</span>
								</div>
								State:<br />
							</div>

							<br> <input type="submit" name="Login" class="button1"
								value="Add Employee" style="margin-bottom: 20px;"><br>
						</div>
					</form>

				</div>

				<div id="div2" hidden="yes">
					<form id="ContactForm" method="post" action="LoginServlet"
						name="form2">
						<h2>Enter email ID to delete User!</h2>
						<div>
							<div class="wrapper">
								<div class="bg">
									<input type="email" class="input" name="emailID" />
								</div>
								Email ID:<br />
							</div>

							<br> <input type="submit" name="Login" class="button1"
								value="Delete Employee" style="margin-bottom: 20px;"><br>
						</div>
					</form>
				</div>


				<!-- Abinaya's changes for Search Employee starts here-->
				<div id="div10" hidden="yes">
					<form id="ContactForm" action="LoginServlet" method="POST"
						name="form10">
						<h2>Search Employee</h2>
						<div>
							<div class="wrapper">
								<div class="bg">
									<input type="text" class="input" name="empID" id="empID" />
								</div>
								Employee ID:<br />
							</div>
							<div class="wrapper">
								<div class="bg">
									<input type="text" class="input" name="empDesc"
										id="description" />
								</div>
								Work Description:<br />
							</div>
							<div class="wrapper">
								<div class="bg">
									<input type="date" class="input" name="hireDate" id="hireDate" />
								</div>
								Hired Date:<br />
							</div>

							<br> <input type="button" name="Login" class="button1"
								value="Search Employee" onclick="searchEmp()"
								style="margin-bottom: 20px;"><br> <br>
						</div>
					</form>
				</div>
				<div class="bg" id="searchDispEmp"></div>
				<script>
					function searchEmp() {
						var xmlHttpReq;
						// Mozilla/Safari
						if (window.XMLHttpRequest) {
							xmlHttpReq = new XMLHttpRequest();
						}
						// IE
						else if (window.ActiveXObject) {
							xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
						}
						xmlHttpReq.onreadystatechange = function() {
							if (xmlHttpReq.readyState == 4) {
								var ajaxDisplay = document
										.getElementById('searchDispEmp');
								ajaxDisplay.innerHTML = xmlHttpReq.responseText;
							}
						}
						var param = "empID="
								+ document.getElementById('empID').value
								+ "&description="
								+ document.getElementById('description').value
								+ "&hireDate="
								+ document.getElementById('hireDate').value
								+ "&Login=Search Employee";
						xmlHttpReq.open("POST", "LoginServlet?" + param, true);
						xmlHttpReq.setRequestHeader("Content-length",
								param.length);
						xmlHttpReq.send(null);

					}
				</script>
				<!-- End of changes by Abinaya -->

			</article>
		</section>
	</div>
	<div class="body2">
		<div class="main">
			<footer>
				<div class="footerlink">
					<div style="clear: both;"></div>
				</div>
			</footer>
		</div>
	</div>
	<script type="text/javascript">
		Cufon.now();
	</script>
	<!-- END PAGE SOURCE -->
</body>
</html>