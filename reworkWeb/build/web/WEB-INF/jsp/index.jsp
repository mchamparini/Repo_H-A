<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html >
<head>
  <meta charset="UTF-8">
  <title>Login in</title>
<link href="css/normalize.min.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="login">
	<h1>session</h1>
        <form method="post" action="index.htm">
    	<input type="text" name="txtName" placeholder="Username" required="required" />
        <input type="password" name="txtPassword" placeholder="Password" required="required" />
        <button type="submit" class="btn btn-primary btn-block btn-large">log in</button>       
        </form>
    </div>
</body>
</html>
