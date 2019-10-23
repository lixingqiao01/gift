package pers.lixingqiao.com.gift.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.lixingqiao.com.gift.dao.BanquetNotesRepository;
import pers.lixingqiao.com.gift.model.BanquetNotes;
import pers.lixingqiao.com.gift.until.JSONResult;
import pers.lixingqiao.com.gift.until.JwtUntil;

@Controller
@RequestMapping(path = "/notes")
public class Notes {

    @Autowired
    private BanquetNotesRepository banquetNotesRepository;

    //创建Notes
    @PostMapping(path = "/create")
    @ResponseBody
    public JSONResult create(@RequestParam(name = "token") String token,
                             @RequestParam(name = "name") String name) {
        //验证token
        if (JwtUntil.userVerify(token)) {
            BanquetNotes banquetNotes = new BanquetNotes();
            banquetNotes.setGmt_create(System.currentTimeMillis());
            banquetNotes.setGmt_modified(banquetNotes.getGmt_create());
            banquetNotes.setUser_id(JwtUntil.getIDByToken(token));
            banquetNotes.setName(name);
            banquetNotesRepository.save(banquetNotes);
            return JSONResult.ok(banquetNotes);
        } else  {
            return JSONResult.build(200,"token验证不通过",null);
        }
    }


}
