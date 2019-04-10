package takeout.yummy.Util;

import takeout.yummy.entity.OrderDetail;
import takeout.yummy.entity.OrderList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/16
 */
public class OrderRecord {
    private Long oid;
    private double totalPrice;
    private List<OrderBaseInfo> orderBaseInfoList;
    private boolean isCancelled;
    private LocalDateTime time;
    private String username;
    private String phone;
    private String address;
    private boolean isProcessed;
    private double restaurantProfit;

    public OrderRecord() {
    }

    public OrderRecord(OrderList orderList, List<OrderDetail> orderDetailList) {
        this.oid = orderList.getOid();
        this.totalPrice = orderList.getTotalPrice();
        this.isCancelled = orderList.isCancelled();
        this.time = orderList.getTime();
        this.username = orderList.getCustomer().getUsername();
        this.phone = orderList.getCustomer().getPhone();
        this.address = orderList.getAddress();
        this.isProcessed = orderList.isProcessed();
        this.restaurantProfit = orderList.getRestaurant_profit();
        orderBaseInfoList = new ArrayList<>();
        for(int i=0;i<orderDetailList.size();i++){
            OrderDetail orderDetail = orderDetailList.get(i);
            orderBaseInfoList.add(new OrderBaseInfo(orderDetail.getProductInfo().getPid(),orderDetail.getQuantity(),orderDetail.getProductInfo().getProductName(),orderDetail.getProductInfo().getPrice()));
        }
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderBaseInfo> getOrderBaseInfoList() {
        return orderBaseInfoList;
    }

    public void setOrderBaseInfoList(List<OrderBaseInfo> orderBaseInfoList) {
        this.orderBaseInfoList = orderBaseInfoList;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    public double getRestaurantProfit() {
        return restaurantProfit;
    }

    public void setRestaurantProfit(double restaurantProfit) {
        this.restaurantProfit = restaurantProfit;
    }
}
