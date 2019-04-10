package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.entity.Approval;

import java.util.List;
import java.util.Optional;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/6
 */
public interface ApprovalRepository extends CrudRepository<Approval, Integer> {

    List<Approval> findAllByIsProcessed(boolean isProcessed);

    @Override
    Optional<Approval> findById(Integer integer);

    List<Approval> findAllByRestaurantRidAndIsProcessed(int rid, boolean isProcessed);
}
