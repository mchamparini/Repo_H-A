
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Panel de Control: Reprocesos</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <!-- styles -->
        <link href="css/stylescp.css" rel="stylesheet" type="text/css"/>  

        <!--TEST -->
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">

        <!--TEST -->
    </head>
    <body>
        <div class="header" style="background-color: #092756">
            <div class="container">
                <div class="row">
                    <div class="col-md-5">
                        <!-- Logo -->
                        <div class="logo">
                            <h1><a href="index.htm">REWORK</a></h1>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="input-group form">
                                    <input type="text" class="form-control" placeholder="Search...">
                                    <span class="input-group-btn">
                                        <button class="btn btn-primary" type="button">Search</button>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="navbar navbar-inverse" role="banner">
                            <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right" role="navigation">
                                <ul class="nav navbar-nav">
                                    <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">My Account <b class="caret"></b></a>
                                        <ul class="dropdown-menu animated fadeInUp">
                                            <li><a href="*">Profile</a></li>
                                            <li><a href="index.htm">Logout</a></li>                                  
                                        </ul>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-md-2">
                    <div class="sidebar content-box" style="display: block;">
                        <ul class="nav">
                            <!-- Main menu -->
                            <li class="current"><a href="showCP.htm"><i class="glyphicon glyphicon-home"></i> HOME</a></li>
                            <li class="submenu">
                                <a href="#">
                                    <i class="glyphicon glyphicon-list"></i> PROJECTS
                                    <span class="caret pull-right"></span>
                                </a>
                                <!-- Sub menu -->
                                <ul>
                                    <li><a href="show_process.htm">project list <i class="glyphicon glyphicon-open-file"></i> </a></li>
                                </ul>
                            </li>
                            <li><a href="index.htm"><i class="glyphicon glyphicon-eject"></i>EXIT</a></li> 
                        </ul>
                    </div>
                </div>
                <div class="col-md-10">
                    <div class="row">
                        <div class="col-md-12 panel-warning">
                            <div class="content-box-header panel-heading">
                               <!-- <div>
                                    <label title="dd/mm/yyyy">fecha reproceso:</label>
                                    <input type="text" id="datepicker" /> 
                                </div>       -->               
                                <!--<div class="panel-options">
                                    <a href="#" data-rel="collapse"><i class="glyphicon glyphicon-refresh"></i></a>
                                    <a href="show_process.htm" data-rel="reload"><i class="glyphicon glyphicon-cog"></i></a>
                                </div>-->
                            </div>                               
                            <div class="content-box-large box-with-header">
                                <table class="table table-striped table-bordered">
                                    <tr>
                                        <th>PROYECTO</th>
                                        <!--<th>RAW</th>
                                        <th>HOUR</th>-->
                                       <!-- <th>DAY</th>
                                        <th>BH</th>
                                        <th>IBHW</th>-->
                                        <th>PARAMETERS</th>
                                        <th>#</th>
                                    </tr>
                                    <c:forEach var="lst" items="${requestScope.listProcessToRun}">
                                        <tr>   
                                            <td >${lst.processName}</td>
                                          <!--  <td>
                                                <label class="label label-success">OK</label>
                                               
                                                <!--<input class="form-control" style="border-style: hidden;border-color: #000\9 ;background-color: #000\9" type="text" name="txtRAW"/>    -->
                                         <!--   </td>
                                            <td>
                                                  <label class="label label-success">OK</label>
                                            </td>
                                            <td>
                                                <label class="label label-success">OK</label>
                                            </td>
                                            <td>
                                                  <label class="label label-success">OK</label>

                                            </td>
                                            <td>-->
                                          <!--        <label class="label label-success">OK</label>-->
                                          <!--  </td>  -->                                                                                      
                                          <form:form action="${lst.processName}.htm" method="get">
                                                <td>
                                                    <input class="form-control" style="border-style: hidden;border-color: #000\9 ;background-color: #000\9" placeholder="input parameters" type="text" name="txtParam"/>
                                                </td>                                      
                                                <td>
                                                    <input class="btn btn-block btn-primary"  type="submit" value="rework" />
                                                </td> 
                                          </form:form>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                             
                        </div>
                        <div class="col-md-12 panel-warning">
                            <div class="content-box-header panel-heading">
                                     <label class="label label-info pull-right">LOG</label>                               
                                                                                   
                            </div> 
                            <div class="content-box-large box-with-header">
                                ${requestScope.txtResultCommand}                                                            
                            </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <footer style="background-color: #092756">
            <div class="container" style="background-color: #092756">

                <div class="copy text-center">
                    Copyright 2017 <a href='#'>Website H+A</a>
                </div>

            </div>
        </footer>

        <script src="https://code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <script src="js/custom.js" type="text/javascript"></script>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $(function () {
                $("#datepicker").datepicker();
            });
        </script>
    </body>
</html>
