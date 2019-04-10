package takeout.yummy.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import takeout.yummy.dao.ApprovalRepository;
import takeout.yummy.dao.CustomerRepository;
import takeout.yummy.dao.OrderListRepository;
import takeout.yummy.dao.RestaurantRepository;
import takeout.yummy.entity.Approval;
import takeout.yummy.entity.Customer;
import takeout.yummy.entity.OrderList;
import takeout.yummy.entity.Restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/6
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    private ApprovalRepository approvalRepository;
    private RestaurantRepository restaurantRepository;
    private CustomerRepository customerRepository;
    private OrderListRepository orderListRepository;

    @Autowired
    public ManagerServiceImpl(ApprovalRepository approvalRepository, RestaurantRepository restaurantRepository,
                              CustomerRepository customerRepository, OrderListRepository orderListRepository) {
        this.approvalRepository = approvalRepository;
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
        this.orderListRepository = orderListRepository;
    }


    @Override
    public void agreeApproval(int id) {
        Approval approval = approvalRepository.findById(id).get();
        Restaurant restaurant = approval.getRestaurant();
        if(approval.getAddress()!=null && approval.getAddress().length()>0){
            restaurant.setAddress(approval.getAddress());
        }
        if(approval.getName()!=null && approval.getName().length()>0){
            restaurant.setName(approval.getName());
        }
        if(approval.getPhone()!=null && approval.getPhone().length()>0){
            restaurant.setPhone(approval.getPhone());
        }
        if(approval.getType()!=null){
            restaurant.setType(approval.getType());
        }
        restaurantRepository.save(restaurant);
        approval.setProcessed(true);
        approvalRepository.save(approval);
    }

    @Override
    public void disagreeApproval(int id) {
        System.out.println(id);
        Approval approval = approvalRepository.findById(id).get();
        approval.setProcessed(true);
        approvalRepository.save(approval);
    }

    @Override
    public ArrayList<Integer> getCustomerData() {
        List<Customer> customers = customerRepository.findAllByEmailValid(true);
        int count1 = 0, count2 = 0, count3 = 0;
        for(int i=0;i<customers.size();i++){
            switch (customers.get(i).getLevel()){
                case 1: count1++; break;
                case 2: count2++; break;
                case 3: count3++; break;
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(count1);
        result.add(count2);
        result.add(count3);
        return result;
    }

    @Override
    public ArrayList<Integer> getRestaurantData() {
        List<Restaurant> restaurants = restaurantRepository.findAllByEmailValid(true);
        int countChinese = 0, countWestern = 0, countLeisure = 0;
        for(int i=0;i<restaurants.size();i++){
            switch (restaurants.get(i).getType()){
                case CHINESE_MEAL: countChinese++; break;
                case WESTERN_MEAL: countWestern++; break;
                case LEISURE_MEAL: countLeisure++; break;
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(countChinese);
        result.add(countWestern);
        result.add(countLeisure);
        return result;
    }

    @Override
    public ArrayList<Double> getProfitData(String year) {
        ArrayList<Double> result = new ArrayList<>();
        for(int i=0;i<=10;i++){
            LocalDateTime startTime = LocalDateTime.of(Integer.parseInt(year),i+1,1,0,0,0);
            LocalDateTime endTime = LocalDateTime.of(Integer.parseInt(year),i+2,1,0,0,0);
            result.add(getMonthTotalProfit(startTime,endTime));
        }
        LocalDateTime startTime = LocalDateTime.of(Integer.parseInt(year),12,1,0,0,0);
        LocalDateTime endTime = LocalDateTime.of(Integer.parseInt(year)+1,1,1,0,0,0);
        result.add(getMonthTotalProfit(startTime,endTime));
        return result;
    }

    private double getMonthTotalProfit(LocalDateTime startTime, LocalDateTime endTime){
        List<OrderList> orderLists = orderListRepository.findAllByTimeAfterAndTimeBeforeAndIsProcessed(startTime,endTime,true);
        double price = 0.0;
        for(int j=0;j<orderLists.size();j++){
            price+=orderLists.get(j).getProfit();
        }
        return price;
    }


}
