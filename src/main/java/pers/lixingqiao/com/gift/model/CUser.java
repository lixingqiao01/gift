package pers.lixingqiao.com.gift.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class CUser {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer cuser_id;
    private String name;
    private Integer user_id;
    private String remark;
    private String phoneNumber;
    private Integer notes_id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "cuser_id")
    private List<Capital> capitals;

    public Integer getCuser_id() {
        return cuser_id;
    }

    public void setCuser_id(Integer cuser_id) {
        this.cuser_id = cuser_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNotes_id() {
        return notes_id;
    }

    public void setNotes_id(Integer notes_id) {
        this.notes_id = notes_id;
    }

    public List<Capital> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }



    public CUser(){

    }

    public CUser(Integer cuser_id,String name,Integer user_id,String remark,String phoneNumber){
        this.cuser_id = cuser_id;
        this.name = name;
        this.user_id = user_id;
        this.remark = remark;
        this.phoneNumber = phoneNumber;
    }
}
