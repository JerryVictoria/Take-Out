package takeout.yummy.service.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import takeout.yummy.Util.RegisterInfo;
import takeout.yummy.dao.CustomerRepository;
import takeout.yummy.dao.RestaurantRepository;
import takeout.yummy.entity.Customer;
import takeout.yummy.entity.Restaurant;
import takeout.yummy.enums.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/4
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private CustomerRepository customerRepository;
    private RestaurantRepository restaurantRepository;
    private MailService mailService;

    @Autowired
    public RegisterServiceImpl(CustomerRepository customerRepository, RestaurantRepository restaurantRepository, MailService mailService) {
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.mailService = mailService;
    }


    @Override
    public String registerAndSendEmail(RegisterInfo registerInfo) {
        UserRole userRole = UserRole.getUserRole(registerInfo.getRole());
        String result = "";
        switch (userRole){
            case CUSTOMER:
                result = registerCustomer(registerInfo);
                break;
            case RESTAURANT:
                result = registerRestaurant(registerInfo);
                break;
        }
        return result;
    }

    @Override
    public void checkEmail(String emailCode) {
        //如果用户不等于null，把emailValid修改true
        if(customerRepository.existsByEmailCode(emailCode)){
            Customer customer = customerRepository.findByEmailCode(emailCode).get();
            customer.setEmailValid(true);
            customerRepository.save(customer);
        }
        if(restaurantRepository.existsByEmailCode(emailCode)){
            Restaurant restaurant = restaurantRepository.findByEmailCode(emailCode).get();
            restaurant.setEmailValid(true);
            restaurantRepository.save(restaurant);
        }
    }

    private String registerCustomer(RegisterInfo registerInfo){
        //0 no same username && right email
        if(customerRepository.existsByUsername(registerInfo.getUsername())){
            return "用户名不可重名！";
        }
        if(!checkEmailForm(registerInfo.getEmail())){
            return "邮箱格式错误";
        }
        // 1 save info
        Customer customer = new Customer();
        customer.setUsername(registerInfo.getUsername());
        customer.setPassword(registerInfo.getPassword());
        customer.setEmail(registerInfo.getEmail());
        customer.setEmailCode(((int)(Math.random()*10000000))+registerInfo.getUsername());
        customerRepository.save(customer);
        // 2 send email
        sendEmail(customer.getUsername(),customer.getEmail(),customer.getEmailCode());
        return "点击邮箱验证后即可登录";
    }

    private String registerRestaurant(RegisterInfo registerInfo){
        // 0 right email
        if(!checkEmailForm(registerInfo.getEmail())){
            return "邮箱格式错误";
        }
        // 1 save info
        Restaurant restaurant = new Restaurant();
        restaurant.setPassword(registerInfo.getPassword());
        restaurant.setEmail(registerInfo.getEmail());
        String code = ((int)(Math.random()*10000000))+""+LocalDateTime.now();
        restaurant.setEmailCode(code);
        restaurantRepository.save(restaurant);
        // 2 send email
        restaurant = restaurantRepository.findByEmailCode(code).get();
        sendEmail(processRid(restaurant.getRid()),restaurant.getEmail(),restaurant.getEmailCode());
        return "点击邮箱验证后即可登录，您的用户名将在邮件标题中给出！";
    }

    private void sendEmail(String username,String email, String emailCode){
        //获取激活码
        //主题
        String subject = username+"，您好！这是来自Yummy！网站的激活邮件";
        //user/checkCode?code=code(激活码)是我们点击邮件链接之后根据激活码查询用户，如果存在说明一致，将用户状态修改为“1”激活
        //上面的激活码发送到用户注册邮箱
        String context = "<a href=\"http://localhost:8082/checkCode?code="+emailCode+"\">激活请点击:"+emailCode+"</a>";
        //发送激活邮件
        mailService.sendHtmlMail (email,subject,context);
    }

    private String processRid(int i){
        String result = i+"";
        while(result.length()<7){
            result = "0"+result;
        }
        return result;
    }

    private boolean checkEmailForm(String email) {

        if(email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            return true;
        }
        else {
            return false;
        }

    }
}
