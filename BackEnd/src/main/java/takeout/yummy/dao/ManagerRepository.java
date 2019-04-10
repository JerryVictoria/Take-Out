package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.entity.Manager;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/3
 */
public interface ManagerRepository extends CrudRepository<Manager, String> {

    Boolean existsByUidAndPassword(String uid,String password);
}
