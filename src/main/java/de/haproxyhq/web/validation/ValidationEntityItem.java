/**
 * 
 */
package de.haproxyhq.web.validation;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
public class ValidationEntityItem {
	
	private String entity;
	
	private String message;
	
	private Object invalidValue;
	
	private String property;
	
	public ValidationEntityItem(String entity, String message, String property) {
		super();
		this.entity = entity;
		this.message = message;
		this.property = property;
	}

	public ValidationEntityItem(String entity, String message,
			Object invalidValue, String property) {
		super();
		this.entity = entity;
		this.message = message;
		this.invalidValue = invalidValue;
		this.property = property;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getInvalidValue() {
		return invalidValue;
	}

	public void setInvalidValue(Object invalidValue) {
		this.invalidValue = invalidValue;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}
