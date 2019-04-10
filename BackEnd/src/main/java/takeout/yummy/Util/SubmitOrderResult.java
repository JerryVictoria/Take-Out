package takeout.yummy.Util;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/14
 */
public class SubmitOrderResult {
    private boolean success;
    private String message;//请在15min内付款；XXX库存不足

    public SubmitOrderResult() {
    }

    public SubmitOrderResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
