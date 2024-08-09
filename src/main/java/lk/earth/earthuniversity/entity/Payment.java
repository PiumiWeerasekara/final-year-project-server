package lk.earth.earthuniversity.entity;

import lk.earth.earthuniversity.util.RegexPattern;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Payment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "billDate")
    @RegexPattern(reg = "^\\d{2}-\\d{2}-\\d{2}$", msg = "Invalid Date Format")
    private Date billDate;

    @Column(name = "billTime")
    private String billTime;

    @Column(name = "amount")
    private int amount;

    @OneToOne
    @JoinColumn(name = "prescription_id", referencedColumnName = "id", nullable = false)
    private Prescription prescription;


    public Payment() {

    }

    public Payment(Integer id, Date billDate, String billTime, int amount, Prescription prescription) {
        this.id = id;
        this.billDate = billDate;
        this.billTime = billTime;
        this.amount = amount;
        this.prescription = prescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return amount == payment.amount && Objects.equals(id, payment.id) && Objects.equals(billDate, payment.billDate) && Objects.equals(billTime, payment.billTime) && Objects.equals(prescription, payment.prescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, billDate, billTime, amount, prescription);
    }
}
