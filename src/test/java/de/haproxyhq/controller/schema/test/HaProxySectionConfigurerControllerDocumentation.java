/**
 * 
 */
package de.haproxyhq.controller.schema.test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import de.haproxy.test.MockMvcTest;
import de.haproxyhq.controller.schema.types.ConnectionDetails;
import de.haproxyhq.nosql.repositories.AgentRepository;

/**
 * @author Johannes Hiemer.
 *
 */
public class HaProxySectionConfigurerControllerDocumentation extends MockMvcTest {

	private String DEFAULT_AGENT_IDENTIFIER = "Default-HaProxy-Agent";

	@SuppressWarnings("unused")
	@Autowired
	private AgentRepository agentRepository;

	private static String REL = "/agents";

	@Test
	public void addListener() throws Exception {
		ConnectionDetails connectionDetails = createConnectionDetails(5000);

		this.mockMvc
				.perform(put(REL + "/" + DEFAULT_AGENT_IDENTIFIER + "/schemas/append?type=listen")
						.accept(MediaTypes.HAL_JSON).header(tokenName, token).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(connectionDetails)))
				.andExpect(status().isCreated()).andDo(document("listen-schema-create"));
	}

	@Test
	public void addAndRemoveListener() throws Exception {
		ConnectionDetails connectionDetails = createConnectionDetails(5003);

		this.mockMvc
				.perform(put(REL + "/" + DEFAULT_AGENT_IDENTIFIER + "/schemas?type=listen").accept(MediaTypes.HAL_JSON)
						.header(tokenName, token).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(connectionDetails)))
				.andExpect(status().isCreated()).andDo(document("listen-schema-create"));

		this.mockMvc
				.perform(delete(REL + "/" + DEFAULT_AGENT_IDENTIFIER + "/schemas?type=listen")
						.accept(MediaTypes.HAL_JSON).header(tokenName, token).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(connectionDetails)))
				.andExpect(status().isNoContent()).andDo(document("listen-schema-create"));

	}

	private ConnectionDetails createConnectionDetails(int port) {
		ConnectionDetails connectionDetails = new ConnectionDetails();
		connectionDetails.setIp("192.168.1.1");
		connectionDetails.setPort(port);

		return connectionDetails;
	}

}
