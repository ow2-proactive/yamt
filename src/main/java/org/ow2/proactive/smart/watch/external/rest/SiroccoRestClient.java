package org.ow2.proactive.smart.watch.external.rest;

import org.ow2.proactive.smart.watch.model.NodeInformation;
import org.ow2.proactive.smart.watch.transformers.SiroccoProviderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Component
public class SiroccoRestClient {

    @Autowired
    private WebTarget siroccoWebTarget;

    @Autowired
    private SiroccoProviderTransformer siroccoProviderTransformer;

    @Value("${sirocco.tenant}")
    private String siroccoTenant;

    /**
     * Translates an alienid (SiroccoID) into a ProActive Resource Manager ID
     * (IaaS ID).
     *
     * @param alienid
     *            An ID of an alien system (Not ProActive).
     * @return ProActive Resource Manager ID string.
     */
    public NodeInformation getProactiveId(String alienid) {
        String response = siroccoWebTarget.path("/" + alienid).request().header("tenantName", siroccoTenant)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);

        return siroccoProviderTransformer.transformJsonIntoProActiveId(response);
    }

}
