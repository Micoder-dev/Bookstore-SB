package com.micoder.bookstore.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SwaggerConfig: WebMvcConfigurer {

    @Bean
    fun api(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Book Store")
                    .description("Book Store API Documentation")
                    .contact(Contact().name("Vexora Solutions").url("https://vexora.in").email("support@vexora.in"))
                    .license(License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                    .version("1.0.0")
                    .summary("Comprehensive API documentation for the Book Store platform.")
                    .termsOfService("https://vexora.in/terms")
            )
    }

}