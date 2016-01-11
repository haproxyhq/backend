package de.haproxyhq.sql.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import de.haproxyhq.sql.model.User;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findUserByEmail(@Param(value = "email") String email);

}
