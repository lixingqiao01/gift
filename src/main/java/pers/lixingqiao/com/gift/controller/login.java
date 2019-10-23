package pers.lixingqiao.com.gift.controller;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.lixingqiao.com.gift.dao.BanquetNotesRepository;
import pers.lixingqiao.com.gift.dao.UserRepository;
import pers.lixingqiao.com.gift.model.BanquetNotes;
import pers.lixingqiao.com.gift.model.User;
import pers.lixingqiao.com.gift.until.JSONResult;
import pers.lixingqiao.com.gift.until.JwtUntil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/api")
public class login {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BanquetNotesRepository banquetNotesRepository;




    @PostMapping(path = "/regist")
    @ResponseBody
    public  JSONResult registUser(@RequestParam(name = "username") String username,
                                           @RequestParam(name = "password") String password,
                                           @RequestParam(name = "gender") Integer gender,
                                           @RequestParam(name = "name") String name) {
        if (userRepository.getByUserWithUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setGender(gender);
            user.setName(name);
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());

            userRepository.save(user);
            return JSONResult.build(200,"注册成功", JwtUntil.build(user.getUsername(),user.getUser_id()));
        } else {
            return JSONResult.build(200,"注册失败",null);
        }
    }





    @GetMapping(path = "/all")
    @ResponseBody
    public  JSONResult getAllUsers() {
        List<User> list = IterableUtils.toList(userRepository.findAll());

        return JSONResult.ok(list);
    }





    @PostMapping(path = "/login")
    @ResponseBody
    public JSONResult loginUsername(@RequestParam(name = "username") String username,
                                    @RequestParam(name = "password") String password) {
        User user = userRepository.getByUserWithUsername(username);
        if (user != null) {
            if (userRepository.getByUserWithUsernameAndPassword(username,password) != null) {
                User signUser = userRepository.getByUserWithUsernameAndPassword(username,password);
                return JSONResult.ok(JwtUntil.build(signUser.getUsername(),signUser.getUser_id()));
            } else {
                return JSONResult.build(200,"密码错误",null);
            }
        }
        return JSONResult.build(200,"当前用户没有注册",null);
    }

    @PostMapping(path = "/relogin")
    @ResponseBody
    public  JSONResult relogin(@RequestParam(name = "token") String token) {
        String verify_token = JwtUntil.refreshToken(token);
        if (verify_token !=null) {
            return JSONResult.ok(verify_token);
        }
        return JSONResult.build(200,"验证过期，请重新登录",null);
    }

    @PostMapping(path = "/getUserInfo")
    @ResponseBody
    public  JSONResult getUserInfo(@RequestParam(name = "token") String token) {
        //验证token
        if (JwtUntil.userVerify(token)) {
            //更具token获取用户id
            Integer user_id = JwtUntil.getIDByToken(token);
            //根据user_id查询用户
            User user = userRepository.getUserByUserId(user_id);
            List<BanquetNotes> banquetNotes = banquetNotesRepository.getBanquetByUserId(user.getUser_id());
            user.setNotes(banquetNotes);
            return JSONResult.ok(user);
        }
        return JSONResult.build(200,"token已经过期，请重新登录",null);
    }
}
