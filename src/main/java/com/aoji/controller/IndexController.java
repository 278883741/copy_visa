package com.aoji.controller;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午10:54 2017/11/10
 */

import com.aoji.contants.ContentException;
import com.aoji.model.SysUser;
import com.aoji.service.StudentService;
import com.aoji.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Value("${interceptedLogin.str}")
    private String loginStr;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value="/")
    public String index(HttpSession session, HttpServletRequest request){
        return login(session,request);
    }

    /**
     * 登录默认执行
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value="/login")
    public String login( HttpSession session, HttpServletRequest request){
        //判断是否模拟登录
        Boolean simulationStatus=simulationLogin(request);

        //模拟登录，跳转系统登录页
        if(simulationStatus){
            return "login";
        }

        //非模拟登录，抛出异常
        SysUser user=userService.getLoginUser();
        if(user==null || !StringUtils.hasText(user.getOaid())){
            throw new ContentException(9999,"登录异常，请联系管理员！");
        }

        //验证通过，根据用户查询所属角色，根据角色查询权限
        try{
            session.setAttribute("functions",userService.getFunctionByName(user.getOaid()));
            session.setAttribute("user",user);
        }catch (Exception e){
            logger.error("文签系统没有此用户:{}",user.toString());
        }
        return "redirect:/workTable";
    }

    /**
     * 模拟登录
     * @param request
     * @return
     */
    private Boolean simulationLogin(HttpServletRequest request) {
        Boolean simulationStatus=false;
        try {
            String  localhostIp = InetAddress.getLocalHost().getHostAddress();
            logger.info("登录的ip:{},本机的ip：{}，登录url：{}",request.getServerName(),localhostIp,request.getRequestURL());

            List<String> list = Arrays.asList(loginStr.split(","));
            if(list.contains(request.getServerName())|| request.getServerName().equals(localhostIp)){
                simulationStatus=true;
                logger.info("模拟登录成功，跳转页面");
            }
        } catch (UnknownHostException e) {
            logger.error("【获取ip异常】，错误如下：{}",e.getMessage());
        }finally{
            return simulationStatus;
        }

    }


    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping(value="/logout")
    public String logout(HttpSession session){
        session.removeAttribute("functions");
        session.removeAttribute("user");
        SecurityUtils.getSubject().logout();
        return "login";
    }

    /**
     * 模拟登录下的登录校验
     * @param oaid
     * @param password
     * @param session
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/checkLogin",method=RequestMethod.POST)
    public String checkLogin(String oaid, String password, HttpSession session,RedirectAttributes redirectAttributes){
        Subject currentUser= SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(oaid, password);
        Boolean loginStatus = false;
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + oaid + "]进行登录验证..验证开始");
            currentUser.login(token);
            loginStatus = true;
            token.clear();
            SysUser user=(SysUser) currentUser.getPrincipal();
            //根据用户查询所属角色
            //根据角色查询权限
            session.setAttribute("functions",userService.getFunctionByName(user.getOaid()));
            session.setAttribute("user",user);
            logger.info("对用户[" + oaid + "]进行登录验证..验证通过");
        }catch(UnknownAccountException uae){
            logger.info("对用户[" + oaid + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        }catch(IncorrectCredentialsException ice){
            logger.info("对用户[" + oaid + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        }catch(LockedAccountException lae){
            logger.info("对用户[" + oaid + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            logger.info("对用户[" + oaid + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + oaid + "]进行登录验证..验证未通过,堆栈轨迹如下");
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        //验证是否登录成功
        if(loginStatus){
            logger.info("用户[" + oaid + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            //根据用户id查询用户所拥有的角色
            List<String> roles = userService.getRolesByOaid(oaid);
            logger.info("登录用户:"+oaid);
            logger.info("登录角色:"+roles.toString());
            logger.info("是否佣金系统权限:"+roles.contains("佣金系统"));
            return "redirect:/workTable";
        }else{
            token.clear();
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "main")
    public String indexPage(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            return "redirect:/login";
        }else{
            return "index";
        }
    }

    @RequestMapping(value = "/exception")
    public String exception(){
            return "/401";
    }
}
