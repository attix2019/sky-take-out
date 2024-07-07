package com.sky.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.MessageConstant;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    WeChatProperties weChatProperties;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(String code){
        //wx login
        String openid = wxlogin(code);

        // 根据openId判断是否要插入新用户
        if(openid == null || openid.equals("")){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user = userMapper.findUserByOpenId(openid);
        if(user == null){
            user = User.builder().openid(openid).createTime(LocalDateTime.now()).build();
            userMapper.insertUser(user);
        }

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder().id(user.getId()).openid(openid).token(token).build();
        return userLoginVO;
    }

    public String wxlogin(String code){
        Map<String, String> params = new HashMap<>();
        params.put("appid", weChatProperties.getAppid());
        params.put("secret", weChatProperties.getSecret());
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        String response = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", params);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = null;
        String openid = "";
        try {
            actualObj = mapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        if(actualObj.has("openid")) {
            openid = actualObj.get("openid").textValue();
        }
        return openid;
    }

}
