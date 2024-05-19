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
public class Doctor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "title")
    //@Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Fullname")
    private String title;

    @Basic
    @Column(name = "firstName")
    //@Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Fullname")
    private String firstName;

    @Basic
    @Column(name = "lastName")
    //@Pattern(regexp = "^([A-Z][a-z]+)$", message = "Invalid Calligname")
    private String lastName;
    @Basic
    @Column(name = "photo")
    private byte[] photo;

    @Basic
    @Column(name = "dob")
    //@RegexPattern(reg = "^\\d{2}-\\d{2}-\\d{2}$", msg = "Invalid Date Format")
    private Date dob;
    @Basic
    @Column(name = "nic")
    //@Pattern(regexp = "^(([\\d]{9}[vVxX])|([\\d]{12}))$", message = "Invalid NIC")
    private String nic;
    @Basic
    @Column(name = "address")
    //@Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    private String address;
    @Basic
    @Column(name = "contactNo")
    //@Pattern(regexp = "^0\\d{9}$", message = "Invalid Contact Number")
    private String contactNo;
    @Basic
    @Column(name = "email")
    //@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid Landphone Number")
    private String email;
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id", nullable = false)
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id", nullable = false)
    private Speciality speciality;


    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private Collection<User> users;

    public Doctor() {
    }

    public Doctor(Integer id, String title, String firstName, String lastName, byte[] photo, Date dob, String nic, String address, String contactNo, String email, Gender gender, Speciality speciality, Collection<User> users) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.dob = dob;
        this.nic = nic;
        this.address = address;
        this.contactNo = contactNo;
        this.email = email;
        this.gender = gender;
        this.speciality = speciality;
        this.users = users;
    }

    public Doctor(Integer id, String title, String firstName, String lastName, String nic, String address, String contactNo, Speciality speciality) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.address = address;
        this.contactNo = contactNo;
        this.speciality = speciality;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Date getDob() {
        return dob;
    }

    public String getNic() {
        return nic;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getEmail() {
        return email;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id) && Objects.equals(title, doctor.title) && Objects.equals(firstName, doctor.firstName) && Objects.equals(lastName, doctor.lastName) && Arrays.equals(photo, doctor.photo) && Objects.equals(dob, doctor.dob) && Objects.equals(nic, doctor.nic) && Objects.equals(address, doctor.address) && Objects.equals(contactNo, doctor.contactNo) && Objects.equals(email, doctor.email) && Objects.equals(gender, doctor.gender) && Objects.equals(speciality, doctor.speciality) && Objects.equals(users, doctor.users);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, firstName, lastName, dob, nic, address, contactNo, email, gender, speciality, users);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
