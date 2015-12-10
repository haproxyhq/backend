package de.fhbingen.epro.sql.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.fhbingen.epro.sql.Group;

public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {

}
