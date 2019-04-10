package takeout.yummy.service.register;

import takeout.yummy.Util.RegisterInfo;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/4
 */
public interface RegisterService {

    public String registerAndSendEmail(RegisterInfo registerInfo);

    public void checkEmail(String emailCode);
}
