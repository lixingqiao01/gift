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

//    public CUser getcUser() {
//        return cUser;
//    }
//
//    public void setcUser(CUser cUser) {
//        this.cUser = cUser;
//    }

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
}
