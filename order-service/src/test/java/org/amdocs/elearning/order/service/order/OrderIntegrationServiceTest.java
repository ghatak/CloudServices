package org.amdocs.elearning.order.service.order;

import java.util.List;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import org.amdocs.elearning.order.service.Application;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "service.user.port=9090",
                "service.user.hostname=http://localhost"
        }
)
public class OrderIntegrationServiceTest {
    private HttpHeaders headers;

    @Value("${service.user.hostname}")
    private String hostName;

    @Value("${service.user.port}")
    private int userPort;

    @LocalServerPort
    private int PORT;

    @Autowired
    private TestRestTemplate restTemplate;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);

    @Before
    public void setUp() {
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        wireMockRule
                .stubFor(get(WireMock.urlMatching("/users/[0-9]+"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withHeader("content-type", "application/json")
                                        .withBody("{" +
                                                "\"id\":\"1235\"," +
                                                "\"firstName\":\"first\"," +
                                                "\"lastName\":\"last\"," +
                                                "\"middleInitial\":\"middle\"," +
                                                "\"userType\":\"PATRON\"" +
                                                "}")
                        )
                );
    }

    @Test
    public void getAllOrders() throws Exception {
        headers.add("userID", "0");

        HttpEntity<?> entity = new HttpEntity(headers);

        final ResponseEntity<List<Order>> responseEntity = this.restTemplate.exchange(
                hostName + ":" + PORT + "/orders", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Order>>() {});

        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody().isEmpty());
    }
}