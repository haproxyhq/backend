package de.haproxyhq.sql.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.haproxyhq.sql.model.Right;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public interface RightRepository extends PagingAndSortingRepository<Right, Long> {

}
