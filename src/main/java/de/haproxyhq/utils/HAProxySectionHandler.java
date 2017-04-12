
package de.haproxyhq.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.haproxyhq.controller.schema.types.ConnectionDetails;
import de.haproxyhq.controller.schema.types.InternalConnectionDetails;
import de.haproxyhq.nosql.model.HAProxyConfig;
import de.haproxyhq.nosql.model.Section;

/**
 * @author Johannes Hiemer.
 *
 */
@Service
public class HAProxySectionHandler {

	private static final String NAME_IDENTIFIER = "name";

	private final Logger log = LoggerFactory.getLogger(HAProxySectionHandler.class);

	private static final String TYPE = "type";
	
	private static final String LISTEN_TYPE = "listen";

	private static final String BIND = "bind";

	private static final String BLANK = " ";

	private static final String COLON = ":";

	private static final String BIND_IP = "0.0.0.0";

	private static final String SERVER = "server service_endpoint";
	
	private static final String MODE = "mode";

	@Value("${haproxy.port_range.start}")
	private int portRangeStart;

	@Value("${haproxy.port_range.end}")
	private int portRangeEnd;

	@Value("${haproxy.external_ip}")
	private String haProxyExternalIp;

	private List<Integer> availablePorts = new ArrayList<Integer>();

	private List<Integer> usedPorts = new ArrayList<Integer>();

	public ConnectionDetails append(HAProxyConfig haProxyConfig, InternalConnectionDetails internalConnectionDetails) {
		List<Section> sections = haProxyConfig.getSections();

		Section section = new Section();

		Map<String, String> sectionProperties = new HashMap<String, String>();
		sectionProperties.put(TYPE, LISTEN_TYPE);
		sectionProperties.put(NAME_IDENTIFIER, internalConnectionDetails.getIdentifier());
		section.setSection(sectionProperties);

		Integer externalPort = this.resolveNextAvailablePort(haProxyConfig);
		List<String> options = internalConnectionDetails.getOptions();
		List<String> values = new ArrayList<String>();
		values.add(BIND + BLANK + BIND_IP + COLON + this.resolveNextAvailablePort(haProxyConfig));
		values.add(MODE + BLANK + internalConnectionDetails.getMode().toLowerCase());
		values.add(SERVER + BLANK + internalConnectionDetails.getIp() + COLON + internalConnectionDetails.getPort());
		for(String option : options) {
			values.add(option);
		}
		
		section.setValues(values);

		sections.add(section);

		return new ConnectionDetails(haProxyExternalIp, externalPort);
	}

	public boolean exists(HAProxyConfig haProxyConfig, InternalConnectionDetails internalConnectionDetails) {
		if (haProxyConfig.getSections() != null)
			for (Section section : haProxyConfig.getSections()) {
				if (section.getSection().size() > 0 && section.getSection().get(NAME_IDENTIFIER) != null) {
					if (section.getSection().get(NAME_IDENTIFIER).equals(internalConnectionDetails.getIdentifier()))
						return true;
				}
			}

		return false;
	}

	public void remove(HAProxyConfig haProxyConfig, InternalConnectionDetails internalCOnnectionDetails) {
		for (Section section : haProxyConfig.getSections()) {
			if (section.getSection().size() > 0 && section.getSection().get(NAME_IDENTIFIER) != null) {
				if (section.getSection().get(NAME_IDENTIFIER).equals(internalCOnnectionDetails.getIdentifier())) {
					haProxyConfig.getSections().remove(section);
					break;
				}
			}
		}
	}

	private void listUsedPort(HAProxyConfig haProxyConfig) {
		for (Section section : haProxyConfig.getSections())
			if (section.getSection().get(TYPE).equals(LISTEN_TYPE))
				for (String value : section.getValues())
					if (value.contains(BIND)) {
						try {
							int portBeginn = value.indexOf(":") + 1;
							String suffix = value.substring(portBeginn, value.length());
							int portEnd = suffix.indexOf(" ");
							if (portEnd < 0)
								portEnd = value.length();
							else
								portEnd += portBeginn;
							usedPorts.add(Integer.parseInt(value.substring(portBeginn, portEnd)));
						} catch (Exception ex) {
							log.error("Exception during used port retrieval. Config corrupt?", ex);
						}
					}
	}

	private void intersect() {
		availablePorts.removeAll(usedPorts);
	}

	private Integer resolveNextAvailablePort(HAProxyConfig haProxyConfig) {
		this.initAvailablePorts();

		this.listUsedPort(haProxyConfig);
		this.intersect();

		return availablePorts.get(0);
	}

	private void initAvailablePorts() {
		availablePorts = new ArrayList<Integer>();
		for (int i = this.portRangeStart; i <= this.portRangeEnd; i++) {
			availablePorts.add(i);
		}
	}

}
