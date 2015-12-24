package de.haproxyhq.sql.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@Entity
@Table(name = "\"group\"")
public class Group extends AbstractEntity{
	private String name;
	private String description;

	@ManyToMany(mappedBy = "groups")
	Set<User> users;

	@ManyToMany
	@JoinTable(name = "groups_rights",
		joinColumns = @JoinColumn(name = "group_id"),
		inverseJoinColumns = @JoinColumn(name = "right_id"),
		uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "right_id"}))
	Set<Right> rights;

	public Group() {
		super();
	}
	
	public Group(String name, String description) {
		super();
		this.name = name;
		this.description = description;
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Right> getRights() {
		return rights;
	}

	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}
	
	
}
