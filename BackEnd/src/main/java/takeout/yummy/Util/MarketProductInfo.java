package takeout.yummy.Util;

import takeout.yummy.entity.ProductInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/8
 */
public class MarketProductInfo {

    private Long pid;
    private String productName;
    private int quantity;
    private double price;
    private String type;
    private String description;
    private boolean isSetMeal;
    private List<OrderBaseInfo> orderBaseInfoList;

    public MarketProductInfo() {
    }

    public MarketProductInfo(ProductInfo productInfo) {
        this.pid = productInfo.getPid();
        this.productName = productInfo.getProductName();
        this.quantity = productInfo.getQuantity();
        this.price = productInfo.getPrice();
        this.type = productInfo.getType();
        this.description = productInfo.getDescription();
        this.isSetMeal = productInfo.getSetMeal();
        orderBaseInfoList = new ArrayList<>();
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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public boolean isSetMeal() {
        return isSetMeal;
    }

    public void setSetMeal(boolean setMeal) {
        isSetMeal = setMeal;
    }

    public List<OrderBaseInfo> getOrderBaseInfoList() {
        return orderBaseInfoList;
    }

    public void setOrderBaseInfoList(List<OrderBaseInfo> orderBaseInfoList) {
        this.orderBaseInfoList = orderBaseInfoList;
    }
}
