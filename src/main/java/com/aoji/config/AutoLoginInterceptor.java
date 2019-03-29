package com.aoji.config;
import com.aoji.contants.ContentException;
import com.aoji.contants.UserTypeEnum;
import com.aoji.model.SysUser;
import com.aoji.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;


/**
 * 根据cookie 判断是否登录
 */

@Component
public class AutoLoginInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    UserService userService;



    private static final Logger logger = LoggerFactory.getLogger(AutoLoginInterceptor.class);


    /**
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {


        try{
            SysUser loginUser = getLoginUser(request);
            if(loginUser != null)
            {
                //自动登录,不需要密码
                Subject subject = SecurityUtils.getSubject();
                if(!subject.isAuthenticated() )
                {
                    //登录

                    SysUser sysUser=new SysUser();
                    sysUser.setOaid(loginUser.getUsername());
                    sysUser.setDeleteStatus(false);
                    sysUser = userService.getUserInfo(sysUser);

                    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginUser.getUsername(), sysUser.getPassword());
                    subject.login(usernamePasswordToken);
                }
            }
        }catch(ContentException e){
            new ContentException(401,"您没有文签系统的权限,请找管理员添加权限");
        }
        return true;
    }


    /**
     * 删除用户状态
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {

    }


    /**
     * 获取登录用户
     * @param req
     * @return
     */
    private synchronized SysUser getLoginUser(HttpServletRequest req)
    {
        String model = System.getProperty("model","pro");
        SysUser userModel = null;

        //查询到头部信息,自动登录
        //开发模式,免登录
        if("dev".equals(model))
        {
            userModel = new SysUser();
            userModel.setUsername("admin");
        } else {
            try {
                //会员ID
                String memberId = req.getHeader("memberId");
                if(StringUtils.isEmpty(memberId)){
                    return userModel;
                }
                userModel = new SysUser();
                userModel.setUsername(memberId);
                //小希账号
                String account = req.getHeader("account");
                //真实姓名
                String realName = URLDecoder.decode(req.getHeader("realName"),"UTF-8");
                //工号
                String staffNo = req.getHeader("staffNo");
                //代理id
                String agentId = req.getHeader("agentId");
                //是否内部顾问
                String isInner = req.getHeader("isInner");

                logger.info("【登录成功】账号：{}，姓名：{}，工号：{}，代理id：{}，是否内部顾问：{}",account,realName,staffNo,agentId,isInner);

                userModel.setAgentId(agentId);

                try {
                    // 初始化用户
                    SysUser sysUser = new SysUser();
                    sysUser.setUsername(realName);
                    // 代理无oaid,默认为memberId
                    sysUser.setOaid(memberId);
                    sysUser.setAgentId(agentId);
                    if(!StringUtils.isEmpty(isInner)){
                        sysUser.setIsInner(Integer.valueOf(isInner));
                    }
                    userService.initUserForSSO(sysUser);
                } catch (Exception e){
                    logger.error("SSO初始化用户失败！", e);
                }
                } catch (Exception e) {
                logger.error("",e);
            }
        }
        return  userModel;
    }

}
