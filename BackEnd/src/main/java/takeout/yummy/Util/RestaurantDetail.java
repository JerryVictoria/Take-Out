package takeout.yummy.Util;

import takeout.yummy.entity.OrderDetail;
import takeout.yummy.entity.OrderList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/19
 */
public class RestaurantDetail {
    private LocalDateTime time;
    private String username;
    private int level;
    private List<OrderBaseInfo> orderBaseInfoList;
    private double totalPrice;
    private boolean cancelled;
    private double restaurantProfit;

    public RestaurantDetail() {
    }

    public RestaurantDetail(OrderList orderList, List<OrderDetail> orderDetailList) {
        this.time = orderList.getTime();
        this.username = orderList.getCustomer().getUsername();
        this.level = orderList.getCustomer().getLevel();
        this.totalPrice = orderList.getTotalPrice();
        this.cancelled = orderList.isCancelled();
        this.restaurantProfit = orderList.getRestaurant_profit();
        orderBaseInfoList = new ArrayList<>();
        for(int i=0;i<orderDetailList.size();i++){
            OrderDetail orderDetail = orderDetailList.get(i);
            orderBaseInfoList.add(new OrderBaseInfo(orderDetail.getProductInfo().getPid(),orderDetail.getQuantity(),orderDetail.getProductInfo().getProductName(),orderDetail.getProductInfo().getPrice()));
        }
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

    public List<OrderBaseInfo> getOrderBaseInfoList() {
        return orderBaseInfoList;
    }

    public void setOrderBaseInfoList(List<OrderBaseInfo> orderBaseInfoList) {
        this.orderBaseInfoList = orderBaseInfoList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public double getRestaurantProfit() {
        return restaurantProfit;
    }

    public void setRestaurantProfit(double restaurantProfit) {
        this.restaurantProfit = restaurantProfit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
