package de.haproxyhq.sql;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@Entity
@Table(name = "\"user\"")
public class User extends AbstractEntity implements UserDetails {
	
	private static final long serialVersionUID = -8563939028416473118L;
	
	private String firstName;
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private String password;

	@ManyToMany
	@JoinTable(name = "users_groups", joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "group_id") , uniqueConstraints = @UniqueConstraint(columnNames = {
			"user_id", "group_id" }) )
	Set<Group> groups;

	public User() {
		super();
	}

	public User(String firstName, String name, String email, String password) {
		super();
		this.firstName = firstName;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transient
	@Override
	public String getUsername() {
		return email;
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		return true;
	}

}
