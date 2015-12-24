package de.haproxyhq.nosql.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@MappedSuperclass
public class AbstractEntity {
	
	@Id
	private String id;

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AbstractEntity) if(this.id.equals(((AbstractEntity) obj).getId())) return true;
		return false;
	}
}
