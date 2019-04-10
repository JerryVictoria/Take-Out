package takeout.yummy.Util;

import takeout.yummy.entity.Restaurant;
import takeout.yummy.enums.RestaurantType;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/6
 */
public class RestaurantInfo {

    private int rid;//len=7,PK

    private String name;

    private String address;//系统提供选项

    private String type;//枚举类

    private double money = 0;//default = 0

    private String phone;

    private String email;

    public RestaurantInfo() {
    }

    public RestaurantInfo(Restaurant restaurant) {
        this.rid = restaurant.getRid();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.type = RestaurantType.getTypeStr(restaurant.getType());
        this.money = restaurant.getMoney();
        this.phone = restaurant.getPhone();
        this.email = restaurant.getEmail();
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
