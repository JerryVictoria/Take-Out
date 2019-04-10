package takeout.yummy.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import takeout.yummy.Util.LoginResult;
import takeout.yummy.dao.CustomerRepository;
import takeout.yummy.dao.ManagerRepository;
import takeout.yummy.dao.RestaurantRepository;
import takeout.yummy.entity.Customer;
import takeout.yummy.entity.Restaurant;
import takeout.yummy.enums.UserRole;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/3
 */
@Service
public class LoginServiceImpl implements LoginService {

    private CustomerRepository customerRepository;
    private RestaurantRepository restaurantRepository;
    private ManagerRepository managerRepository;

    @Autowired
    public LoginServiceImpl(CustomerRepository customerRepository, ManagerRepository managerRepository, RestaurantRepository restaurantRepository) {
        this.customerRepository = customerRepository;
        this.managerRepository = managerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public LoginResult checkLoginInfo(String username, String password, String type) {
        LoginResult loginResult = new LoginResult();
        UserRole userRole = UserRole.getUserRole(type);
        switch (userRole){
            case CUSTOMER:
                // System.out.println("customer");
                loginResult = checkCustomer(username, password);
                break;
            case RESTAURANT:
                // System.out.println("restaurant");
                loginResult = checkRestaurant(username, password);
                break;
            case MANAGER:
                // System.out.println("manager");
                loginResult = checkManager(username, password);
                break;
        }
        // System.out.println("result:"+result);
        return loginResult;
    }

    private LoginResult checkCustomer(String username, String password){
        LoginResult loginResult = new LoginResult();
        boolean result = customerRepository.existsByUsernameAndPassword(username,password);
        int level = 0;
        if(result){
            Customer customer = customerRepository.findByUsername(username).get();
            result = (customer.isEmailValid()&&customer.isValid());
            level = customer.getLevel();
        }
        loginResult.setLevel(level);
        loginResult.setSuccess(result);
        return loginResult;
    }

    private LoginResult checkRestaurant(String username, String password){
        LoginResult loginResult = new LoginResult();
        // 餐厅用户名一定是数值
        if(!isNum(username)){
            loginResult.setSuccess(false);
            return loginResult;
        }
        boolean result = restaurantRepository.existsByRidAndPassword(Integer.parseInt(username),password);
        if(result){
            Restaurant restaurant = restaurantRepository.findByRid(Integer.parseInt(username)).get();
            // 邮箱已验证
            result = restaurant.isEmailValid();
        }
        loginResult.setSuccess(result);
        return loginResult;
    }

    private LoginResult checkManager(String username, String password){
        LoginResult loginResult = new LoginResult();
        loginResult.setSuccess(managerRepository.existsByUidAndPassword(username,password));
        return loginResult;
    }

    private boolean isNum(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

}
