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
        <H1 style="color:white">Create Project</H1>
            
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
	
	            <h3 class="mb-5">Create Project</h3>
	            
	
				<form:form modelAttribute="project" method="post">
					<div class="form-outline mb-4">
		              <label class="form-label" for="typeEmailX-2">Project Name</label>
		              <form:input path="projectName" class="form-control form-control-lg"/>
		            </div>
		            <div class="form-outline mb-4">
		              <label class="form-label" for="typePasswordX-2">Description</label>
		              <form:textarea path="description" class="form-control form-control-lg"/>
		            </div>
		            
		            <div class="form-outline mb-4">
		              <label class="form-label">Manager</label>
		              <form:select path="managerName">
	                        <c:forEach var="use" items="${managers}">
	                            <form:option class="form-control form-control-lg" value="${use}"/>
	                        </c:forEach>
	                    </form:select>
		            </div>
		            
		            <button class="btn btn-primary btn-lg btn-block" type="submit">Add</button>
				</form:form>
	
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
	</section>
</body>
</html>