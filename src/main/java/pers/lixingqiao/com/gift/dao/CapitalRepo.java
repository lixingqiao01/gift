package pers.lixingqiao.com.gift.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pers.lixingqiao.com.gift.model.Capital;

import java.util.List;

public interface CapitalRepo extends CrudRepository<Capital, Integer> {
    //根据notes_id查询资金项目
    @Query(value = "select new Capital(c.capital_id,c.money,c.notes_id,cu) from Capital c,CUser cu where cu.cuser_id=c.cuser_id and c.notes_id=?1")
    public List<Capital> searchByNotesId(Integer notes_id);
}
