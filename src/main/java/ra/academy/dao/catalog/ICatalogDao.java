package ra.academy.dao.catalog;


import ra.academy.dao.IGenericDao;
import ra.academy.model.Catalog;

import java.util.List;
public interface ICatalogDao extends IGenericDao<Catalog,Long> {
    List<Catalog> findByName(String name);
    List<Catalog> findCatalogHaveProduct();
}
