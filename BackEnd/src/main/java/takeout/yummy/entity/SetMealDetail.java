package takeout.yummy.entity;

import javax.persistence.*;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/25
 */
@Entity
public class SetMealDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name="smpid",referencedColumnName="pid") // 外键设置为pid
    private ProductInfo setMeal;//FK

    @ManyToOne(cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name="pid",referencedColumnName="pid") // 外键设置为pid
    private ProductInfo productInfo;//FK

    private int quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductInfo getSetMeal() {
        return setMeal;
    }

    public void setSetMeal(ProductInfo setMeal) {
        this.setMeal = setMeal;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
