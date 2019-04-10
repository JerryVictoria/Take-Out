package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.entity.OrderList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/14
 */
public interface OrderListRepository extends CrudRepository<OrderList, Long> {

    List<OrderList> findAllByCustomerUsernameAndIsValid(String username, boolean isValid);

    List<OrderList> findAllByCustomerUsernameAndIsPaid(String username, boolean isPaid);

    Optional<OrderList> findByOid(Long oid);

    List<OrderList> findAllByRestaurantRidAndIsProcessedAndIsPaid(int rid, boolean isProcessed, boolean isPaid);

    List<OrderList> findAllByTimeAfterAndTimeBeforeAndIsProcessed(LocalDateTime startTime, LocalDateTime endTime, boolean isProcessed);

}
