package takeout.yummy.Util;

import takeout.yummy.entity.Customer;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/18
 */
public class LevelChange {

    private static double[] money = new double[]{5000,1000,0};

    public static void change(Customer customer){
        for(int i=0;i<3;i++){
            if(customer.getConsumption()>=money[i]){
                customer.setLevel(3-i);
                break;
            }
        }
    }
}
