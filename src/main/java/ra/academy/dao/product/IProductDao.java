package ra.academy.dao.product;

import ra.academy.dao.IGenericDao;
import ra.academy.dto.response.CartList;
import ra.academy.model.Product;

import java.util.List;

public interface IProductDao extends IGenericDao<Product,Long> {
    List<Product> findByName(String name);
    List<Product> findByCatalogId(Long id);
    List<Product> findByGen(Boolean gen);
    List<Product> findBestSeller(int size);
    void updateStock(CartList c);

}
