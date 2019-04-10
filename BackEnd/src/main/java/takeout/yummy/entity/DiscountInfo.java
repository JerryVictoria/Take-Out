package takeout.yummy.entity;

import takeout.yummy.Util.DiscountReleaseInfo;
import takeout.yummy.Util.TimeFormatChange;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/1
 */
@Entity
public class DiscountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade={CascadeType.MERGE}, fetch= FetchType.EAGER)
    @JoinColumn(name="rid",referencedColumnName="rid") // 外键设置为rid
    private Restaurant restaurant;//FK

    private LocalDate startTime;

    private LocalDate endTime;

    private Integer upper;//=满？

    private Integer discount;//=减？

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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

    public Integer getUpper() {
        return upper;
    }

    public void setUpper(Integer upper) {
        this.upper = upper;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public DiscountInfo() {
    }

    public DiscountInfo(DiscountReleaseInfo discountReleaseInfo, Restaurant restaurant) {
        this.restaurant = restaurant;
        this.startTime = TimeFormatChange.getLocalDateTime(discountReleaseInfo.getStartTime());
        this.endTime = TimeFormatChange.getLocalDateTime(discountReleaseInfo.getEndTime());
        this.upper = discountReleaseInfo.getUpper();
        this.discount = discountReleaseInfo.getDiscount();
    }

}
