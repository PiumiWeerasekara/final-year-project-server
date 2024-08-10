package lk.earth.earthuniversity.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Room {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "number")
    private String number;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "equipments")
    private String equipments;

    public Room() {
    }

    public Room(Integer id, String number, String status, String equipments) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.equipments = equipments;
    }

    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getStatus() {
        return status;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(number, room.number) && Objects.equals(status, room.status) && Objects.equals(equipments, room.equipments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, status, equipments);
    }
}
