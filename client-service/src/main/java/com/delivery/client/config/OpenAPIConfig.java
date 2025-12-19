/*package com.delivery.client.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI clientServiceAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8081");
        devServer.setDescription("Development Server");

        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080/client-service");
        gatewayServer.setDescription("Gateway Server");

        Contact contact = new Contact();
        contact.setName("Delivery Platform Team");
        contact.setEmail("support@delivery-platform.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Client Service API")
                .version("1.0.0")
                .description("API for managing clients in the Delivery Platform")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, gatewayServer));
    }
}

 */