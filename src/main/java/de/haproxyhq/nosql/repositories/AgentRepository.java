package de.haproxyhq.nosql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.haproxyhq.nosql.model.Agent;

@RepositoryRestResource
public interface AgentRepository extends MongoRepository<Agent, String> {

}
