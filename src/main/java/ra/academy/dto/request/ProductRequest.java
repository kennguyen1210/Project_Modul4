package ra.academy.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductRequest {
    private String name;
    private Long catalogId;
    private String des;
    private double price;
    private int stock;
    private List<MultipartFile> imageUrl;
    private boolean status = true;
    private boolean gen;


    public ProductRequest() {
    }

    public ProductRequest(String name, Long catalogId, String des, double price, int stock, List<MultipartFile> imageUrl, boolean status, boolean gen) {
        this.name = name;
        this.catalogId = catalogId;
        this.des = des;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.status = status;
        this.gen = gen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<MultipartFile> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<MultipartFile> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public boolean isGen() {
        return gen;
    }

    public void setGen(boolean gen) {
        this.gen = gen;
    }
}
