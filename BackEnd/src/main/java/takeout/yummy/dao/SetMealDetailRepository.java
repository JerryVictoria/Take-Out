package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.entity.SetMealDetail;

import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/25
 */
public interface SetMealDetailRepository extends CrudRepository<SetMealDetail, Integer> {

    List<SetMealDetail> findAllBySetMealPid(Long pid);
}
