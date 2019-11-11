package pers.lixingqiao.com.gift.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer user_id;
    private String username;
    private String name;
    private Integer gender;
    private String password;
    private Long gmt_create;
    private Long gmt_modified;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private List<BanquetNotes> notes;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private List<CUser> cUsers;

//    public List<CUser> getcUsers() {
//        return cUsers;
//    }
//
//    public void setcUsers(List<CUser> cUsers) {
//        this.cUsers = cUsers;
//    }
//
//    public List<BanquetNotes> getNotes() {
//        return notes;
//    }
//
//    public void setNotes(List<BanquetNotes> notes) {
//        this.notes = notes;
//    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Long getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(Long gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public User(Integer user_id,String name, String username,Integer gender,Long gmt_create,Long gmt_modified) {
        this.user_id = user_id;
        this.name = name;
        this.username = username;
        this.gender = gender;
        this.gmt_create = gmt_create;
        this.gmt_modified = gmt_modified;
    }

    public  User(){

    }
}
