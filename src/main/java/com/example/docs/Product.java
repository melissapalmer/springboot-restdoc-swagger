package com.example.docs;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a product tracked by the application.")
public class Product {

    @ApiModelProperty(notes = "Unique identifier of the product. No two products can have the same sku.")
    /**
     * SKU - unique product id
     */
    private String sku;

    /**
     * Descriptive text of a product
     */
    @NotNull
    @Size(min = 1, max = 20)
    private String description;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product [sku=" + sku + ", description=" + description + "]";
    }

    public static class Builder {
        private Product prod = new Product();

        public Builder sku(String sku) {
            prod.setSku(sku);

            return this;
        }

        public Builder description(String desc) {
            prod.setDescription(desc);

            return this;
        }

        public Product build() {
            return prod;
        }
    }

}
