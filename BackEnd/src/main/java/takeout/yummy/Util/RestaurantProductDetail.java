package takeout.yummy.Util;


import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/8
 */
public class RestaurantProductDetail {

    private List<MarketProductInfo> productInfoList;
    private List<MarketDiscountInfo> discountInfoList;

    public RestaurantProductDetail() {
    }

    public RestaurantProductDetail(List<MarketProductInfo> productInfoList, List<MarketDiscountInfo> discountInfoList) {
        this.productInfoList = productInfoList;
        this.discountInfoList = discountInfoList;
    }

    public List<MarketProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<MarketProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

    public List<MarketDiscountInfo> getDiscountInfoList() {
        return discountInfoList;
    }

    public void setDiscountInfoList(List<MarketDiscountInfo> discountInfoList) {
        this.discountInfoList = discountInfoList;
    }
}
