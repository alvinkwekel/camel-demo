package com.liberition;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.nio.charset.Charset;

@ActiveProfiles("it")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisableJmx
@UseAdviceWith
public class FileRoutesIT {

    @EndpointInject(uri = "direct:in")
    static ProducerTemplate SENDER;

    @EndpointInject(uri = "mock:out")
    static MockEndpoint MOCK;

    @Autowired
    ModelCamelContext camelContext;

    @Before
    public void prepare() throws Exception {

        camelContext.getRouteDefinition(FileRoutes.ROUTEID).adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith(SENDER.getDefaultEndpoint());
                weaveAddLast().to(MOCK.getEndpointUri());
            }
        });
        camelContext.start();
    }

    @After
    public void clean() {
        MOCK.reset();
    }

    @Test
    public void testRoute() throws Exception {

        MOCK.expectedMessageCount(1);
        SENDER.sendBody(
            FileUtils.readFileToString(
                new File("src/test/resources/com/liberition/order/sales-order-v1-sample-1.xml"),
                Charset.defaultCharset()));
        MOCK.assertIsSatisfied();
    }
}