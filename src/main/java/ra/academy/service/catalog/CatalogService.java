package ra.academy.service.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.academy.dao.catalog.CatalogDao;
import ra.academy.model.Catalog;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CatalogService implements ICatalogService{
    @Override
    public int getTotalPage(int size, int len) {
        if(len % size == 0){
            return len / size;
        }
        return (len / size) + 1 ;
    }

    @Override
    public List<Catalog> findAll(int page, int size, String search) {
        List<Catalog> list = catalogDao.findByName(search);
        return list.stream()
                .filter(cat->list.indexOf(cat) >= page*size)
                .limit(size).collect(Collectors.toList());
    }

    @Autowired
    private CatalogDao catalogDao;
    @Override
    public List<Catalog> findAll() {
        return catalogDao.findAll();
    }

    @Override
    public Catalog findById(Long id) {
        return catalogDao.findById(id);
    }

    @Override
    public void save(Catalog catalog) {
        catalogDao.save(catalog);
    }

    @Override
    public void delete(Long id) {
        catalogDao.delete(id);
    }

    @Override
    public List<Catalog> findByName(String name) {
        return catalogDao.findByName(name);
    }

    @Override
    public void update(Catalog catalog) {
        catalogDao.update(catalog);
    }

    @Override
    public boolean checkExistByName(String name) {
        return catalogDao.findAll().stream().anyMatch(e->e.getCatalogName().equals(name));
    }
}
