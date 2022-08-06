package com.atlong.crm.settings.web.controller;

import com.atlong.crm.commons.constant.Constant;
import com.atlong.crm.commons.domain.ReturnObject;
import com.atlong.crm.commons.utils.DateUtils;
import com.atlong.crm.settings.domain.User;
import com.atlong.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: YunLong
 * @Date: 2022/7/20 17:18
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        //请求转发到登录页面
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用service层的方法，查询用户
        User user = userService.queryByActAndPwd(map);
        System.out.println("ip:"+request.getRemoteAddr());
        //根据查询结果返回相应信息
        ReturnObject returnObject=new ReturnObject();
        if (user == null){
            //登录失败用户名或密码错误
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        }else {
            if(DateUtils.formatDateTime(new Date()).compareTo(user.getExpireTime())>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已经过期");
            }else if ("0".equals(user.getLockState())){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("状态被锁定");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("ip受限");
            }else {
                //登录成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                //把user保存到session中
                session.setAttribute(Constant.SESSION_USER,user);
                //如果需要记住密码，则往外写cookie
                if("true".equals(isRemPwd)){
                    Cookie c1=new Cookie("loginAct",user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd",user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else{
                    //把没有过期cookie删除
                    Cookie c1=new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //清空cookie
        Cookie c1=new Cookie("loginAct","1");
        c1.setMaxAge(0);
        Cookie c2=new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c1);
        response.addCookie(c2);
        //销毁session
        session.invalidate();
        //重定向跳转到首页
        return "redirect:/";
    }
}
