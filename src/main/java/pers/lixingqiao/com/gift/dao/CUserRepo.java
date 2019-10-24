package pers.lixingqiao.com.gift.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pers.lixingqiao.com.gift.model.CUser;

import java.util.List;

public interface CUserRepo extends CrudRepository<CUser,Integer> {
    @Query(value = "select new CUser(cu.cuser_id,cu.name,cu.user_id,cu.remark,cu.phoneNumber) from CUser cu where name=?1")
    public List<CUser> searchCUser(String name);

    @Query(value = "select cuser_id from cuser where name=?1", nativeQuery = true)
    public Integer searchIdByCuserName(String name);

    @Query(value = "select new CUser(cu.cuser_id,cu.name,cu.user_id,cu.remark,cu.phoneNumber) from CUser cu where cu.cuser_id=?1")
    public CUser searchById(Integer cuser_id);
}
