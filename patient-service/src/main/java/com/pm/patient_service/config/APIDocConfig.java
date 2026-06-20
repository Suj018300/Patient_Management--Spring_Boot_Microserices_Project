package com.pm.patient_service.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;


//@Configuration
//public class SwaggerConfig {
//    @Bean
//    public OpenAPI openAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("User Application APIs")
//                        .description("API documentation for User project")
//                        .version("1.0.6"));
//    }
//}
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Patient Application build by Sujal Bendre.",
                description = "Patient service application that is used to register and manage patients data.",
                contact = @Contact(
                        name = "Sujal Sunil Bendre",
//                        url = "https://www.substringtechnologies.com/",
                        email = "sujalbendre2526@gmail.com"
                ),
                version = "1.0",
                summary = "This app is very useful if you dont want create patient service from scratch."
        ),
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer", //Authorization: Bearer htokenaswga,
        bearerFormat = "JWT"
)
public class APIDocConfig {

}
