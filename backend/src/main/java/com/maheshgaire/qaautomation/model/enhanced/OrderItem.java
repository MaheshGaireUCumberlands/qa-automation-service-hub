package com.maheshgaire.qaautomation.model.enhanced;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Order item model")
public class OrderItem {
    
    @Schema(description = "Product ID")
    private String productId;
    
    @Schema(description = "Product name")
    private String productName;
    
    @Schema(description = "Product SKU")
    private String sku;
    
    @Schema(description = "Quantity ordered")
    private Integer quantity;
    
    @Schema(description = "Unit price")
    private BigDecimal unitPrice;
    
    @Schema(description = "Total price for this item")
    private BigDecimal totalPrice;
    
    @Schema(description = "Product category")
    private String category;

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}