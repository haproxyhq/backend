package de.haproxyhq.sql.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.haproxyhq.sql.Group;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {

}
