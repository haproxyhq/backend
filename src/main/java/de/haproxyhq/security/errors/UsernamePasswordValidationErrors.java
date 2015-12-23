package de.haproxyhq.security.errors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.AbstractErrors;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class UsernamePasswordValidationErrors extends AbstractErrors {

	private String name;
	private List<ObjectError> globalErrors = new ArrayList<ObjectError>();
	private List<FieldError> fieldErrors = new ArrayList<FieldError>();

	public UsernamePasswordValidationErrors(String name) {
		super();
		this.name = name;
	}

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Errors#getObjectName()
	 * 
	 */
	@Override
	public String getObjectName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Errors#reject(java.lang.String,
	 * java.lang.Object[], java.lang.String)
	 * 
	 */

	@Override
	public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
		globalErrors.add(new ObjectError(name, new String[] { errorCode }, errorArgs, defaultMessage));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Errors#rejectValue(java.lang.String,
	 * java.lang.String, java.lang.Object[], java.lang.String)
	 * 
	 */
	@Override
	public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {
		fieldErrors.add(new FieldError(name, field, getFieldValue(field), true, new String[] { errorCode }, errorArgs,
				defaultMessage));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.validation.Errors#addAllErrors(org.springframework.
	 * validation.Errors)
	 * 
	 */
	@Override
	public void addAllErrors(Errors errors) {
		globalErrors.addAll(errors.getAllErrors());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Errors#getGlobalErrors()
	 * 
	 */
	@Override
	public List<ObjectError> getGlobalErrors() {
		return this.globalErrors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Errors#getFieldErrors()
	 * 
	 */
	@Override
	public List<FieldError> getFieldErrors() {
		return this.fieldErrors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.validation.Errors#getFieldValue(java.lang.String)
	 * 
	 */
	@Override
	public Object getFieldValue(String field) {
		return null;
	}
}