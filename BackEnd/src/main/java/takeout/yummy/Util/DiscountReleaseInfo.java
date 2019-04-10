package takeout.yummy.Util;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/7
 */
public class DiscountReleaseInfo {
    private int rid;
    private String startTime;
    private String endTime;
    private int upper;
    private int discount;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
}
