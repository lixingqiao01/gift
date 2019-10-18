package pers.lixingqiao.com.gift.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pers.lixingqiao.com.gift.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {
    @Query(value = "SELECT * FROM User WHERE username=?1", nativeQuery = true)
    public User getByUserWithUsername(String username);

    @Query(value = "select * from User where username=?1 and password=?2",nativeQuery = true)
    public User getByUserWithUsernameAndPassword(String username,String password);
}
