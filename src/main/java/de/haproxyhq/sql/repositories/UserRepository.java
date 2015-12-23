package de.haproxyhq.sql.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import de.haproxyhq.sql.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	@RestResource(path = "nameContains", rel = "nameContains")
	public Page<User> findByNameContains(@Param("name") String name, Pageable p);
	
	@RestResource(path = "emailContains", rel = "emailContains")
	public Page<User> findByEmailContains(@Param("email") String email, Pageable p);
	
	User findUserByEmail(String email);
	
	/*@Query("SELECT u FROM User u WHERE u.firstName = :secondName")
	List<User> findUserBySecondName(String secondName);*/

}
