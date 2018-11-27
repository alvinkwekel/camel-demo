package com.liberition.cameldemo;

import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource("classpath:shared.properties"),
    @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound=true),
    @PropertySource(value = "file:application.properties", ignoreResourceNotFound=true)})
public class SharedConfig {

    @Value("${amq.url}")
    private String amqUrl;

    RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy policy = new RedeliveryPolicy();
        policy.setMaximumRedeliveries(0);
        return policy;
    }

    ActiveMQConnectionFactory coreConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(amqUrl);
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy());
        return connectionFactory;
    }

    JmsConfiguration jmsConfiguration() {
       return new JmsConfiguration(new PooledConnectionFactory(coreConnectionFactory()));
    }

    @Bean(name = "activemq")
    public ActiveMQComponent activeMQComponent() {
        ActiveMQComponent component = new ActiveMQComponent();
        component.setConfiguration(jmsConfiguration());
        return component;
    }

    @Bean
    public CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            public void beforeApplicationStart(CamelContext context) {
                context.setUseMDCLogging(true);
                context.setUnitOfWorkFactory(BusinessContextUnitOfWork::new);
            }

            public void afterApplicationStart(CamelContext camelContext) {
            }
        };
    }
}
