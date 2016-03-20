package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.haproxyhq.nosql.model.HaProxyCompletion;

/**
 * 
 * @author Maximilian Büttner, Johannes Hiemer.
 *
 */
public interface HaProxyCompletionRepository extends MongoRepository<HaProxyCompletion, String>{

}
