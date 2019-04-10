package takeout.yummy.Util;

import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/14
 */
public class OrderInfo {
    private List<OrderBaseInfo> orderBaseInfoList;
    private String username;
    private int rid;
    private double totalPrice;
    private String address;

    public OrderInfo() {
    }

    public OrderInfo(List<OrderBaseInfo> orderBaseInfoList, String username, int rid, double totalPrice,String address) {
        this.orderBaseInfoList = orderBaseInfoList;
        this.username = username;
        this.rid = rid;
        this.totalPrice = totalPrice;
        this.address = address;
    }

    public List<OrderBaseInfo> getOrderBaseInfoList() {
        return orderBaseInfoList;
    }

    public void setOrderBaseInfoList(List<OrderBaseInfo> orderBaseInfoList) {
        this.orderBaseInfoList = orderBaseInfoList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
