package com.example.docs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.javadoc.configuration.JavadocPluginConfiguration;

@Configuration
@EnableSwagger2
@Import({ BeanValidatorPluginsConfiguration.class, JavadocPluginConfiguration.class })
public class SwaggerConfig {

    private Predicate<String> apiPaths() {
        return Predicates.or(PathSelectors.regex("/api/.*"));
    }

    private ApiInfo apiInfo() {
        //@formatter:off
        return new ApiInfoBuilder()
                .title("TITLE")
                .description("TDESCIPRION")
                .termsOfServiceUrl("TERMS OF SERVICE URL")
                .contact(new Contact("NAME","URL","EMAIL"))
                .license("LICENSE")
                .licenseUrl("LICENSE URL")
                .version("VERSION")
                .build();
        //@formatter:on
    }

    @Bean
    public Docket apiDocket() {
        //@formatter:off
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.docs"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
        //@formatter:on
    }
}
