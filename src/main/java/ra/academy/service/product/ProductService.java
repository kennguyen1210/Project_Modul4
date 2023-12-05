package ra.academy.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.academy.dao.product.ProductDao;
import ra.academy.dto.request.ProductRequest;
import ra.academy.dto.response.UpdateProductRequest;
import ra.academy.model.Product;
import ra.academy.service.UploadFileService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private ProductDao productDao;
    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }

    @Override
    public Product createProduct(ProductRequest pro) {
        String urls = pro.getImageUrl().stream().map(u->uploadFileService.uploadFile(u)).collect(Collectors.joining(","));
        return new Product(null,pro.getName(), pro.getCatalogId(),pro.getDes(),pro.getPrice(),pro.getStock(), LocalDateTime.now(),pro.isStatus(),urls, pro.isGen());
    }

    @Override
    public Product updateProduct(UpdateProductRequest pro) {
        String urls;
        if(pro.getImageUrl().get(0).getSize() == 0){
            // img khong thay doi
            urls = pro.getOldImageUrls();
        } else {
            // img co thay doi
            urls = pro.getImageUrl().stream().map(u->uploadFileService.uploadFile(u)).collect(Collectors.joining(","));
        }
        Product product = new Product(pro.getId(), pro.getName(),pro.getCatalogId(), pro.getDes(),pro.getPrice(),pro.getStock(), null,pro.isStatus(),urls,pro.isGen());
        product.setUpdated_at(LocalDateTime.now());
        return product;
    }

    @Override
    public List<Product> findByName(String name) {
        return productDao.findByName(name);
    }

    @Override
    public UpdateProductRequest updatePro(Product p) {
        return new UpdateProductRequest(p.getProductId(),p.getProductName(),p.getDescription(),p.getUnitPrice(),p.getStock(),null,p.getImageUrl(),p.getCreated_at(),p.isStatus(),p.getCatalogId(), p.isGen());
    }

    @Override
    public List<Product> findAll(int page, int size, String search) {
        List<Product> list = productDao.findByName(search);
        return list.stream()
                .filter(cat->list.indexOf(cat) >= page*size)
                .limit(size).collect(Collectors.toList());
    }

    @Override
    public int getTotalPage(int size, int len) {
        if(len % size == 0){
           return len / size;
        }
        return (len / size) + 1;
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public boolean checkExistByName(String name) {
        return productDao.findAll().stream().anyMatch(e->e.getProductName().equals(name));
    }

    @Override
    public List<Product> getListPro(int page, int size, List<Product> list) {
        return list.stream()
                .filter(cat->list.indexOf(cat) >= page*size)
                .limit(size).collect(Collectors.toList());
    }
}
