package com.example.usersample.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth", new io.swagger.v3.oas.models.security.SecurityScheme()
                        .type(Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(In.HEADER)
                        .name("Authorization")))
                .info(apiInfo());
    }

    private Info apiInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("1. 먼저 사용자를 생성합니다. - POST /api/v1/users (권한은 ROLE_USER or ROLE_ADMIN) \n");
        sb.append("2. 생성된 사용자로 로그인을 합니다. - GET /api/v1/login \n");
        sb.append("3. 로그인을 하면 JWT 토큰이 발급됩니다.\n");
        sb.append("4. 발급된 JWT 토큰을 사용하여 API를 호출합니다. (상단 Authorize 에 토큰값을 입력 합니다. (Bearer 제거 순수 토큰값 만) \n");

        return new Info()
                .title("User-Sample API")
                .description(sb.toString())
                .version("1.0.0");
    }
}
