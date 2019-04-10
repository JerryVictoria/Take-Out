package takeout.yummy.service.login;

import takeout.yummy.Util.LoginResult;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/3
 */
public interface LoginService {

    public LoginResult checkLoginInfo(String username, String password, String type);
}
