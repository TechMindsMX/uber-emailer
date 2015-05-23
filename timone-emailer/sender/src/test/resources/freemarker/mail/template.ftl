<html>
<head>
<style type="text/css">
	body{
		font-family: sans-serif;
	}
	.t1{
		background-color: green;
		color: white;
		padding: 10 10 10 10px;
		margin: 10 10 10 10px;
		font-size: 16px;
		text-align: center;
	}
	.t2{
		background-color: #11FF22;
		color: green;
		font-size: 14px;
		text-align: center;
	}
	.t3{
		color: green;
		font-size: 12px;
		text-align: center;
	}
</style>
</head>
<body>
	<div class="t1">Hola ${user.userName}, bienvenido a SpringHispano!!!</div>
	<div class="t2">
   Tu correo es <a href="mailto:${user.email}">${user.email}</a>.
	</div>
	<div class="t3">Muchas gracias por leer este post!!!</div>
</body>
</html>