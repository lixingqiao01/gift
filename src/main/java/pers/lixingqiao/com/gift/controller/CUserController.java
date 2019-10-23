package pers.lixingqiao.com.gift.controller;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.lixingqiao.com.gift.dao.CUserRepo;
import pers.lixingqiao.com.gift.model.CUser;
import pers.lixingqiao.com.gift.until.JSONResult;
import pers.lixingqiao.com.gift.until.JwtUntil;

import java.util.List;

@Controller
@RequestMapping(path = "/cuser")
public class CUserController {

    @Autowired
    private CUserRepo cUserRepo;

    //所有联系人
    @PostMapping(path = "/search")
    @ResponseBody
    public JSONResult searchCUser(@RequestParam(name = "token") String token,
                                  @RequestParam(name = "name") String name) {
        //验证token
        if (JwtUntil.userVerify(token)) {
            //查询Cuser
            List<CUser> cUsers = cUserRepo.searchCUser(name);
            return JSONResult.ok(cUsers);
        } else  {
            return JSONResult.build(200,"登录验证失效、请重新登录",null);
        }
    }

    //创建联系人
    @PostMapping(path = "/add")
    @ResponseBody
    public JSONResult addCUser(@RequestParam(name = "token") String token,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "remark",required = false) String remark,
                               @RequestParam(name = "phoneNumber", required = false) String phoneNumber) {
        if (JwtUntil.userVerify(token)) {
            CUser cUser = new CUser();
            cUser.setName(name);
            cUser.setPhoneNumber(phoneNumber);
            cUser.setRemark(remark);
            cUserRepo.save(cUser);
            return JSONResult.ok(cUser);
        } else {
            return JSONResult.build(200,"登录验证失效、请重新登录",null);
        }
    }
}
