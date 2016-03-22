package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.haproxyhq.nosql.model.HaProxyConfigSchema;

/**
 * 
 * @author Maximilian Büttner, Johannes Hiemer.
 *
 */
public interface HaProxyConfigSchemaRepository extends MongoRepository<HaProxyConfigSchema, String>{
}
