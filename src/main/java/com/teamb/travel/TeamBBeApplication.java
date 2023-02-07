package com.teamb.travel;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TeamBBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamBBeApplication.class, args);
    }

    @Bean
    public ServletWebServerFactory serverFactory() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory
                = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(createStandardConnector());

        return tomcatServletWebServerFactory;
    }

    private Connector createStandardConnector() { // http : 80
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(80);
        return connector;
    }

}
