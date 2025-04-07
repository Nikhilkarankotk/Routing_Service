package com.nkk.Gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtValidationFilter jwtValidationFilter) {
		return builder.routes()
				.route("register-route", r -> r.path("/api/users/register")
						.uri("http://localhost:8080")) // No filter for register
				.route("login-route", r -> r.path("/api/users/login")
						.uri("http://localhost:8080")) // No filter for login
				.route("hello-route", r -> r.path("/api/users/hello")
						.filters(f -> f.filter(jwtValidationFilter.apply(new JwtValidationFilter.Config())))
						.uri("http://localhost:8080"))
				.build();
	}
}
