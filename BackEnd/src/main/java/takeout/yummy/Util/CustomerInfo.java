package takeout.yummy.Util;

import takeout.yummy.entity.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/5
 */
public class CustomerInfo {

    private String email;
    private String phone;
    private String name;
    private int level;
    private double money;

    private List<String> address;

    public CustomerInfo() {
    }

    public CustomerInfo(Customer customer, List<String> address) {
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.name = customer.getName();
        this.level = customer.getLevel();
        this.money = customer.getMoney();
        this.address = address;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
