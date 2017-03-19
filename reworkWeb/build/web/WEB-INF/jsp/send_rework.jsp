<%-- 
    Document   : send_rework
    Created on : 06/02/2017, 14:55:38
    Author     : mchamparini
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${requestScope.processName}</h1>
        <h1>${requestScope.txtParam}</h1>    
        <p>
            ${requestScope.error_jpql}
        </p>
    </body>
</html>
