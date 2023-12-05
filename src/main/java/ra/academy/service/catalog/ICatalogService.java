package ra.academy.service.catalog;

import ra.academy.model.Catalog;
import ra.academy.service.IGeneric;

import java.util.List;

public interface ICatalogService extends IGeneric<Catalog, Long> {
    List<Catalog> findByName(String name);
    List<Catalog> findAll(int page, int size, String search);
    int getTotalPage(int size, int len);
    boolean checkExistByName(String name);
}
