package takeout.yummy.dao;

import org.springframework.data.repository.CrudRepository;
import takeout.yummy.entity.Customer;

import java.util.List;
import java.util.Optional;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/3
 */
public interface CustomerRepository extends CrudRepository<Customer, String> {

    Boolean existsByUsernameAndPassword(String username,String password);

    Boolean existsByUsername(String username);

    Boolean existsByEmailCode(String code);

    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByEmailCode(String emailCode);

    List<Customer> findAllByEmailValid(boolean emailValid);
}
