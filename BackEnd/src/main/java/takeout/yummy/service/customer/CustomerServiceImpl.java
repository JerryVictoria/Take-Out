package takeout.yummy.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import takeout.yummy.Util.CustomerDetail;
import takeout.yummy.Util.LevelChange;
import takeout.yummy.dao.CustomerAddressRepository;
import takeout.yummy.dao.CustomerRepository;
import takeout.yummy.dao.OrderDetailRepository;
import takeout.yummy.dao.OrderListRepository;
import takeout.yummy.entity.Customer;
import takeout.yummy.entity.CustomerAddress;
import takeout.yummy.entity.OrderList;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/4
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CustomerAddressRepository customerAddressRepository;
    private OrderListRepository orderListRepository;
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerAddressRepository customerAddressRepository,
                               OrderListRepository orderListRepository,OrderDetailRepository orderDetailRepository) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.orderListRepository = orderListRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public void changeInfo(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void changeInfo(String username, String name, String phone) {
        Customer customer = customerRepository.findByUsername(username).get();
        customer.setName(name);
        customer.setPhone(phone);
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(String username) {
        Customer customer = customerRepository.findByUsername(username).get();
        customer.setValid(false);
        customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerByUsername(String username) {
        return customerRepository.findByUsername(username).get();
    }

    @Override
    public void addCustomerAddress(String username, String address) {
        Customer customer = customerRepository.findByUsername(username).get();
        CustomerAddress customerAddress = new CustomerAddress(customer,address);
        customerAddressRepository.save(customerAddress);
    }

    @Override
    public List<String> getCustomerAddress(String username) {
        List<String> addressList = new ArrayList<>();
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAllByCustomerUsername(username);
        for(int i=0;i<customerAddressList.size();i++){
            addressList.add(customerAddressList.get(i).getAddress());
        }
        return addressList;
    }

    @Override
    public boolean payOrder(Long oid, String username) {
        OrderList orderList = orderListRepository.findByOid(oid).get();
        Customer customer = customerRepository.findByUsername(username).get();
        if(customer.getMoney()<orderList.getTotalPrice()){
            return false;
        }else{
            customer.setMoney(customer.getMoney()-orderList.getTotalPrice());
            customer.setConsumption(customer.getConsumption()+orderList.getTotalPrice());
            LevelChange.change(customer);
            customerRepository.save(customer);
            orderList.setPaid(true);
            orderListRepository.save(orderList);
            //30min默认确认收货
            Timer timer = new Timer();
            timer.schedule(new ReceiveDDLTask(orderList,orderListRepository),1000*1800);//30min = 1800s
            return true;
        }
    }

    @Override
    public void cancelOrder(Long oid) {
        OrderList orderList = orderListRepository.findByOid(oid).get();
        orderList.setProcessed(false);
        orderList.setCancelled(true);
        orderListRepository.save(orderList);
    }

    @Override
    public List<CustomerDetail> checkCustomerDetail(String username) {
        List<OrderList> orderLists = orderListRepository.findAllByCustomerUsernameAndIsPaid(username,true);
        List<CustomerDetail> customerDetailList = new ArrayList<>();
        for(int i=0;i<orderLists.size();i++){
            OrderList orderList = orderLists.get(i);
            customerDetailList.add(new CustomerDetail(orderList,orderDetailRepository.findAllByOrderListOid(orderList.getOid())));
        }
        return customerDetailList;
    }

    @Override
    public List<String> getAllAddress(String username) {
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAllByCustomerUsername(username);
        List<String> addressList = new ArrayList<>();
        for(int i=0;i<customerAddressList.size();i++){
            addressList.add(customerAddressList.get(i).getAddress());
        }
        return addressList;
    }

    @Override
    public void receiveOrder(Long oid) {
        OrderList orderList = orderListRepository.findByOid(oid).get();
        orderList.setReceived(true);
        orderListRepository.save(orderList);
    }

    @Override
    public List<String> getCustomerValidEmails() {
        List<Customer> customerList = customerRepository.findAllByEmailValid(true);
        List<String> emails = new ArrayList<>();
        for(Customer customer:customerList){
            emails.add(customer.getEmail());
        }
        return emails;
    }
}
