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
import pers.lixingqiao.com.gift.until.Tool;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
            if (user == null) {
                return JSONResult.build(200,"无法查找到当前用户，请重新登录",null);
            } else  {
                List<BanquetNotes> banquetNotes = banquetNotesRepository.getBanquetByUserId(user.getUser_id());
//                user.setNotes(banquetNotes);
                return JSONResult.ok(user);
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
