<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>

	<meta charset="ISO-8859-1">
	<title>Login </title>
	<link rel="stylesheet" type="text/css" href="/css/universal.css">
	<link rel="stylesheet" type="text/css" href="/css/login.css">
	<script src="home.js"></script>

</head>

<body>
	<nav>
		<div id="nav_container">
			<a href="/">Home </a>
			<a href="menu">Menu </a>
			<a href="about">About </a>
			<a href="contact">Contact </a>
			<a href="login">Login </a>
			<a href="register">Register </a>
			<a></a>
		</div>
	</nav>
	
	<div id="main_container">
		<div id="spacer"></div>
		<div id="section_container">
			<section>
				<div id="form_container">
					<form:form method="POST" modelAttribute="user" class="form-horizontal">
					  Email<br>
					  <form:input type="email" path="email" placeholder="example@test.com" />
					  <form:errors path="email" cssClass="error" />
					  <br>
					  Password<br>
					  <form:input type="password" path="password" />
					  <form:errors path="password" cssClass="error" />
					  <br><br>
					  <input type="submit" value="Login">
					  <input type="button" name="register" value="Register" onclick="window.location='register'">
					  
					</form:form> 
				</div>
			</section>
		</div>

	</div>

	<footer>
		<p>123 example st, Phoenix, AZ, 12345</p>
		<p>123-456-7890</p>
	</footer>

</body>
</html>