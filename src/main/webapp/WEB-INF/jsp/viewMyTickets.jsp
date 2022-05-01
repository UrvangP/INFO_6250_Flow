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
        <H1 style="color:white">Tickets</H1>
            
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
                <th for="ticketSubject">Ticket Name</th>
                <th for="status">Status</th>
                <th for="pick">Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${tickets}" var="tic">
                <tr>
                    <td>${tic.ticketSubject}</td>
                    <td>${tic.status}</td>
                    <td>
				<div style="display:inline-block">
                    	<form class="form-inline my-2 my-lg-0"  method="GET" action="/workflow/ticket/details">
                    		<input type="hidden" name="ticketNum" value="${tic.id}"/>
                			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">View</button>
            			</form>
				</div>
				<div style="display:inline-block">
                    	<form class="form-inline my-2 my-lg-0"  method="GET" action="/workflow/ticket/edit">
                    		<input type="hidden" name="ticketNum" value="${tic.id}"/>
                			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Edit</button>
            			</form>
				</div>
				<div style="display:inline-block">
                    	<form class="form-inline my-2 my-lg-0"  method="GET" action="/workflow/ticket/delete">
                    		<input type="hidden" name="ticketNum" value="${tic.id}"/>
                			<button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Delete</button>
            		</form>
				</div>
            		</td>
                </tr>
            </c:forEach>
        </tbody>
     </table>
</body>
</html>