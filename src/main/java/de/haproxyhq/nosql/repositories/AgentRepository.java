package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.haproxyhq.nosql.model.Agent;

/**
 * 
 * @author Maximilian Büttner, Jonas Depoix, Johannes Hiemer.
 *
 */
public interface AgentRepository extends MongoRepository<Agent, String> {
}
