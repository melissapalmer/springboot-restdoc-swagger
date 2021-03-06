package com.example.docs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Do we see this JavaDoc in swagger? Its on the ProductsController
 */
@RestController
public class ProductsController {

    /**
     * Do we see this JavaDoc in swagger? Its on the ProductsController function getProducts
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = ProductMockData.buildProductList();

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    /**
     * Do we see this JavaDoc in swagger? Its on the ProductsController function updateProduct
     */
    @RequestMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        product = ProductMockData.updateProduct(product);

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

}
