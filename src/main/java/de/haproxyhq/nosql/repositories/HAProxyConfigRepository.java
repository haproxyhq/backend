package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.haproxyhq.nosql.model.HAProxyConfig;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@RepositoryRestResource(collectionResourceRel = "configs", path = "configs")
public interface HAProxyConfigRepository extends MongoRepository<HAProxyConfig, String>{
	
}
