package pers.lixingqiao.com.gift.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pers.lixingqiao.com.gift.model.BanquetNotes;

import java.util.List;

public interface BanquetNotesRepository extends CrudRepository<BanquetNotes,Integer> {
    @Query(value = "SELECT * FROM banquet_notes WHERE user_id=?1", nativeQuery = true)
    public List<BanquetNotes> getBanquetByUserId(Integer user_id);
}
