package ra.academy.dto.response;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateProductRequest {
    private Long id;
    private String name;
    private Long catalogId;
    private String des;
    private double price;
    private int stock;
    private List<MultipartFile> imageUrl;
    private String oldImageUrls;

    private LocalDateTime createDate;
    private boolean status = true;
    private boolean gen;

    public UpdateProductRequest() {
    }

    public UpdateProductRequest(Long id, String name, String des, double price, int stock, List<MultipartFile> imageUrl, String oldImageUrls, LocalDateTime createDate, boolean status, Long catalogId, boolean gen) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.oldImageUrls = oldImageUrls;
        this.createDate = createDate;
        this.status = status;
        this.catalogId = catalogId;
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

    public String getOldImageUrls() {
        return oldImageUrls;
    }

    public void setOldImageUrls(String oldImageUrls) {
        this.oldImageUrls = oldImageUrls;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public boolean isGen() {
        return gen;
    }

    public void setGen(boolean gen) {
        this.gen = gen;
    }
}
