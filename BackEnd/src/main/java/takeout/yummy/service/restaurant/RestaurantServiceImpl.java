package takeout.yummy.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import takeout.yummy.Util.*;
import takeout.yummy.dao.*;
import takeout.yummy.entity.*;
import takeout.yummy.enums.RestaurantType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/6
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;
    private ApprovalRepository approvalRepository;
    private ProductInfoRepository productInfoRepository;
    private DiscountInfoRepository discountInfoRepository;
    private OrderListRepository orderListRepository;
    private CustomerRepository customerRepository;
    private OrderDetailRepository orderDetailRepository;
    private SetMealDetailRepository setMealDetailRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ApprovalRepository approvalRepository,
                                 ProductInfoRepository productInfoRepository,DiscountInfoRepository discountInfoRepository,
                                 OrderListRepository orderListRepository,CustomerRepository customerRepository,
                                 OrderDetailRepository orderDetailRepository,
                                 SetMealDetailRepository setMealDetailRepository) {
        this.restaurantRepository = restaurantRepository;
        this.approvalRepository = approvalRepository;
        this.productInfoRepository = productInfoRepository;
        this.discountInfoRepository = discountInfoRepository;
        this.orderListRepository = orderListRepository;
        this.customerRepository = customerRepository;
        this.orderListRepository = orderListRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.setMealDetailRepository = setMealDetailRepository;
    }

    @Override
    public RestaurantInfo getRestaurantInfo(Integer rid) {
        Restaurant restaurant = restaurantRepository.findByRid(rid).get();
        return new RestaurantInfo(restaurant);
    }

    @Override
    public void saveApprovalInfo(Integer rid, String address) {
        Restaurant restaurant = restaurantRepository.findByRid(rid).get();
        Approval approval = new Approval();
        approval.setRestaurant(restaurant);
        approval.setAddress(address);
        approvalRepository.save(approval);
    }

    @Override
    public void saveApprovalInfo(Integer rid, String name, String phone, String type) {
        Restaurant restaurant = restaurantRepository.findByRid(rid).get();
        Approval approval = new Approval();
        approval.setRestaurant(restaurant);
        approval.setPhone(phone);
        approval.setName(name);
        approval.setType(RestaurantType.getRestaurantType(type));
        approvalRepository.save(approval);
    }

    @Override
    public List<ApprovalInfo> getApprovals() {
        List<Approval> approvalList = approvalRepository.findAllByIsProcessed(false);
        List<ApprovalInfo> approvalInfoList = new ArrayList<>();
        for(int i=0;i<approvalList.size();i++){
            approvalInfoList.add(new ApprovalInfo(approvalList.get(i)));
        }
        return approvalInfoList;
    }

    @Override
    public void releaseProductInfo(ProductReleaseInfo productReleaseInfo) {
        Restaurant restaurant = restaurantRepository.findByRid(productReleaseInfo.getRid()).get();
        ProductInfo productInfo = new ProductInfo(productReleaseInfo,restaurant);
        productInfoRepository.save(productInfo);
    }

    @Override
    public void releaseDiscountInfo(DiscountReleaseInfo discountReleaseInfo) {
        Restaurant restaurant =restaurantRepository.findByRid(discountReleaseInfo.getRid()).get();
        DiscountInfo discountInfo = new DiscountInfo(discountReleaseInfo,restaurant);
        discountInfoRepository.save(discountInfo);
    }

    @Override
    public List<MarketInfo> getRestaurants() {
        List<Restaurant> restaurantList = restaurantRepository.findAllByEmailValid(true);
        List<MarketInfo> marketInfoList = new ArrayList<>();
        for(int i=0;i<restaurantList.size();i++){
            marketInfoList.add(new MarketInfo(restaurantList.get(i)));
        }
        return marketInfoList;
    }

    @Override
    public RestaurantProductDetail getRestaurantDetail(int rid) {
        LocalDate now = LocalDate.now();
        List<ProductInfo> productInfoList = productInfoRepository.findAllByRestaurantRidAndStartTimeBeforeAndEndTimeAfter(rid,now.plusDays(1),now);
        List<MarketProductInfo> marketProductInfoList = getMarketProductInfoList(productInfoList);
        List<DiscountInfo> discountInfoList = discountInfoRepository.findAllByRestaurantRidAndStartTimeBeforeAndEndTimeAfter(rid,now.plusDays(1),now);
        List<MarketDiscountInfo> marketDiscountInfoList = new ArrayList<>();
        for(int i=0;i<discountInfoList.size();i++){
            marketDiscountInfoList.add(new MarketDiscountInfo(discountInfoList.get(i)));
        }
        Collections.sort(marketDiscountInfoList);
        RestaurantProductDetail restaurantProductDetail = new RestaurantProductDetail(marketProductInfoList, marketDiscountInfoList);
        return restaurantProductDetail;
    }

    @Override
    public SubmitOrderResult submitOrder(OrderInfo orderInfo) {

        SubmitOrderResult submitOrderResult = new SubmitOrderResult();
        submitOrderResult.setSuccess(true);
        submitOrderResult.setMessage("请在15分钟内付款！");

        //判断库存
        List<OrderBaseInfo> list = orderInfo.getOrderBaseInfoList();
        List<ProductInfo> productInfoList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            OrderBaseInfo orderBaseInfo = list.get(i);
            ProductInfo productInfo = productInfoRepository.findByPid(orderBaseInfo.getPid()).get();
            productInfoList.add(productInfo);
            if(productInfo.getQuantity()<orderBaseInfo.getQuantity()){
                submitOrderResult.setSuccess(false);
                submitOrderResult.setMessage(productInfo.getProductName()+"库存不足！");
                return submitOrderResult;
            }
        }
        // 减库存
        for(int i=0;i<list.size();i++){
            ProductInfo productInfo = productInfoList.get(i);
            productInfo.setQuantity(productInfo.getQuantity()-list.get(i).getQuantity());
            productInfoRepository.save(productInfo);
        }

        // OrderList
        OrderList orderList = new OrderList();

        String username = orderInfo.getUsername();
        Customer customer = customerRepository.findByUsername(username).get();
        orderList.setCustomer(customer);

        int rid = orderInfo.getRid();
        Restaurant restaurant = restaurantRepository.findByRid(rid).get();
        orderList.setRestaurant(restaurant);

        String customerAddress = orderInfo.getAddress();
        String restaurantAddress = restaurant.getAddress();
        String customerAddr = customerAddress.substring(0,customerAddress.indexOf("区")+1);
        String restaurantAddr = restaurantAddress.substring(0,restaurantAddress.indexOf("区")+1);
        if(customerAddr.equals(restaurantAddr)){
            LocalDateTime time = LocalDateTime.now().plusMinutes(40);
            orderList.setTime(time);
        }else{
            LocalDateTime time = LocalDateTime.now().plusMinutes(80);
            orderList.setTime(time);
        }

        orderList.setAddress(orderInfo.getAddress());

        double totalPrice = orderInfo.getTotalPrice();
        orderList.setTotalPrice(totalPrice);

        orderList.setRestaurant_profit(totalPrice*0.7);

        orderList.setProfit(totalPrice*0.3);

        orderListRepository.save(orderList);

        // OrderDetail
        for(int i=0;i<list.size();i++){
            OrderBaseInfo orderBaseInfo = list.get(i);
            OrderDetail orderDetail = new OrderDetail(orderList,productInfoList.get(i),orderBaseInfo.getQuantity());
            orderDetailRepository.save(orderDetail);
        }
        Long oid = orderList.getOid();

        // 2 min 未付款取消订单
        Timer timer = new Timer();
        timer.schedule(new PayDDLTask(oid,orderListRepository,productInfoRepository,orderDetailRepository),1000*120);//2 min = 120s

        return submitOrderResult;
    }

    @Override
    public List<OrderTrackInfo> findAllValidOrder(String username) {
        List<OrderList> orderLists = orderListRepository.findAllByCustomerUsernameAndIsValid(username,true);
        Collections.sort(orderLists, new Comparator<OrderList>() {
            @Override
            public int compare(OrderList o1, OrderList o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });
        List<OrderTrackInfo> orderTrackInfoList = new ArrayList<>();
        for(int i=0;i<orderLists.size();i++){
            OrderList orderList = orderLists.get(i);
            orderTrackInfoList.add(new OrderTrackInfo(orderList,
                    orderDetailRepository.findAllByOrderListOid(orderList.getOid())));
        }
        return orderTrackInfoList;
    }

    @Override
    public List<OrderRecord> findAllUntreatedOrder(int rid) {
        List<OrderList> orderLists = orderListRepository.findAllByRestaurantRidAndIsProcessedAndIsPaid(rid,false,true);
        List<OrderRecord> orderRecordList = new ArrayList<>();
        for(int i=0;i<orderLists.size();i++){
            OrderList orderList = orderLists.get(i);
            List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderListOid(orderList.getOid());
            orderRecordList.add(new OrderRecord(orderList,orderDetails));
        }
        return orderRecordList;
    }

    @Override
    public List<OrderRecord> findAllRecord(int rid) {
        List<OrderList> orderLists = orderListRepository.findAllByRestaurantRidAndIsProcessedAndIsPaid(rid,true,true);
        List<OrderRecord> orderRecordList = new ArrayList<>();
        for(int i=0;i<orderLists.size();i++){
            OrderList orderList = orderLists.get(i);
            List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderListOid(orderList.getOid());
            orderRecordList.add(new OrderRecord(orderList,orderDetails));
        }
        return orderRecordList;
    }

    @Override
    public void takeOrder(Long oid) {
        OrderList orderList = orderListRepository.findByOid(oid).get();
        orderList.setProcessed(true);
        orderListRepository.save(orderList);
        Restaurant restaurant = orderList.getRestaurant();
        //restaurant get money
        restaurant.setMoney(restaurant.getMoney()+orderList.getRestaurant_profit());
        restaurantRepository.save(restaurant);
    }

    @Override
    public void takeCancelledOrder(Long oid, boolean isMade) {
        OrderList orderList = orderListRepository.findByOid(oid).get();
        orderList.setProcessed(true);
        orderList.setMade(isMade);
        // money change
        Customer customer = orderList.getCustomer();
        Restaurant restaurant = orderList.getRestaurant();
        if(isMade){
            //customer get 50%
            customer.setMoney(customer.getMoney()+orderList.getTotalPrice()*0.5);
            customer.setConsumption(customer.getConsumption()-orderList.getTotalPrice()*0.5);
            // restaurant** & dot
            restaurant.setMoney(restaurant.getMoney()-orderList.getTotalPrice()*0.7*0.5);
            orderList.setProfit(orderList.getProfit()*0.5);
            orderList.setRestaurant_profit(orderList.getRestaurant_profit()*0.5);
        }else{
            //customer get 90%
            customer.setMoney(customer.getMoney()+orderList.getTotalPrice()*0.9);
            customer.setConsumption(customer.getConsumption()-orderList.getTotalPrice()*0.9);
            // restaurant** & dot
            restaurant.setMoney(restaurant.getMoney()-orderList.getTotalPrice()*0.7*0.9);
            orderList.setProfit(orderList.getProfit()*0.1);
            orderList.setRestaurant_profit(orderList.getRestaurant_profit()*0.1);
        }

        // level change
        LevelChange.change(customer);
        
        // add stock 加库存
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderListOid(oid);
        for(int i=0;i<orderDetails.size();i++){
            ProductInfo productInfo = orderDetails.get(i).getProductInfo();
            productInfo.setQuantity(productInfo.getQuantity()+orderDetails.get(i).getQuantity());
            productInfoRepository.save(productInfo);
        }

        orderListRepository.save(orderList);
        customerRepository.save(customer);
        restaurantRepository.save(restaurant);
    }

    @Override
    public List<RestaurantDetail> checkOrder(int rid) {
        List<OrderList> orderLists = orderListRepository.findAllByRestaurantRidAndIsProcessedAndIsPaid(rid,true,true);
        List<RestaurantDetail> restaurantDetails = new ArrayList<>();
        for(int i=0;i<orderLists.size();i++){
            OrderList orderList = orderLists.get(i);
            restaurantDetails.add(new RestaurantDetail(orderList,orderDetailRepository.findAllByOrderListOid(orderList.getOid())));
        }
        return restaurantDetails;
    }

    @Override
    public List<MarketProductInfo> getRestaurantAllProduct(int rid) {
        LocalDate now = LocalDate.now();
        List<ProductInfo> productInfoList = productInfoRepository.findAllByRestaurantRidAndEndTimeAfter(rid,now);
        return getMarketProductInfoList(productInfoList);
    }

    private List<MarketProductInfo> getMarketProductInfoList (List<ProductInfo> productInfoList){
        List<MarketProductInfo> marketProductInfoList = new ArrayList<>();
        for(int i=0;i<productInfoList.size();i++){
            MarketProductInfo marketProductInfo = new MarketProductInfo(productInfoList.get(i));
            if(marketProductInfo.isSetMeal()){
                List<OrderBaseInfo> orderBaseInfoList = new ArrayList<>();
                List<SetMealDetail> setMealDetails = setMealDetailRepository.findAllBySetMealPid(marketProductInfo.getPid());
                for(int j=0;j<setMealDetails.size();j++){
                    SetMealDetail setMealDetail = setMealDetails.get(j);
                    orderBaseInfoList.add(new OrderBaseInfo(setMealDetail.getProductInfo().getPid(),setMealDetail.getQuantity()
                            ,setMealDetail.getProductInfo().getProductName(),setMealDetail.getProductInfo().getPrice()));
                }
                marketProductInfo.setOrderBaseInfoList(orderBaseInfoList);
            }
            marketProductInfoList.add(marketProductInfo);
        }
        return marketProductInfoList;
    }

    @Override
    public List<MarketDiscountInfo> getRestaurantAllDiscount(int rid) {
        LocalDate now = LocalDate.now();
        List<DiscountInfo> discountInfoList = discountInfoRepository.findAllByRestaurantRidAndEndTimeAfter(rid,now);
        List<MarketDiscountInfo> marketDiscountInfoList = new ArrayList<>();
        for(int i=0;i<discountInfoList.size();i++){
            marketDiscountInfoList.add(new MarketDiscountInfo(discountInfoList.get(i)));
        }
        Collections.sort(marketDiscountInfoList);
        return marketDiscountInfoList;
    }

    @Override
    public void releaseSetMealInfo(SetMealInfo setMealInfo) {
        //list
        ProductInfo productInfo = new ProductInfo();
        productInfo.setStartTime(TimeFormatChange.getLocalDateTime(setMealInfo.getStartTime()));
        productInfo.setEndTime(TimeFormatChange.getLocalDateTime(setMealInfo.getEndTime()));
        productInfo.setRestaurant(restaurantRepository.findByRid(setMealInfo.getRid()).get());
        productInfo.setProductName(setMealInfo.getSetMealName());
        productInfo.setType(setMealInfo.getType());
        productInfo.setPrice(setMealInfo.getPrice());
        productInfo.setQuantity(setMealInfo.getQuantity());
        productInfo.setDescription(setMealInfo.getDescription());
        productInfo.setSetMeal(true);
        productInfo = productInfoRepository.save(productInfo);
        //detail
        List<OrderBaseInfo> orderBaseInfos = setMealInfo.getOrderBaseInfoList();
        for(int i=0;i<orderBaseInfos.size();i++){
            SetMealDetail setMealDetail = new SetMealDetail();
            setMealDetail.setProductInfo(productInfoRepository.findByPid(orderBaseInfos.get(i).getPid()).get());
            setMealDetail.setQuantity(orderBaseInfos.get(i).getQuantity());
            setMealDetail.setSetMeal(productInfo);
            setMealDetailRepository.save(setMealDetail);
        }
    }

    @Override
    public boolean waitToApprove(int rid) {
        List<Approval> approvalList = approvalRepository.findAllByRestaurantRidAndIsProcessed(rid,false);
        if(approvalList==null||approvalList.size()==0){
            return false;
        }
        return true;
    }
}
