package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.Util.MarketProductInfo;
import takeout.yummy.entity.ProductInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/7
 */
public interface ProductInfoRepository extends CrudRepository<ProductInfo, Long> {

    List<ProductInfo> findAllByRestaurantRidAndStartTimeBeforeAndEndTimeAfter(int rid, LocalDate time1, LocalDate time2);

    List<ProductInfo> findAllByRestaurantRidAndEndTimeAfter(int rid, LocalDate now);

    Optional<ProductInfo> findByPid(Long pid);
}
