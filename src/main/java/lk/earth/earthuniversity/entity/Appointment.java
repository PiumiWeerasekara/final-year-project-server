package lk.earth.earthuniversity.entity;

import javax.persistence.*;

@Entity
public class Appointment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "scheduleId", referencedColumnName = "id", nullable = false)
    private Schedule schedule;

    // Constructors, getters, and setters
}
