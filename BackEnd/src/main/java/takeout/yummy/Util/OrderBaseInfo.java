package takeout.yummy.Util;

import takeout.yummy.entity.OrderDetail;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/14
 */
public class OrderBaseInfo {
    private Long pid;
    private int quantity;
    private String name;
    private double price;

    public OrderBaseInfo() {
    }

    public OrderBaseInfo(Long pid, int quantity, String name, double price) {
        this.pid = pid;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }


    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
