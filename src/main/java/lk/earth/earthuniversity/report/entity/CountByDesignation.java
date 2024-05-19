package lk.earth.earthuniversity.report.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CountByDesignation {

    private Integer id;
    //private String designation;
    private String firstName;
    private String lastName;
    private Long count;
    private double percentage;

    public CountByDesignation() {  }

//    public CountByDesignation(String designation, String firstName, String lastName, Long count) {
//        this.designation = designation;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.count = count;
//    }
    public CountByDesignation(String firstName, String lastName, Long count) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.count = count;
    }

//    public String getDesignation() {
//        return designation;
//    }
//    public void setDesignation(String designation) {
//        this.designation = designation;
//    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }
    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    public Integer getId() {
        return id;
    }

}
