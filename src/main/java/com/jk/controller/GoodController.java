package com.jk.controller;

import com.jk.bean.UserBean;
import com.jk.service.GoodService;
import com.jk.util.httpclient.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Random;

@Controller
public class GoodController {
       @Autowired
       GoodService service;
   public HashMap<String,Object> queryGood(Integer start, Integer pageSize){

         return service.queryGood(start,pageSize);
   }
    //登录页面
     @RequestMapping("/loginPage")
    public String loginPage(){

       return "denglu";
     }


      //登录
     @RequestMapping("login")
     @ResponseBody
    public HashMap<String,Object> login(UserBean user,HttpSession session) {
           HashMap<String, Object> map = new HashMap<>();
        //判断用户名和密码是否正确
        UserBean userFromDb = service.getUserByUsernamePwd(user);

        if (userFromDb != null) {
             map.put("msg","你已经成功登录");
             session.setAttribute("user",userFromDb);
        }else{
            map.put("msg","账号密码不匹配");
        }

               return map;
    }
       //注册页面
    @RequestMapping("zhuce")
    public String zhucePage(){

         return "zhuce";
    }




    //获取邮箱验证的验证码

    @RequestMapping("getCheckCode")
    @ResponseBody
    public String getCheckCode(String email, HttpServletRequest request){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        request.getSession().setAttribute("yanzheng",checkCode);
        String message = "您的注册验证码为："+checkCode+"请赶快使用，错了别怪我";
        try {
            service.sendSimpleMail(email, "您的验证码为", message);
        }catch (Exception e){
            return "";
        }
        return checkCode;
    }

   //注册
    @RequestMapping("registered")
    @ResponseBody
   public HashMap<String,Object> registered(UserBean user,HttpServletRequest request){
        UserBean userBean = service.queryUserByUserId(user);
        String yanzheng = (String)request.getSession().getAttribute("yanzheng");
        HashMap<String, Object> hashmap = new HashMap<>();
        if(userBean==null){
               if(yanzheng.equals(user.getYanzhengma())){
                   service.registered(user);
                   hashmap.put("msg","注册成功");
               }else{

                   hashmap.put("msg","注册失败");
               }


         }
        return hashmap;
    }
    //获取短信验证码
    //
    @RequestMapping("phoneTest")
    @ResponseBody
    public String  phoneTest(String phoneNumber){
        Integer randomNumber= (int)(Math.random()*899999+100000);
        String number="#code#="+randomNumber+"&#company#=沐繁责任有限公司";
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("mobile",phoneNumber);
        hashmap.put("tpl_id","156143");
        hashmap.put("tpl_value",number);
        hashmap.put("key","c14f7d6e7c6ee43940fef391c41ad7fa");
       String str = HttpClient.sendGet("http://v.juhe.cn/sms/send",hashmap);

          return str;
    }







}
