<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Workflow</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark flex" style="justify-content: space-between;width: 100\%">
        <H1 style="color:white">My Projects</H1>
            
    	<div class="d-flex">
            <form class="form-inline my-2 my-lg-0 mx-5"  method="GET" action="/workflow">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Home</button>
            </form>
        
            <form class="form-inline my-2 my-lg-0"  method="GET" action="/workflow/logout">
                <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Logout</button>
            </form>
        </div>

    </nav>
    
	<table class='table table-bordered'>
        <thead>
            <tr>
                <th for="project">Project</th>
                <th for="tickets">Tickets</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${projects}" var="proj">
                <tr>
                    <td>${proj.projectName}</td>
                    <td>
                    	<div style="display:inline-block">
                    	<form class="form-inline my-2 my-lg-0"  method="POST" action="/workflow/project/tickets">
                    		<input type="hidden" name="projId" value="${proj.id}"/>
                			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">View Tickets</button>
            			</form>
            			</div>
            			
            			<div style="display:inline-block">
            			<form class="form-inline my-2 my-lg-0"  method="GET" action="select">
                    		<input type="hidden" name="projId" value="${proj.id}"/>
                			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Add Users</button>
            			</form>
            			</div>
            			
            			<div style="display:inline-block">
            			<form class="form-inline my-2 my-lg-0"  method="GET" action="/workflow/ticket/create">
                    		<input type="hidden" name="projId" value="${proj.id}"/>
                			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Create Ticket</button>
            			</form>
            			</div>
            		</td>
                </tr>
            </c:forEach>
        </tbody>
     </table>
</body>
</html>