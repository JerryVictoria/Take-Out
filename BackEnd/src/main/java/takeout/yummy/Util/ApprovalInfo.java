package takeout.yummy.Util;

import takeout.yummy.entity.Approval;
import takeout.yummy.enums.RestaurantType;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/6
 */
public class ApprovalInfo {

    private String rid;
    private Integer id;//PK
    private String name;
    private String phone;
    private String address;//系统提供选项
    private String type;//枚举类

    public ApprovalInfo() {
    }

    public ApprovalInfo(Approval approval) {
        this.rid = processRid(approval.getRestaurant().getRid());
        this.id = approval.getId();
        this.name = approval.getName();
        this.phone = approval.getPhone();
        this.address = approval.getAddress();
        this.type = RestaurantType.getTypeStr(approval.getType());
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String processRid(int rid){
        String result = rid+"";
        while(result.length()<7){
            result = "0"+result;
        }
        return result;
    }
}
