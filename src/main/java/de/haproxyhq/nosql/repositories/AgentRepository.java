package de.haproxyhq.nosql.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import de.haproxyhq.nosql.model.Agent;

/**
 * 
 * @author Maximilian Büttner, Jonas Depoix, Johannes Hiemer.
 *
 */
public interface AgentRepository extends MongoRepository<Agent, ObjectId> {
	
	Agent findByName(@Param("name") String name);
	
}
