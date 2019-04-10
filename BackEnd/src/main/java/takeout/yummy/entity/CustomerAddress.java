package takeout.yummy.entity;

import javax.persistence.*;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/1
 */
@Entity
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name="username",referencedColumnName="username") // 外键设置为username
    private Customer customer;

    private String address;//系统提供选项

    public CustomerAddress() {
    }

    public CustomerAddress(Customer customer, String address) {
        this.customer = customer;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
