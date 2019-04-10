package takeout.yummy.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import takeout.yummy.Util.ApprovalInfo;
import takeout.yummy.entity.Approval;
import takeout.yummy.entity.Manager;
import takeout.yummy.service.admin.ManagerService;
import takeout.yummy.service.restaurant.RestaurantService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/6
 */
@RestController
public class ManagerController {

    private RestaurantService restaurantService;
    private ManagerService managerService;

    @Autowired
    public ManagerController(RestaurantService restaurantService, ManagerService managerService) {
        this.restaurantService = restaurantService;
        this.managerService = managerService;
    }

    @GetMapping(value = "/GetApprovals")
    public List<ApprovalInfo> getApprovals(){
        return restaurantService.getApprovals();
    }

    @GetMapping(value = "/AgreeApproval")
    public void agreeApproval(@RequestParam int id ){
        managerService.agreeApproval(id);
    }

    @GetMapping(value = "/DisagreeApproval")
    public void disagreeApproval(@RequestParam int id ){
        managerService.disagreeApproval(id);
    }

    @GetMapping(value = "/GetCustomerData")
    public ArrayList<Integer> getCustomerData(){
        return managerService.getCustomerData();
    }

    @GetMapping(value = "/GetRestaurantData")
    public ArrayList<Integer> getRestaurantData(){
        return managerService.getRestaurantData();
    }

    @GetMapping(value = "/GetProfitData")
    public ArrayList<Double> getProfitData(@RequestParam String year){
        return managerService.getProfitData(year);
    }
}
