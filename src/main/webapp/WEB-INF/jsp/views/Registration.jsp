<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Registrierung</h1>

<div id="messages">
    <spring:hasBindErrors name="User">
        <h2>Errors</h2>
        <div class="formerror">
            <ul>
            <c:forEach var="error" items="${errors.allErrors}">
                <li>${error.defaultMessage}</li>
            </c:forEach>
            </ul>
        </div>
    </spring:hasBindErrors>
</div>

<div id="result">
	
</div>

<div id="form">
	<form:form modelAttribute="user" id="creationForm" method="POST" action="/users">
	    <fieldset>
	        <div class="form-row">
	            <label for="firstName">Vorname:</label>
	            <span class="input"><form:input path="firstName" /></span>
	        </div>       
	        <div class="form-row">
	            <label for="name">Nachname:</label>
	            <span class="input"><form:input path="name" /></span>
	        </div>
	        <div class="form-row">
	            <label for="email">Email:</label>
	            <span class="input"><form:input path="email" /></span>
	        </div>
	        <div class="form-row">
	            <label for="password">Passwort:</label>
	            <span class="input"><form:input path="password" type="password" /></span>
	        </div>
	        <div class="form-buttons">
	            <div class="button">
	                <input type="submit" id="save" name="_eventId_save" value="Registrieren"/>&#160;
	                <input type="submit" name="_eventId_cancel" value="Cancel"/>&#160;          
	            </div>    
	        </div>
	    </fieldset>
	</form:form>
</div>
<script type="text/javascript" src="<c:url value='/static/js/jquery.js'/>"></script>
<script type="text/javascript"> 
	var form = $('#creationForm');
	form.submit(function () {
		var user = {
				firstName: $("#firstName").val(),
				name: $("#name").val(),
				email: $("#email").val(),
				password: $("#password").val()
		};
		$.ajax({
			type: form.attr('method'),
			url: form.attr('action'),
			data: JSON.stringify(user),
			headers: {
				"Content-Type":"application/json"
			},
			success: function (result) {
				console.log(result);
				$('#result').prepend('<p>'+JSON.stringify(result)+'<p>');
			},
			error: function (error) {
				console.log(error);
				$('#result').prepend('<p>'+error.responseText+'<p>');
			}
		});
		return false;
	});
</script>