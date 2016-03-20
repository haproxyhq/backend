package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.haproxyhq.nosql.model.Schema;

/**
 * 
 * @author Maximilian Büttner, Johannes Hiemer.
 *
 */
public interface SchemaRepository extends MongoRepository<Schema, String>{
}
