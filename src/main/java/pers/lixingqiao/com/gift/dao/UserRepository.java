package pers.lixingqiao.com.gift.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pers.lixingqiao.com.gift.model.User;


public interface UserRepository extends CrudRepository<User,Integer> {
    @Query(value = "SELECT * FROM User WHERE username=?1", nativeQuery = true)
    public User getByUserWithUsername(String username);

    @Query(value = "select * from User where username=?1 and password=?2",nativeQuery = true)
    public User getByUserWithUsernameAndPassword(String username,String password);

//    @Query(value = "select user_id,name,username,gender,gmt_create,gmt_modified FROM User u where u.user_id=?1")
    @Query(value = "select new pers.lixingqiao.com.gift.model.User(u.user_id,u.name,u.username,u.gender,u.gmt_create,u.gmt_modified) from User u where u.user_id=?1")
    public User getUserByUserId(Integer user_id);
}
