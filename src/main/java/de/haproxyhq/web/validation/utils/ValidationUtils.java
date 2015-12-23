/**
 * 
 */
package de.haproxyhq.web.validation.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.support.RequestContext;

import de.haproxyhq.web.validation.ValidationEntity;
import de.haproxyhq.web.validation.ValidationEntityItem;

/**
 * 
 * 
 * @author Johannes Hiemer, Maximilian Büttner
 *
 */
public final class ValidationUtils extends org.springframework.validation.ValidationUtils {
	
	private ValidationUtils() {	
	}
	
	/**
	 * @param failures
	 * @return
	 */
	public static <T> Map<String, String> getErrorMessages(Set<ConstraintViolation<T>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<T> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}

	/**
	 * @param target
	 * @param name
	 * @return
	 */
	public static Errors errors(Object target, String name) {
		return new BeanPropertyBindingResult(target, name);
	}
	
	/**
	 * @param target
	 * @return
	 */
	public static Errors errors(Object target) {
		return new BeanPropertyBindingResult(target, target.getClass().getSimpleName());
	}
	
	/**
	 * @param errors
	 * @param field
	 * @param errorCode
	 */
	public static void rejectBlank(Errors errors, String field, String errorCode) {
		org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, errorCode);
	}
	
	/**
	 * @param maxLength
	 * @param errors
	 * @param field
	 * @param errorCode
	 */
	public static void rejectMaxLength(int maxLength, Errors errors, String field, String errorCode) {
		String value = (String)errors.getFieldValue(field);
		if( value != null && value.trim().length() > maxLength ) {
			errors.rejectValue(field, errorCode, new Object[] {Integer.valueOf(maxLength)}, "");
		}
	}
	
	/**
	 * @param errors
	 * @param request
	 * @return
	 */
	public static Map<String, String> resolveMessages(Errors errors, HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		RequestContext requestContext = new RequestContext(request);
		for(FieldError error : errors.getFieldErrors()) {			
			String message = requestContext.getMessage(error.getCode(), error.getArguments());
			result.put(error.getField(), message);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param entity
	 * @param errors
	 * @param request
	 * @return
	 */
	public static ValidationEntity resolveResponse(String entity, Errors errors, HttpServletRequest request) {
		ValidationEntity validationEntity = new ValidationEntity();
		RequestContext requestContext = new RequestContext(request);
		for(FieldError error : errors.getFieldErrors()) {			
			String message = requestContext.getMessage(error.getCode(), error.getArguments());
			validationEntity.getErrors().add(new ValidationEntityItem(entity, message, error.getField()));
		}
		
		return validationEntity;
	}
	
	/**
	 * 
	 * @param entity
	 * @param errors
	 * @return
	 */
	public static ValidationEntity resolveResponse(String entity, Errors errors) {
		ValidationEntity validationEntity = new ValidationEntity();
		for(FieldError error : errors.getFieldErrors()) {			
			validationEntity.getErrors().add(new ValidationEntityItem(entity, error.getCode(), error.getField()));
		}
		
		return validationEntity;
	}
}