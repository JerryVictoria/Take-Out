package takeout.yummy.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeout.yummy.Util.LoginInfo;
import takeout.yummy.Util.LoginResult;
import takeout.yummy.Util.RegisterInfo;
import takeout.yummy.service.login.LoginService;
import takeout.yummy.service.register.RegisterService;

import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/3
 */
@RestController
public class LoginController {

    private LoginService loginService;
    private RegisterService registerService;

    @Autowired
    public LoginController(LoginService loginService, RegisterService registerService) {
        this.loginService = loginService;
        this.registerService = registerService;
    }


    @PostMapping(value = "/Login")
    public LoginResult isSuccessfulLogged(@RequestBody LoginInfo loginInfo){
        return loginService.checkLoginInfo(loginInfo.getUsername(), loginInfo.getPassword(), loginInfo.getRole());
    }

    @PostMapping(value = "/Register")
    public String register(@RequestBody RegisterInfo registerInfo){
        System.out.println("/Register");
        return registerService.registerAndSendEmail(registerInfo);
    }

    /**
     *校验邮箱中的code激活账户
     * 首先根据激活码code查询用户，之后再把状态修改为"1"
     */
    @RequestMapping(value = "/checkCode")
    public void checkCode(String code){
        System.out.println("/checkCode");
        registerService.checkEmail(code);
    }



}
