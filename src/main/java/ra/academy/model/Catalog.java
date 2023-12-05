package ra.academy.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Catalog {
    private Long catalogId;
    private String catalogName;
    private String description;

    private LocalDateTime createdAt;
    private boolean status ;

    public Catalog() {
    }

    public Catalog(Long catalogId, String catalogName, String description, LocalDateTime createdAt, boolean status) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
    }


    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String time(LocalDateTime c){
       return c.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
