package de.fhbingen.epro.sql;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "\"right\"")
public class Right extends AbstractEntity {
	private String name;
	private String description;
	private String key;
	
	@ManyToMany(mappedBy = "rights")
	Set<Group> groups;
	
	public Right() {
		super();
	}

	public Right(String name, String description, String key) {
		super();
		this.name = name;
		this.description = description;
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	
	
}
