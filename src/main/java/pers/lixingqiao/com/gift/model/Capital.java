package pers.lixingqiao.com.gift.model;

import javax.persistence.*;

@Entity
public class Capital {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer capital_id;
    private float money;
    private Integer notes_id;
    private Integer cuser_id;

    //资金和联系人是一个多对一的关系，多个资金可以对应一个人
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cuser_id")

    @Transient
    private CUser cUser;

    public CUser getcUser() {
        return cUser;
    }

    public void setcUser(CUser cUser) {
        this.cUser = cUser;
    }

    public Integer getCapital_id() {
        return capital_id;
    }

    public void setCapital_id(Integer capital_id) {
        this.capital_id = capital_id;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Integer getNotes_id() {
        return notes_id;
    }

    public void setNotes_id(Integer notes_id) {
        this.notes_id = notes_id;
    }

    public Integer getCuser_id() {
        return cuser_id;
    }

    public void setCuser_id(Integer cuser_id) {
        this.cuser_id = cuser_id;
    }

    public Capital() {

    }

    public Capital(Integer capital_id,float money,Integer notes_id,CUser cUser){
        this.capital_id = capital_id;
        this.money = money;
        this.notes_id = notes_id;
        this.cUser = cUser;
    }
}

