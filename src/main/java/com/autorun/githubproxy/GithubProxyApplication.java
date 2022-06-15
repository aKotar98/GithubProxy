package com.autorun.githubproxy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="GITHUB PROXY API", version = "1.0", description = "Proxy API for github.") )
public class GithubProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubProxyApplication.class, args);
    }

}
