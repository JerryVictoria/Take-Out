package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.entity.OrderDetail;

import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/14
 */
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {

    List<OrderDetail> findAllByOrderListOid(Long oid);
}
