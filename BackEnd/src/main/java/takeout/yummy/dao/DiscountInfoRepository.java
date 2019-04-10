package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.entity.DiscountInfo;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/7
 */
public interface DiscountInfoRepository extends CrudRepository<DiscountInfo, Integer> {

    List<DiscountInfo> findAllByRestaurantRidAndStartTimeBeforeAndEndTimeAfter(int rid, LocalDate time1, LocalDate time2);

    List<DiscountInfo> findAllByRestaurantRidAndEndTimeAfter(int rid,LocalDate now);
}
