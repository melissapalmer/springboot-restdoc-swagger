package com.example.docs;

public class Product {

    private String sku;
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
