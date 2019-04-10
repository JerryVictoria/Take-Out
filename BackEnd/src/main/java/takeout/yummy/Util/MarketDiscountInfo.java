package takeout.yummy.Util;

import takeout.yummy.entity.DiscountInfo;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/8
 */
public class MarketDiscountInfo implements Comparable<MarketDiscountInfo>{

    private int upper;
    private int discount;

    public MarketDiscountInfo() {
    }

    public MarketDiscountInfo(DiscountInfo discountInfo) {
        this.upper = discountInfo.getUpper();
        this.discount = discountInfo.getDiscount();
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public int compareTo(MarketDiscountInfo o) {
        return this.upper-o.upper;
    }
}
