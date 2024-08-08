package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.earth.earthuniversity.util.RegexPattern;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Drug {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;

    public Drug() {
    }

    public Drug(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        return Objects.equals(id, drug.id) && Objects.equals(name, drug.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
