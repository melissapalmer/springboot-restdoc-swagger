package com.example.docs.config;

import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Predicate<String> apiPaths() {
        return Predicates.or(PathSelectors.regex("/api/.*"));
    }

    private ApiInfo apiInfo() {
        //@formatter:off
        return new ApiInfoBuilder()
                .title("AngularJS Spring MVC Example API")
                .description("The online reference documentation for developers")
                .termsOfServiceUrl("http://hantsy.blogspot.com")
                .contact(new Contact("Hantsy Bai", "http://hantsy.blogspot.com", "hantsy@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/hantsy/angularjs-springmvc-sample-boot/blob/master/LICENSE")
                .version("2.0")
                .build();
        //@formatter:on
    }
}
