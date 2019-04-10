package takeout.yummy.enums;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/3
 */
public enum UserRole {

    CUSTOMER("普通用户"),
    RESTAURANT("餐厅"),
    MANAGER("管理员");

    private String str;

    UserRole(String str){
        this.str = str;
    }

    public String getStr(){
        return str;
    }

    public static UserRole getUserRole(String role){
        UserRole[] userRoles = UserRole.values();
        for(UserRole userRole : userRoles){
            if(role.equals(userRole.getStr())){
                return userRole;
            }
        }
        return UserRole.CUSTOMER;
    }

}
