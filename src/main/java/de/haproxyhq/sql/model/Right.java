package de.haproxyhq.sql.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@Entity
@Table(name = "\"right\"")
public class Right extends AbstractEntity implements GrantedAuthority {

	private static final long serialVersionUID = 6139817541217340461L;

	private String name;
	private String description;
	private String key;

	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "rights")
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

	@Override
	public String getAuthority() {
		return name;
	}
}
