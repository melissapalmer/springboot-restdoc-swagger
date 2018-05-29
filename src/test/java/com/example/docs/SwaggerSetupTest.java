package com.example.docs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import springfox.documentation.staticdocs.SwaggerResultHandler;

public class SwaggerSetupTest extends BaseRestDocTest {

    @Test
    public void createSpringfoxSwaggerJson() throws Exception {
        // String designFirstSwaggerLocation = Swagger2MarkupTest.class.getResource("/swagger.yaml").getPath();

        System.out.println("\n\n\n .................... ");
        System.out.println(".................... " + outputDir);
        System.out.println(".................... \n\n\n ");

        //@formatter:off
        //THIS generates the swagger.json file cause its accessing swagger-ui json file
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(
                        SwaggerResultHandler.outputDirectory(outputDir)
                        .build()
                )
                .andExpect(status().isOk());
        //@formatter:on

        // String springfoxSwaggerJson = mvcResult.getResponse().getContentAsString();
        // SwaggerAssertions.assertThat(Swagger20Parser.parse(springfoxSwaggerJson)).isEqualTo(designFirstSwaggerLocation);
    }

    @Override
    public void init() {
    }

    @Override
    public Object testControllers() {
        return null;
    }

}
