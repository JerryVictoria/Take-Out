package takeout.yummy.service.customer;

import takeout.yummy.Util.CustomerDetail;
import takeout.yummy.entity.Customer;
import takeout.yummy.entity.CustomerAddress;

import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/4
 */
public interface CustomerService {

    public void changeInfo(Customer customer);

    public void changeInfo(String username, String name, String phone);

    public void deleteCustomer(String username);

    public Customer findCustomerByUsername(String username);

    public void addCustomerAddress(String username, String address);

    public List<String> getCustomerAddress(String username);

    public boolean payOrder(Long oid, String username);

    public void cancelOrder(Long oid);

    public List<CustomerDetail> checkCustomerDetail(String username);

    public List<String> getAllAddress(String username);

    public void receiveOrder(Long oid);

    public List<String> getCustomerValidEmails();
}
