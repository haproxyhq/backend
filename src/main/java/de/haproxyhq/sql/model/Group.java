package de.haproxyhq.sql.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@Entity
@Table(name = "\"group\"")
public class Group extends AbstractEntity implements GrantedAuthority {

	private static final long serialVersionUID = 3654960858853619755L;

	private String name;
	private String description;

	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "groups")
	Set<User> users;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "groups_rights", joinColumns = @JoinColumn(name = "group_id") , inverseJoinColumns = @JoinColumn(name = "right_id") , uniqueConstraints = @UniqueConstraint(columnNames = {
			"group_id", "right_id" }) )
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

	@Override
	public String getAuthority() {
		return name;
	}
}
