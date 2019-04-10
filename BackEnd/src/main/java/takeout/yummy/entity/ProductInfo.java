package takeout.yummy.entity;

import takeout.yummy.Util.ProductReleaseInfo;
import takeout.yummy.Util.TimeFormatChange;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/1
 */
@Entity
public class ProductInfo {

    @ManyToOne(cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name="rid",referencedColumnName="rid") // 外键设置为rid
    private Restaurant restaurant;//FK

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;//PK

    private LocalDate startTime;

    private LocalDate endTime;

    private String productName;

    private int quantity;

    private double price;

    private String type;

    private  String description;//单品说明以及套餐说明

    private Boolean isSetMeal = false;


    @OneToMany(cascade={CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinColumn(name="pid")
    private Set<OrderDetail> orderDetailSet;

    @OneToMany(cascade={CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinColumn(name="pid")
    private Set<SetMealDetail> setMealDetailSet;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<OrderDetail> getOrderDetailSet() {
        return orderDetailSet;
    }

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
        this.orderDetailSet = orderDetailSet;
    }


    public ProductInfo() {
    }

    public ProductInfo(ProductReleaseInfo productReleaseInfo, Restaurant restaurant) {
        this.restaurant = restaurant;
        this.startTime = TimeFormatChange.getLocalDateTime(productReleaseInfo.getStartTime());
        this.endTime = TimeFormatChange.getLocalDateTime(productReleaseInfo.getEndTime());
        this.productName = productReleaseInfo.getProductName();
        this.quantity = productReleaseInfo.getQuantity();
        this.price= productReleaseInfo.getPrice();
        this.type = productReleaseInfo.getType();
        this.description = productReleaseInfo.getDescription();
    }

    public Set<SetMealDetail> getSetMealDetailSet() {
        return setMealDetailSet;
    }

    public void setSetMealDetailSet(Set<SetMealDetail> setMealDetailSet) {
        this.setMealDetailSet = setMealDetailSet;
    }

    public Boolean getSetMeal() {
        return isSetMeal;
    }

    public void setSetMeal(Boolean setMeal) {
        isSetMeal = setMeal;
    }
}
