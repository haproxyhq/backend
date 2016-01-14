package de.haproxyhq.sql.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import de.haproxyhq.sql.model.User;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	User findUserByEmail(@Param(value = "email") String email);
	
}
