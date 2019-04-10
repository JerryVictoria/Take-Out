package takeout.yummy.Util;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/14
 */
public class LoginResult {
    private boolean isSuccess;
    private int level;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
