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
public class OrderTrackInfo {

    private Long oid;

    private double totalPrice;

    private List<OrderBaseInfo> orderBaseInfoList;

    private boolean isPayed;

    private boolean isCancelled;

    private LocalDateTime time;

    private boolean received;

    public OrderTrackInfo() {
    }

    public OrderTrackInfo(OrderList orderList, List<OrderDetail> orderDetailList) {
        this.oid = orderList.getOid();
        this.totalPrice = orderList.getTotalPrice();
        this.isPayed = orderList.isPaid();
        this.isCancelled = orderList.isCancelled();
        this.time = orderList.getTime();
        this.received = orderList.isReceived();
        this.orderBaseInfoList = new ArrayList<>();
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

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
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

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
