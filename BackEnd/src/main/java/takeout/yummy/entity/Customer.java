package takeout.yummy.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/1
 */
@Entity
public class Customer {

    @Id
    @NotNull
    private String username;//len<20,PK

    private String password;//len<20

    private String email;

    private String phone;

    private String name;//real name

    private int level = 1;

    private boolean isValid = true;//default=true

    private double money = 5000;

    private boolean emailValid = false;

    private String emailCode;

    private double consumption = 0;

    @OneToMany(cascade={CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinColumn(name="username")
    private Set<CustomerAddress> customerAddressSet;

    @OneToMany(cascade={CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinColumn(name="username")
    private Set<OrderList> orderListSet;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Set<CustomerAddress> getCustomerAddressSet() {
        return customerAddressSet;
    }

    public void setCustomerAddressSet(Set<CustomerAddress> customerAddressSet) {
        this.customerAddressSet = customerAddressSet;
    }

    public Set<OrderList> getOrderListSet() {
        return orderListSet;
    }

    public void setOrderListSet(Set<OrderList> orderListSet) {
        this.orderListSet = orderListSet;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }
}
