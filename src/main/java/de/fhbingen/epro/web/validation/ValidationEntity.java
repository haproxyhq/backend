/**
 * 
 */
package de.fhbingen.epro.web.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Johannes hiemer
 *
 */
public class ValidationEntity {

	private List<ValidationEntityItem> errors = new ArrayList<ValidationEntityItem>();

	public List<ValidationEntityItem> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationEntityItem> errors) {
		this.errors = errors;
	}
	
	public boolean hasErrors() {
		return this.errors.size() > 0;
	}
	
}