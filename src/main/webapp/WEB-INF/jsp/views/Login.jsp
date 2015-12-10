<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="result">
	
</div>

<div id="form">
	<form id="loginForm" method="POST" action="/login">
	    <fieldset>
	        <div class="form-row">
	            <label for="username">Username:</label>
	            <span class="input"><input id="username" name="username"/></span>
	        </div>       
	        <div class="form-row">
	            <label for="password">Passwort:</label>
	            <span class="input"><input id="password" type="password" name="password"/></span>
	        </div>
	        <div class="form-buttons">
	            <div class="button">
	                <input type="submit" id="login" name="_eventId_login" value="Login"/>   
	            </div>    
	        </div>
	    </fieldset>
	</form>
</div>