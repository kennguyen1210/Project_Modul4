package ra.academy.dao.deliverInfo;

import ra.academy.dao.IGenericDao;
import ra.academy.model.DeliverInfo;

import java.util.List;

public interface IDeliverInfoDao extends IGenericDao<DeliverInfo, Long> {
    List<DeliverInfo> findByUserId(Long userId);
}
