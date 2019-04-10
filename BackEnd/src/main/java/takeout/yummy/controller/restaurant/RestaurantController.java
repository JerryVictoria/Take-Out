package takeout.yummy.controller.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import takeout.yummy.Util.*;
import takeout.yummy.service.restaurant.RestaurantService;

import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/6
 */
@RestController
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/GetRestaurantInfo")
    public RestaurantInfo getRestaurantInfo(@RequestParam int rid){
        return restaurantService.getRestaurantInfo(rid);
    }

    @GetMapping(value = "/ChangeRestaurantAddress")
    public void changeRestaurantAddress(@RequestParam int rid, @RequestParam String address){
        restaurantService.saveApprovalInfo(rid,address);
    }

    @GetMapping(value = "/ChangeRestaurantBaseInfo")
    public void changeRestaurantBaseInfo(@RequestParam int rid, @RequestParam String name, @RequestParam String phone, @RequestParam String type){
        restaurantService.saveApprovalInfo(rid, name, phone, type);
    }

    @PostMapping(value = "/ReleaseProductInfo")
    public void releaseProductInfo(@RequestBody ProductReleaseInfo productReleaseInfo){
        restaurantService.releaseProductInfo(productReleaseInfo);
    }

    @PostMapping(value = "/ReleaseSetMealInfo")
    public void releaseSetMealInfo(@RequestBody SetMealInfo setMealInfo){
        restaurantService.releaseSetMealInfo(setMealInfo);
    }

    @PostMapping(value = "/ReleaseDiscountInfo")
    public void releaseDiscountInfo(@RequestBody DiscountReleaseInfo discountReleaseInfo){
        restaurantService.releaseDiscountInfo(discountReleaseInfo);
    }

    @GetMapping(value = "/GetUntreatedOrder")
    public List<OrderRecord> getUntreatedOrder(@RequestParam int rid){
        return restaurantService.findAllUntreatedOrder(rid);
    }

    @GetMapping(value = "/TakeOrder")
    public void takeOrder(@RequestParam Long oid){
        restaurantService.takeOrder(oid);
    }

    @GetMapping(value = "/GetRecord")
    public List<OrderRecord> getRecord(@RequestParam int rid){
        return restaurantService.findAllRecord(rid); // isProcessed = true
    }

    @GetMapping(value = "/TakeCancelledOrder")
    public void takeCancelledOrder(@RequestParam Long oid, @RequestParam boolean isMade){
        restaurantService.takeCancelledOrder(oid, isMade);
    }

    @GetMapping(value = "/CheckOrder")
    public List<RestaurantDetail> checkOrder(@RequestParam int rid){
        return restaurantService.checkOrder(rid);
    }

    @GetMapping(value = "/GetAllProduct")
    public List<MarketProductInfo> getRestaurantDetail(@RequestParam int rid){
        return restaurantService.getRestaurantAllProduct(rid);
    }

    @GetMapping(value = "/GetAllDiscount")
    public List<MarketDiscountInfo> getRestaurantDiscount(@RequestParam int rid){
        return restaurantService.getRestaurantAllDiscount(rid);
    }

    @GetMapping(value = "/WaitToApprove")
    public boolean waitToApprove(@RequestParam int rid){
        return restaurantService.waitToApprove(rid);
    }

}
