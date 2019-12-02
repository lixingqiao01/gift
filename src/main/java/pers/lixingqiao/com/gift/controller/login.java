package pers.lixingqiao.com.gift.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public  JSONResult getAllUsers(HttpServletRequest request) {
        List<User> list = IterableUtils.toList(userRepository.findAll());
        String path = request.getSession().getServletContext().getRealPath("");
        System.out.println(path);
        return JSONResult.ok(list);
    }





    @PostMapping(path = "/login")
    @ResponseBody
    public JSONResult loginUsername(@RequestBody String requestJson, HttpServletRequest request, HttpServletResponse response) {
        System.out.print(requestJson);
        JSONObject jsonObject = JSON.parseObject(requestJson);
        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");

        User user = userRepository.getByUserWithUsername(username);
        if (user != null) {
            if (userRepository.getByUserWithUsernameAndPassword(username,password) != null) {
                User signUser = userRepository.getByUserWithUsernameAndPassword(username,password);
//                return JSONResult.ok(JwtUntil.build(signUser.getUsername(),signUser.getUser_id()));
                return JSONResult.build(JSONResult.JsonResultStatus.SUCCESS.status, JSONResult.JsonResultStatus.SUCCESS.msg,JwtUntil.build(signUser.getUsername(),signUser.getUser_id()));
            } else {
                return JSONResult.build(JSONResult.JsonResultStatus.LOGIN_ERROR.status, JSONResult.JsonResultStatus.LOGIN_ERROR.msg,null);
            }
        }
        return JSONResult.build(JSONResult.JsonResultStatus.UNREGIST.status, JSONResult.JsonResultStatus.UNREGIST.msg,null);
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
    public  JSONResult getUserInfo(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        String token = httpServletRequest.getHeader("authorization");
        //验证token
        if (JwtUntil.userVerify(token)) {
            //根据token获取用户id
            Integer user_id = JwtUntil.getIDByToken(token);
            //根据user_id查询用户
            User user = userRepository.getUserByUserId(user_id);
            if (user == null) {
                return JSONResult.build(200,"无法查找到当前用户，请重新登录",null,token);
            } else  {
//                return JSONResult.ok(user);
                return JSONResult.build(JSONResult.JsonResultStatus.SUCCESS.status,JSONResult.JsonResultStatus.SUCCESS.msg,user,token);
            }
        }
        return JSONResult.build(200,"token已经过期，请重新登录",null);
    }

//    @/Users/bsj-mac1
//    @PostMapping(path = "/uploadImage")
//    @ResponseBody
//    public JSONResult uploadImage(@RequestParam(name = "image") MultipartFile file) {String oldName = file.getOriginalFilename();
//        String imgType = oldName.substring(oldName.lastIndexOf("."), oldName.length());
//        String name = UUID.randomUUID().toString()+imgType; // 图片名
//        String realpath = "/Users/bsj-mac1/uploadImage";
//        String fileName = writeUploadFile(file, realpath, name);
//        System.out.println(fileName);
//        return JSONResult.ok();
//    }

//    public static String writeUploadFile(MultipartFile file, String realpath, String fileName) {
//        File fileDir = new File(realpath);
//        if (!fileDir.exists())
//            fileDir.mkdirs();
//
//        InputStream input = null;
//        FileOutputStream fos = null;
//        try {
//            input = file.getInputStream();
//            fos = new FileOutputStream(realpath + "/" + fileName);
//            IOUtils.copy(input, fos);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            IOUtils.closeQuietly(input);
//            IOUtils.closeQuietly(fos);
//        }
//        return fileName;
//    }
}
