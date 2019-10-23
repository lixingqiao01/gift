package pers.lixingqiao.com.gift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.lixingqiao.com.gift.model.Capital;
import pers.lixingqiao.com.gift.until.JSONResult;
import pers.lixingqiao.com.gift.until.JwtUntil;

import java.util.List;

@Controller
@RequestMapping(path = "/capital")
public class CapitalController {

    //根据notes_id查询资金列表
    public JSONResult serarchCapital(@RequestParam(name = "token") String token,
                                     @RequestParam(name = "notes_id") Integer notes_id){
        if (JwtUntil.userVerify(token)) {
            return JSONResult.ok(null);
        } else {
            return JSONResult.build(200,"登录验证失效、请重新登录",null);
        }
    }
}
