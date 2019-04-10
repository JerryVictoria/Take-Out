package takeout.yummy.Util;

import takeout.yummy.entity.Restaurant;
import takeout.yummy.enums.RestaurantType;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/8
 */
public class MarketInfo {
    private int rid;
    private String name;
    private String type;
    private String address;
    private String phone;

    public MarketInfo(){

    }

    public MarketInfo(Restaurant restaurant){
        this.rid = restaurant.getRid();
        this.name = restaurant.getName();
        this.type = RestaurantType.getTypeStr(restaurant.getType());
        this.address = restaurant.getAddress();
        this.phone = restaurant.getPhone();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
