package pers.lixingqiao.com.gift.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.lixingqiao.com.gift.dao.CUserRepo;
import pers.lixingqiao.com.gift.dao.CapitalRepo;
import pers.lixingqiao.com.gift.model.CUser;
import pers.lixingqiao.com.gift.model.Capital;
import pers.lixingqiao.com.gift.until.JSONResult;
import pers.lixingqiao.com.gift.until.JwtUntil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/capital")
public class CapitalController {

    @Autowired
    CapitalRepo capitalRepo;
    @Autowired
    CUserRepo cUserRepo;

    //根据notes_id查询资金列表
    public JSONResult serarchCapital(@RequestParam(name = "token") String token,
                                     @RequestParam(name = "notes_id") Integer notes_id){
        if (JwtUntil.userVerify(token)) {
            return JSONResult.ok();
        } else {
            return JSONResult.build(200,"登录验证失效、请重新登录",null);
        }
    }

    @PostMapping(path = "/add")
    @ResponseBody
    public JSONResult addCapital(@RequestParam(name = "name") String name,
                                 @RequestParam(name = "money") float money,
                                 @RequestParam(name = "phoneNumber",required = false) String phoneNumber,
                                 @RequestParam(name = "remark",required = false) String remark,
                                 @RequestParam(name = "token") String token,
                                 @RequestParam(name = "notes_id",required = false) Integer notes_id) {
        if (JwtUntil.userVerify(token)){
            //首先去表cuser中查询
            List<CUser> cUsers = cUserRepo.searchCUser(name);
            if (cUsers.size() == 0) {
                //如果查询不到这个人则创建
                CUser cUser = new CUser();
                cUser.setName(name);
                cUser.setPhoneNumber(phoneNumber);
                cUser.setRemark(remark);
                cUser.setUser_id(JwtUntil.getIDByToken(token));
                cUserRepo.save(cUser);
                //保存cuser否获取cuserid
                System.out.println(cUserRepo.searchIdByCuserName(name));

                Capital capital = new Capital();
                capital.setMoney(money);
                capital.setNotes_id(notes_id);
                capital.setCuser_id(cUserRepo.searchIdByCuserName(name));
                capitalRepo.save(capital);
                Map<String,Object> map = new HashMap<>();
                map.put("cuser",cUser);
                map.put("capital",capital);
                return JSONResult.ok(map);
            } else {
                //查询到这个人则获取
                Integer cuser_id = cUserRepo.searchIdByCuserName(name);
                Capital capital = new Capital();
                capital.setMoney(money);
                capital.setNotes_id(notes_id);
                capital.setCuser_id(cuser_id);
                capitalRepo.save(capital);

                CUser cUser = cUserRepo.searchById(cuser_id);

                Map<String,Object> map = new HashMap<>();
                map.put("cuser",cUser);
                map.put("capital",capital);
                return JSONResult.ok(map);
            }
        } else {
            return JSONResult.build(200,"当前登录验证已过期，请重新登录",null);
        }
    }
}
