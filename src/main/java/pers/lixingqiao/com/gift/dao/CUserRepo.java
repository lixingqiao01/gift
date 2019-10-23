package pers.lixingqiao.com.gift.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pers.lixingqiao.com.gift.model.CUser;

import java.util.List;

public interface CUserRepo extends CrudRepository<CUser,Integer> {
    @Query(value = "select new CUser(cu.cuser_id,cu.name,cu.user_id,cu.remark,cu.phoneNumber) from CUser cu where name=?1")
    public List<CUser> searchCUser(String name);
}
