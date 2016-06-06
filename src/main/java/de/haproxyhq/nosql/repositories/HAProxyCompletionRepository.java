package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.haproxyhq.nosql.model.HAProxyCompletion;

/**
 * 
 * @author Maximilian Büttner, Johannes Hiemer.
 *
 */
public interface HAProxyCompletionRepository extends MongoRepository<HAProxyCompletion, String> {

}
