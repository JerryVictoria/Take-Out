package takeout.yummy.service.restaurant;


import takeout.yummy.Util.*;

import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/6
 */
public interface RestaurantService {

    public RestaurantInfo getRestaurantInfo(Integer rid);

    public void saveApprovalInfo(Integer rid, String address);

    public void saveApprovalInfo(Integer rid, String name, String phone, String type);

    public List<ApprovalInfo> getApprovals();

    public void releaseProductInfo(ProductReleaseInfo productReleaseInfo);

    public void releaseDiscountInfo(DiscountReleaseInfo discountReleaseInfo);

    public List<MarketInfo> getRestaurants();

    public RestaurantProductDetail getRestaurantDetail(int rid);

    public SubmitOrderResult submitOrder(OrderInfo orderInfo);

    public List<OrderTrackInfo> findAllValidOrder(String username);

    public List<OrderRecord> findAllUntreatedOrder(int rid);

    public List<OrderRecord> findAllRecord(int rid);

    public void takeOrder(Long oid);

    public void takeCancelledOrder(Long oid,boolean isMade);

    public List<RestaurantDetail> checkOrder(int rid);

    public List<MarketProductInfo> getRestaurantAllProduct(int rid);

    public List<MarketDiscountInfo> getRestaurantAllDiscount(int rid);

    public void releaseSetMealInfo(SetMealInfo setMealInfo);

    public boolean waitToApprove(int rid);
}
