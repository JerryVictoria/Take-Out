package takeout.yummy.entity;

import takeout.yummy.enums.RestaurantType;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/2
 */
@Entity
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//PK

    @ManyToOne(cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name="rid",referencedColumnName="rid") // 外键设置为rid
    private Restaurant restaurant;//FK

    private String name;

    private String phone;

    private String address;//系统提供选项

    private RestaurantType type;//枚举类

    boolean isProcessed = false;//defalut=false

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RestaurantType getType() {
        return type;
    }

    public void setType(RestaurantType type) {
        this.type = type;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
