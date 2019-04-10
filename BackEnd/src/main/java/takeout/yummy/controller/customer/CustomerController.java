package takeout.yummy.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import takeout.yummy.Util.*;
import takeout.yummy.service.customer.CustomerService;
import takeout.yummy.service.restaurant.RestaurantService;

import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/5
 */
@RestController
public class CustomerController {

    private CustomerService customerService;
    private RestaurantService restaurantService;

    @Autowired
    public CustomerController(CustomerService customerService, RestaurantService restaurantService) {
        this.customerService = customerService;
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/DeleteCustomer")
    public void deleteCustomer(@RequestParam String username){
        customerService.deleteCustomer(username);
    }

    @GetMapping(value = "/GetCustomerInfo")
    public CustomerInfo getCustomerInfo(@RequestParam String username){
        CustomerInfo customerInfo = new CustomerInfo(customerService.findCustomerByUsername(username),customerService.getCustomerAddress(username));
        return customerInfo;
    }

    @GetMapping(value = "/ChangeBaseCustomerInfo")
    public void changeCustomerInfo(@RequestParam String username,@RequestParam String name, @RequestParam String phone){
        customerService.changeInfo(username,name,phone);
    }

    @GetMapping(value = "/AddCustomerAddress")
    public void addCustomerAddress(@RequestParam String username,@RequestParam String address){
        customerService.addCustomerAddress(username, address);
    }

    @GetMapping(value = "/GetRestaurants")
    public List<MarketInfo> getRestaurants(){
        return restaurantService.getRestaurants();
    }

    @GetMapping(value = "/GetRestaurantDetail")
    public RestaurantProductDetail getRestaurantDetail(@RequestParam int rid){
        RestaurantProductDetail restaurantProductDetail = restaurantService.getRestaurantDetail(rid);
        return restaurantProductDetail;
    }

    @PostMapping(value = "/SubmitOrder")
    public SubmitOrderResult submitOrder(@RequestBody OrderInfo orderInfo){
        return restaurantService.submitOrder(orderInfo);
    }

    @GetMapping(value = "/TrackOrder")
    public List<OrderTrackInfo> getTrackOrder(@RequestParam String username){
        return restaurantService.findAllValidOrder(username);
    }

    @GetMapping(value = "/Pay")
    public boolean payOrder(@RequestParam Long oid,@RequestParam String username){
        return customerService.payOrder(oid,username);
    }

    @GetMapping(value = "/CancelOrder")
    public void cancelOrder(@RequestParam Long oid){
        customerService.cancelOrder(oid);
    }

    @GetMapping(value = "/CheckCustomerDetail")
    public List<CustomerDetail> checkCustomerDetail(@RequestParam String username){
        return customerService.checkCustomerDetail(username);
    }

    @GetMapping(value = "/GetAllAddress")
    public List<String> getAllAddress(@RequestParam String username){
        return customerService.getAllAddress(username);
    }

    @GetMapping(value = "/ReceiveOrder")
    public void receiveOrder(@RequestParam long oid){
        customerService.receiveOrder(oid);
    }

    @GetMapping(value = "/GetAllEmails")
    public List<String> getAllEmails(){
        return customerService.getCustomerValidEmails();
    }
}
