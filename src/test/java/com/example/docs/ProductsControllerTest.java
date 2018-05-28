package com.example.docs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;

public class ProductsControllerTest extends BaseRestDocTest {

    @InjectMocks
    private ProductsController productController;

    @Override
    public void init() {
    }

    @Override
    public Object testControllers() {
        return productController;
    }

    @Test
    public void getProducts() throws Exception {
        //@formatter:off
        
        // Convert object to JSON string
        List<Product> products = ProductMockData.buildProductList();
        String json = objectMapper.writeValueAsString(products);
        System.out.println(json);

        this.mockMvc.perform(get("/products"))     
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
               // .andExpect(jsonPath("$.banks", hasSize(banks.size())))
               // .andExpect(jsonPath("$.banks[0].id", is(banks.get(0).getId())))
               // .andExpect(jsonPath("$.banks[1].id", is(banks.get(1).getId())))
               // .andExpect(jsonPath("$.banks[2].id", is(banks.get(2).getId())))
  //              .andExpect(jsonPath("$.banks[3].id", is(banks.get(3).getId())))
  //              .andExpect(jsonPath("$.banks[4].id", is(banks.get(4).getId())))
                ;

        //@formatter:on
    }

    @Test
    public void createProducts() throws Exception {
        // @formatter:off
        Product newProduct = new Product(); 
        newProduct.setSku("123");
        newProduct.setDescription("DESCRIPTION");
       
         // Convert object to JSON string
        String json = objectMapper.writeValueAsString(newProduct);
        System.out.println(json);

        this.mockMvc.perform(post("/products" )
               // .contextPath(ApiController.API_PATH).with(userToken(false))
                .contentType(contentType) 
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())

                //.andExpect(jsonPath("$.generateReportRequest.multiple", is(false)))
                ;

        // @formatter:on
    }

}
