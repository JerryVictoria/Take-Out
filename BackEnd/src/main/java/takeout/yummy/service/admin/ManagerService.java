package takeout.yummy.service.admin;

import java.util.ArrayList;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/6
 */
public interface ManagerService {

    public void agreeApproval(int id);

    public void disagreeApproval(int id);

    public ArrayList<Integer> getCustomerData();

    public ArrayList<Integer> getRestaurantData();

    public ArrayList<Double> getProfitData(String year);
}
