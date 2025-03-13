package com.emarkx.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The main function of service registry is used for inter service communication with microservices
 * registration happens with heart beat signal
 * during registration ms provides details like hostname ip address port etc
 * heartbeat signal : interval is configurable and can be adjusted
 * heartbeat monitoring is done by eureka server
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceregistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceregistryApplication.class, args);
	}

}
