package takeout.yummy.entity;

import com.sun.istack.internal.NotNull;
import takeout.yummy.enums.RestaurantType;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/1
 */
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;//len=7,PK

    @NotNull
    private String password;

    private String name;

    private String address;//系统提供选项

    private RestaurantType type;//枚举类

    private double money = 0;//default = 0

    private String phone;

    private String email;

    private boolean emailValid = false;

    private String emailCode;

    @OneToMany(cascade={CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinColumn(name="rid")
    private Set<ProductInfo> productInfoSet;


    @OneToMany(cascade={CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinColumn(name="rid")
    private Set<DiscountInfo> discountInfoSet;

    @OneToMany(cascade={CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinColumn(name="rid")
    private Set<OrderList> orderListSet;

    @OneToMany(cascade={CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinColumn(name="rid")
    private Set<Approval> approvalSet;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Set<ProductInfo> getProductInfoSet() {
        return productInfoSet;
    }

    public void setProductInfoSet(Set<ProductInfo> productInfoSet) {
        this.productInfoSet = productInfoSet;
    }

    public Set<DiscountInfo> getDiscountInfoSet() {
        return discountInfoSet;
    }

    public void setDiscountInfoSet(Set<DiscountInfo> discountInfoSet) {
        this.discountInfoSet = discountInfoSet;
    }

    public Set<OrderList> getOrderListSet() {
        return orderListSet;
    }

    public void setOrderListSet(Set<OrderList> orderListSet) {
        this.orderListSet = orderListSet;
    }

    public Set<Approval> getApprovalSet() {
        return approvalSet;
    }

    public void setApprovalSet(Set<Approval> approvalSet) {
        this.approvalSet = approvalSet;
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

}
