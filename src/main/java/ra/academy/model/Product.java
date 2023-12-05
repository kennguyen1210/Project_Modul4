package ra.academy.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Product {
    private Long productId;
    private String productName;
    private Long catalogId;
    private String description;
    private double unitPrice;
    private int stock;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean status;
    private String imageUrl;
    private boolean gen;



    public Product() {
    }

    public Product(Long productId, String productName, Long catalogId, String description, double unitPrice, int stock, LocalDateTime created_at, boolean status, String imageUrl, boolean gen) {
        this.productId = productId;
        this.productName = productName;
        this.catalogId = catalogId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.created_at = created_at;
        this.status = status;
        this.imageUrl = imageUrl;
        this.gen = gen;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String time(LocalDateTime c){
        return c.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public boolean isGen() {
        return gen;
    }

    public void setGen(boolean gen) {
        this.gen = gen;
    }
}
