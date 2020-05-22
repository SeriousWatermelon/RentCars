package cn.ac.ict.controller;

import cn.ac.ict.utils.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Controller
public class OMLoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登录
     */
    @ResponseBody
    @PostMapping(value = "/loginUser")
    public ModelAndView loginUser(String username,
                                  String password,
                                  boolean isRememberMe,
                                  Map<String, Object> map) {
        try {
            Subject subject = ShiroUtil.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(isRememberMe);
            subject.login(token);
            return new ModelAndView("/index");
        } catch (Exception e) {
            log.info("[LOGIN] FAILED,username={},login failed! May be incorrect username or password.", username);
            map.put("url", "/login");
            map.put("msg", "用户名或密码错误");
            return new ModelAndView("/common/error");
        }
    }

    /**
     * 退出登陆
     */
    @GetMapping(value = "/logout")
    public String logOut() {
        ShiroUtil.logout();
        return "redirect:/login.html";
    }

}
