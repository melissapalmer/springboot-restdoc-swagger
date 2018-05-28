package com.example.docs;

import java.util.ArrayList;
import java.util.List;

public class ProductMockData {

    public static List<Product> buildProductList() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product.Builder().sku("1234").description("Hockey Shoes").build());
        products.add(new Product.Builder().sku("4567").description("Soccer Shoes").build());
        products.add(new Product.Builder().sku("8910").description("Golf Shoes").build());

        return products;
    }

    public static Product updateProduct(Product product) {
        product.setDescription(product.getDescription() + " - modified ");
        return product;
    }
}
