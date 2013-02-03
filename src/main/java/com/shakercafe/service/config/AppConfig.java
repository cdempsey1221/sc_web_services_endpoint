package com.shakercafe.service.config;

import java.util.Arrays;

import javax.inject.Inject;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.shakercafe.config.RepositoryConfig;
import com.shakercafe.service.ShakercafeServiceEndpoint;

@Configuration
@Import( {RepositoryConfig.class, ServiceConfig.class} )
public class AppConfig {
	
	@Inject
	private ServiceConfig serviceConfig;
    
	@Bean( destroyMethod = "shutdown" )
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    public Server jaxRsServer() {
        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint( shakercafeServiceEndpoint(), JAXRSServerFactoryBean.class );
        factory.setServiceBeans( Arrays.<Object> asList( serviceConfig.vendorManagementServiceImpl(), serviceConfig.invoiceManagementService() ) );
        factory.setAddress( '/' + factory.getAddress() );
        factory.setProviders( Arrays.< Object >asList( jsonProvider() ) );
        return factory.create();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }
    
    @Bean 
    public ShakercafeServiceEndpoint shakercafeServiceEndpoint() {
        return new ShakercafeServiceEndpoint();
    }
    
}