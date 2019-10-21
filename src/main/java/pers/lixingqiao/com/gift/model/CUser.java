package pers.lixingqiao.com.gift.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
}
