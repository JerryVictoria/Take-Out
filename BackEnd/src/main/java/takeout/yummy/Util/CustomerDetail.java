package takeout.yummy.Util;

import takeout.yummy.entity.OrderDetail;
import takeout.yummy.entity.OrderList;
import takeout.yummy.enums.RestaurantType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/19
 */
public class CustomerDetail {
    private LocalDateTime time;
    private String restaurantName;
    private String type;
    private List<OrderBaseInfo> orderBaseInfoList;
    private double totalPrice;
    private boolean cancelled;

    public CustomerDetail() {
    }

    public CustomerDetail(OrderList orderList, List<OrderDetail> orderDetailList) {
        this.time = orderList.getTime();
        this.restaurantName = orderList.getRestaurant().getName();
        this.type = RestaurantType.getTypeStr(orderList.getRestaurant().getType());
        this.totalPrice = orderList.getTotalPrice();
        this.cancelled = orderList.isCancelled();
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
