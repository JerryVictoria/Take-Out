package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.entity.CustomerAddress;

import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/5
 */
public interface CustomerAddressRepository extends CrudRepository<CustomerAddress, Integer> {

    List<CustomerAddress> findAllByCustomerUsername(String username);
}
