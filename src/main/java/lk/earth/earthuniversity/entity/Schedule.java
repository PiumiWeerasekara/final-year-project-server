package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.earth.earthuniversity.util.RegexPattern;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
public class Schedule implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "scheduleDate")
    private String scheduleDate;

    @ManyToOne
    @JoinColumn(name = "doctorId", referencedColumnName = "id", nullable = false)
    private Doctor doctor;
}
