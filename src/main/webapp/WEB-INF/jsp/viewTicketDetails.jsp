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
        <H1 style="color:white">Create a Ticket</H1>
            
    	<div class="d-flex">
            <form class="form-inline my-2 my-lg-0 mx-5"  method="GET" action="/workflow">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Home</button>
            </form>
        
            <form class="form-inline my-2 my-lg-0"  method="GET" action="/workflow/logout">
                <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Logout</button>
            </form>
        </div>

    </nav>
    
	<section class="vh-100" style="background-color: #508bfc;">
	  <div class="container py-5 h-100">
	    <div class="row d-flex justify-content-center align-items-center h-100">
	      <div class="col-12 col-md-8 col-lg-6 col-xl-5">
	        <div class="card shadow-2-strong" style="border-radius: 1rem;">
	          <div class="card-body p-5 text-center">
	
	            <h3 class="mb-5">Ticket Details</h3>

					<div class="form-outline mb-4">
		              <label class="form-label" for="typeEmailX-2">Subject: </label>
		              <label>${ticket.ticketSubject}</label>
		            </div>
		            <div class="form-outline mb-4">
		              <label class="form-label" for="typePasswordX-2">Description: </label>
		              <label>${ticket.ticketDescription}</label>
		            </div>
		            
		            <div class="form-outline mb-4">
		              <label class="form-label">Type: </label>
		              <label>${ticket.ticketType}</label>
		            </div>

				<div class="form-outline mb-4">
		              <label class="form-label">Status: </label>
		              <label>${ticket.status}</label>
		            </div>

				<div class="form-outline mb-4">
		              <label class="form-label">Assiged Project: </label>
		              <label>${ticket.project.projectName}</label>
		            </div>

				<div class="form-outline mb-4">
		              <label class="form-label">Assiged Engineer: </label>
		              <label>${ticket.assigedTo.userName}</label>
		            </div>

				<div class="form-outline mb-4">
		              <label class="form-label">Created On: </label>
		              <label>${ticket.createOn}</label>
		            </div>

				<div class="form-outline mb-4">
		              <label class="form-label">Modified On: </label>
		              <label>${ticket.modifiedOn}</label>
		            </div>

				<div class="form-outline mb-4">
		              <label class="form-label">Supporting Document: </label>
				  <label>${ticket.filePath}</label>
				  <form class="form-inline my-2 my-lg-0"  method="GET" action="/workflow/ticket/download">
					<input type="hidden" name="filename" value="C:/workflowFiles/${ticket.filePath}" />
                			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Download</button>
            		  </form>
		            </div>
	
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
	</section>
</body>
</html>