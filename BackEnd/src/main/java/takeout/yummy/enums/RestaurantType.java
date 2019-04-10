package takeout.yummy.enums;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/1
 */
public enum RestaurantType {

    CHINESE_MEAL("中餐"),
    WESTERN_MEAL("西餐"),
    LEISURE_MEAL("休闲餐");

    private String str;

    RestaurantType(String str){
        this.str = str;
    }

    public String getStr(){
        return str;
    }

    public static RestaurantType getRestaurantType(String str){
        RestaurantType[] restaurantTypes = RestaurantType.values();
        for(RestaurantType restaurantType : restaurantTypes){
            if(str.equals(restaurantType.getStr())){
                return restaurantType;
            }
        }
        return RestaurantType.CHINESE_MEAL;
    }

    public static String getTypeStr(RestaurantType restaurantType){
        if(restaurantType==null){
            return "";
        }
        switch (restaurantType){
            case CHINESE_MEAL: return "中餐";
            case WESTERN_MEAL: return "西餐";
            case LEISURE_MEAL: return "休闲餐";
        }
        return "";
    }
}
