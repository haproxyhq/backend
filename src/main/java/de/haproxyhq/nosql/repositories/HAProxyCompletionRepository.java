package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.haproxyhq.nosql.model.HAProxyCompletion;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@RepositoryRestResource(collectionResourceRel = "completions", path = "completions")
public interface HAProxyCompletionRepository extends MongoRepository<HAProxyCompletion, String>{

}
