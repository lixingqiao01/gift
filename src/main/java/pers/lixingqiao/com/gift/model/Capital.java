package pers.lixingqiao.com.gift.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Capital {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer capital_id;
    private float money;
}
