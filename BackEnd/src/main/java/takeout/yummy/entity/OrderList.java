package takeout.yummy.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/1
 */
@Entity
public class OrderList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;//PK

    private boolean isPaid = false;

    @ManyToOne(cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name="username",referencedColumnName="username") // 外键设置为username
    private Customer customer;//FK

    @ManyToOne(cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name="rid",referencedColumnName="rid") // 外键设置为rid
    private Restaurant restaurant;//FK

    private LocalDateTime time;//下单时间+30min

    private double totalPrice;

    private double restaurant_profit;//default=totalPrice*0.7

    private double profit;//default=totalPrice*0.3

    private boolean isCancelled = false;//default=false

    private boolean isProcessed = false;//default=false商家默认未处理，确认接单后改为true；如果退订被修改，会修改成未处理

    private boolean isMade = false;//defalault=false商家默认未制作

    private boolean isValid = true;

    private String address;

    private boolean received = false;

    @OneToMany(cascade={CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinColumn(name="oid")
    private Set<OrderDetail> orderDetailSet;

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = Math.round(totalPrice*100)/100.0;
    }

    public double getRestaurant_profit() {
        return restaurant_profit;
    }

    public void setRestaurant_profit(double restaurant_profit) {
        this.restaurant_profit = Math.round(restaurant_profit*100)/100.0;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = Math.round(profit*100)/100.0;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    public boolean isMade() {
        return isMade;
    }

    public void setMade(boolean made) {
        isMade = made;
    }

    public Set<OrderDetail> getOrderDetailSet() {
        return orderDetailSet;
    }

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
        this.orderDetailSet = orderDetailSet;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Long getOid() {
        return oid;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
