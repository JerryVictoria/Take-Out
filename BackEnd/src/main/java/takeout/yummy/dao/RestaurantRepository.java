package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.entity.Restaurant;

import java.util.List;
import java.util.Optional;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/3
 */
public interface RestaurantRepository extends CrudRepository<Restaurant,Integer>{

    Boolean existsByRidAndPassword(int rid,String password);

    Boolean existsByEmailCode(String code);

    Optional<Restaurant> findByRid(Integer integer);

    Optional<Restaurant> findByEmailCode(String emailCode);

    List<Restaurant> findAllByEmailValid(boolean emailValid);
}
