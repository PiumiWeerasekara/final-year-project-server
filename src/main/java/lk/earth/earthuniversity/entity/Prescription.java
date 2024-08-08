package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lk.earth.earthuniversity.dao.AppointmentDao;
import lk.earth.earthuniversity.util.RegexPattern;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Prescription {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "referenceNo")
    private String referenceNo;
    @Column(name = "prescribedDate")
    @RegexPattern(reg = "^\\d{2}-\\d{2}-\\d{2}$", msg = "Invalid Date Format")
    private Date prescribedDate;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false)
    private Appointment appointment;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PrescriptionDetail> prescriptionDetails;

    public Prescription() {

    }

    public Prescription(Integer id, String referenceNo, Date prescribedDate, String description, int status, Appointment appointment) {
        this.id = id;
        this.referenceNo = referenceNo;
        this.prescribedDate = prescribedDate;
        this.description = description;
        this.status = status;
        this.appointment = appointment;
    }

    public Prescription(Integer id, String referenceNo, Date prescribedDate, String description, int status, Appointment appointment, List<PrescriptionDetail> prescriptionDetails) {
        this.id = id;
        this.referenceNo = referenceNo;
        this.prescribedDate = prescribedDate;
        this.description = description;
        this.status = status;
        this.appointment = appointment;
        this.prescriptionDetails = prescriptionDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Date getPrescribedDate() {
        return prescribedDate;
    }

    public void setPrescribedDate(Date prescribedDate) {
        this.prescribedDate = prescribedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public List<PrescriptionDetail> getPrescriptionDetails() {
        return prescriptionDetails;
    }

    public void setPrescriptionDetails(List<PrescriptionDetail> prescriptionDetails) {
        this.prescriptionDetails = prescriptionDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return status == that.status && Objects.equals(id, that.id) && Objects.equals(referenceNo, that.referenceNo) && Objects.equals(prescribedDate, that.prescribedDate) && Objects.equals(description, that.description) && Objects.equals(appointment, that.appointment) && Objects.equals(prescriptionDetails, that.prescriptionDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, referenceNo, prescribedDate, description, status, appointment, prescriptionDetails);
    }
}
