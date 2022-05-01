<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Workflow</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <style>
            form{
                margin: 20px;
            }

        </style>
    </head>
    <body>
            	
    	<nav class="navbar navbar-expand-lg navbar-dark bg-dark flex" style="justify-content: space-between;width: 100%;">
            <H1 style="color:white">Admin Home</H1>
            
		<div class="d-flex">
            	<form class="form-inline my-2 my-lg-0" method="GET" action="/workflow/ticket/search">
      			<input class="form-control mr-sm-2" type="search" placeholder="Search" name="searchText">
      			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search Ticket</button>
      		</form>

			<form class="form-inline my-2 my-lg-0" style="float: right;"  method="GET" action="/workflow/logout">
                		<button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Logout</button>
            	</form>
		</div>
	</nav>
	    
	    <h1>Menu</h1>
	    
	    <ul class="list-group">
		  <li class="list-group-item"><a href="/workflow/user/register" class='nav-link'>Add User</a></li>
		  <li class="list-group-item"><a href="/workflow/user/view" class='nav-link'>View Users</a></li>
		  <li class="list-group-item"><a href="/workflow/project/create" class='nav-link'>Create Projects</a></li>
		  <li class="list-group-item"><a href="/workflow/project/view" class='nav-link'>View Projects</a></li>
		</ul>
        
    </body>
</html>
        
   
