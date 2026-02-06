package org.bazar.chat.it.testutil;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.bazar.chat.it.testutil.TestDataTransformUtil.readFileWithoutThrow;

@Component
public class WireMockTestHelper {
    private static final String GET_USERS_BAZAR_PERSONA = "/users";

    @Autowired
    protected WireMockServer bazarPersonaServer;

    public void startMockBazarPersonaServer() {
        bazarPersonaServer.start();
    }

    public void stopWireMockServers() {
        bazarPersonaServer.stop();
    }

    public void stubBazarPersonaGetUsers_200(List<UUID> userIds, String bodyPath) {
        MappingBuilder mapping = get(urlPathEqualTo(GET_USERS_BAZAR_PERSONA));

        for (UUID id : userIds) {
            mapping = mapping.withQueryParam("ids", equalTo(id.toString()));
        }

        mapping.willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(readFileWithoutThrow(bodyPath))
        );

        bazarPersonaServer.stubFor(mapping);
    }
}
