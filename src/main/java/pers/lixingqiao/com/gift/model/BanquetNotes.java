package pers.lixingqiao.com.gift.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Banquet_notes")
public class BanquetNotes {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer notes_id;
    private String name;
    private Integer user_id;
    private Long gmt_create;
    private Long gmt_modified;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "notes_id")
//    private List<Capital> capitals;
//
//    public List<Capital> getCapitals() {
//        return capitals;
//    }

//    public void setCapitals(List<Capital> capitals) {
//        this.capitals = capitals;
//    }

    public Integer getNotes_id() {
        return notes_id;
    }

    public void setNotes_id(Integer notes_id) {
        this.notes_id = notes_id;
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
}
