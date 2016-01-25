package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.haproxyhq.nosql.model.Schema;


@RepositoryRestResource(collectionResourceRel = "schemas", path = "schemas")
public interface SchemaRepository extends MongoRepository<Schema, String>{
	
}
