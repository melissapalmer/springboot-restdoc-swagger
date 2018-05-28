package com.example.docs;

import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.AbstractMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.SnippetRegistry;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.section.SectionSnippet;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseRestDocTest {

    private static final String HOST = "host.domain.com";

    String outputDir = System.getProperty("io.springfox.staticdocs.outputDir");
    String snippetsDir = System.getProperty("io.springfox.staticdocs.snippetsOutputDir");
    String asciidocOutputDir = System.getProperty("generated.asciidoc.directory");

    @Autowired
    private WebApplicationContext context;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    // public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(System.getProperty("io.springfox.staticdocs.snippetsOutputDir"));

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * Note that the converter needs to be autowired into the test in order for MockMvc to recognize it in the setup() method.
     */
    @Autowired
    private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    public MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    protected MockMvc mockMvc;

    /**
     * Pretty print request and response
     * 
     * @param useCase
     *            the name of the snippet
     * @return RestDocumentationResultHandler
     */
    protected String stripFirstPathForUnix(String path) {
        // Unix assumes the / to be root and not under the target folder :-(

        if (path.startsWith("/")) {
            path = path.substring(1, path.length());
        }

        return path;
    }

    protected RestDocumentationResultHandler commonDocumentation() {
        return document("api/{class-name}/{method-name}", commonRequestPreprocessor(), commonResponsePreprocessor());
    }

    protected OperationRequestPreprocessor commonRequestPreprocessor() {
        return preprocessRequest(replaceBinaryContent(), limitJsonArrayLength(objectMapper), prettyPrint());
    }

    protected OperationResponsePreprocessor commonResponsePreprocessor() {
        return preprocessResponse(replaceBinaryContent(), /* limitJsonArrayLength(objectMapper), */prettyPrint());
    }

    @Before
    public void setup() throws Exception {
        init();

        //@formatter:off
        SectionSnippet section = AutoDocumentation.sectionBuilder()
                                .snippetNames(SnippetRegistry.PATH_PARAMETERS, 
                                              SnippetRegistry.REQUEST_PARAMETERS, 
                                              SnippetRegistry.REQUEST_FIELDS, 
                                              SnippetRegistry.RESPONSE_FIELDS, 
                                              SnippetRegistry.HTTP_REQUEST, 
                                              SnippetRegistry.HTTP_RESPONSE)
                                .skipEmpty(true)
                                .build();

        MockitoAnnotations.initMocks(this);
        
        AbstractMockMvcBuilder<?> mvcBuilder = null;
        if (testControllers() != null) {
            mvcBuilder = MockMvcBuilders.standaloneSetup(testControllers());
        } else {
            mvcBuilder = MockMvcBuilders.webAppContextSetup(context);
        }
        
        this.mockMvc = mvcBuilder
                //.setMessageConverters(jackson2HttpMessageConverter)
                //.addFilters(springSecurityFilterChain)                
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .alwaysDo(commonDocumentation())
                //.setControllerAdvice(new CustomRestExceptionHandler())
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()) //
                .apply(documentationConfiguration(restDocumentation)
                    .uris()
                    .withScheme("https")
                    .withHost(HOST)
                    .withPort(443)
                    .and()
                    .snippets()
                    .withDefaults(CliDocumentation.curlRequest(),
                            HttpDocumentation.httpRequest(),
                            HttpDocumentation.httpResponse(),
                            AutoDocumentation.requestFields(),
                            AutoDocumentation.responseFields(),
                            AutoDocumentation.pathParameters(),
                            AutoDocumentation.requestParameters(),
                            AutoDocumentation.description(),
                            AutoDocumentation.methodAndPath(),
                            section/*, 
                            AutoDocumentation.authorization(DEFAULT_NO_AUTHORIZATION)*/
                            )
                ).build();
        //@formatter:on       
    }

    public abstract void init();

    public abstract Object testControllers();

}
