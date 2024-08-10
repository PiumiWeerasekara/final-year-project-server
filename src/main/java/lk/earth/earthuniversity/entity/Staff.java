package lk.earth.earthuniversity.entity;

import lk.earth.earthuniversity.util.RegexPattern;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Staff {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "firstName")
    private String firstName;

    @Basic
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Column(name = "photo")
    private byte[] photo;

    @Basic
    @Column(name = "dob")
    @RegexPattern(reg = "^\\d{2}-\\d{2}-\\d{2}$", msg = "Invalid Date Format")
    private Date dob;
    @Basic
    @Column(name = "nic")
    @Pattern(regexp = "^(([\\d]{9}[vVxX])|([\\d]{12}))$", message = "Invalid NIC")
    private String nic;
    @Basic
    @Column(name = "address")
    //@Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    private String address;
    @Basic
    @Column(name = "contactNo")
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid Contact Number")
    private String contactNo;
    @Basic
    @Column(name = "email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid Email Address")
    private String email;
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id", nullable = false)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "staff_type_id", referencedColumnName = "id", nullable = false)
    private StaffType staffType;

    @Column(name = "status")
    private int status;

    public Staff() {
    }

    public Staff(Integer id, String title, String firstName, String lastName, byte[] photo, Date dob, String nic, String address, String contactNo, String email, Gender gender, StaffType staffType, int status) {
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
        this.staffType = staffType;
        this.status = status;
    }

    public Staff(Integer id, String title, String firstName, String lastName, String nic, String address, String contactNo) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.address = address;
        this.contactNo = contactNo;
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


    public Gender getGender() {
        return gender;
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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public void setStaffType(StaffType staffType) {
        this.staffType = staffType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return status == staff.status && Objects.equals(id, staff.id) && Objects.equals(title, staff.title) && Objects.equals(firstName, staff.firstName) && Objects.equals(lastName, staff.lastName) && Arrays.equals(photo, staff.photo) && Objects.equals(dob, staff.dob) && Objects.equals(nic, staff.nic) && Objects.equals(address, staff.address) && Objects.equals(contactNo, staff.contactNo) && Objects.equals(email, staff.email) && Objects.equals(gender, staff.gender) && Objects.equals(staffType, staff.staffType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, firstName, lastName, dob, nic, address, contactNo, email, gender, staffType, status);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
