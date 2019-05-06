package com.jk.service.Impl;

import com.alibaba.dubbo.config.annotation.Reference;

import com.jk.bean.UserBean;
import com.jk.service.GoodService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
@Service
public class GoodServiceImpl implements GoodService {
       @Reference
       GoodService service;
    @Override
    public HashMap<String, Object> queryGood(Integer start, Integer pageSize) {

        return service.queryGood(start,pageSize);
    }

    @Override
    public UserBean getUserByUsernamePwd(UserBean user) {

        return service.getUserByUsernamePwd(user);
    }

    @Override
    public void registered(UserBean user) {

        service.registered(user);
        
        
    }

    @Override
    public UserBean queryUserByUserId(UserBean user) {

        return  service.queryUserByUserId(user);
    }

    @Override
    public void sendSimpleMail(String email, String 您的验证码为, String message) {
          service.sendSimpleMail(email,"您的验证码为",message);
    }




}
