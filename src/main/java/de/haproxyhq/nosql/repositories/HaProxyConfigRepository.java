package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.haproxyhq.nosql.HaProxyConfig;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public interface HaProxyConfigRepository extends MongoRepository<HaProxyConfig, String>{
	
}
