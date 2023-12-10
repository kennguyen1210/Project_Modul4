package ra.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.academy.dao.deliverInfo.DeliverInfoDao;
import ra.academy.model.DeliverInfo;
@Service
public class DeliverInfoService {
    @Autowired
    private DeliverInfoDao deliverInfoDao;
    public void save(DeliverInfo d){
        if(d.getId() == null){
            // tao moi
            deliverInfoDao.save(d);
        } else {
            // update
            deliverInfoDao.update(d);
        }
    }
}
