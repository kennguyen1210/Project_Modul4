package ra.academy.service.product;

import ra.academy.dto.request.ProductRequest;
import ra.academy.dto.response.UpdateProductRequest;
import ra.academy.model.Product;
import ra.academy.service.IGeneric;

import java.util.List;

public interface IProductService extends IGeneric<Product,Long> {
    Product createProduct(ProductRequest pro);
    Product updateProduct(UpdateProductRequest pro);
    List<Product> findByName(String name);

    UpdateProductRequest updatePro(Product p);
    List<Product> findAll(int page, int size, String search);
    int getTotalPage(int size, int len);
    boolean checkExistByName(String name);
    List<Product> getListPro(int page, int size, List<Product> list);
}
