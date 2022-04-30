<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Workflow</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
<body>
	<section class="vh-100" style="background-color: #508bfc;">
	  <div class="container py-5 h-100">
	    <div class="row d-flex justify-content-center align-items-center h-100">
	      <div class="col-12 col-md-8 col-lg-6 col-xl-5">
	        <div class="card shadow-2-strong" style="border-radius: 1rem;">
	          <div class="card-body p-5 text-center">
	
	            <h3 class="mb-5">Sign in</h3>
	
				<form:form modelAttribute="user">
					<div class="form-outline mb-4">
		              <label class="form-label" for="typeEmailX-2">Email</label>
		              <form:input path="email" class="form-control form-control-lg"/>
		            </div>
		            <div class="form-outline mb-4">
		              <label class="form-label" for="typePasswordX-2">Password</label>
		              <form:password path="password" class="form-control form-control-lg"/>
		            </div>
		            
		            <button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>
				</form:form>

				<hr class="my-4">
				
			      <form class="form-inline my-2 my-lg-0"  method="GET" action="/workflow/user/register">
		              	<label class="form-label" for="typeRegisterX-2">New User?</label>
                			<button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Register</button>
            		</form>
	
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
	</section>
</body>
</html>