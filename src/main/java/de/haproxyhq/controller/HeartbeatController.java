package de.haproxyhq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.haproxyhq.nosql.model.Agent;
import de.haproxyhq.nosql.model.AgentHeartbeatStatus;
import de.haproxyhq.nosql.repositories.AgentRepository;

@RepositoryRestController
public class HeartbeatController {

    @Autowired
    private AgentRepository agentRepository;

    @RequestMapping(value = "/agents/heartbeats", method = RequestMethod.GET)
    public ResponseEntity<Map<String, AgentHeartbeatStatus>> getHeartbeatData() {
    	Map<String, AgentHeartbeatStatus> agentHeartbeats = new HashMap<>();
    	
    	for(Agent agent : agentRepository.findAll()) {
    		agentHeartbeats.put(agent.getId(), agent.getAgentHeartbeat());
    	}
    	
        return new ResponseEntity<Map<String, AgentHeartbeatStatus>>(agentHeartbeats, HttpStatus.OK);
    }
}
