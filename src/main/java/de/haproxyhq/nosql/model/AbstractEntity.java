package de.haproxyhq.nosql.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * 
 * @author Maximilian Büttner.
 *
 */
public class AbstractEntity {
	
	@Id
	private ObjectId id;

	public ObjectId getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AbstractEntity) if(this.id.equals(((AbstractEntity) obj).getId())) return true;
		return false;
	}
}
