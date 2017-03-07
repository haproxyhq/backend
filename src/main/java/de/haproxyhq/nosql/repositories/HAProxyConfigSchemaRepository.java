package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.haproxyhq.nosql.model.HAProxyConfigSchema;

/**
 * 
 * @author Maximilian Büttner, Johannes Hiemer.
 *
 */
public interface HAProxyConfigSchemaRepository extends MongoRepository<HAProxyConfigSchema, String>{
}
