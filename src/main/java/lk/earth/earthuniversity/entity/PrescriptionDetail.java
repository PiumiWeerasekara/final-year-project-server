package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "prescription_detail")
public class PrescriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dosage", nullable = false)
    private String dosage;

    @Column(name = "instruction")
    private String instruction;

    @ManyToOne
    @JoinColumn(name = "prescription_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "drug_id", referencedColumnName = "id", nullable = false)
    private Drug drug;

    // Constructors, getters, and setters

    public PrescriptionDetail() {
    }

    public PrescriptionDetail(Integer id, String dosage, String instruction, Drug drug) {
        this.id = id;
        this.dosage = dosage;
        this.instruction = instruction;
        this.drug = drug;
    }

    public PrescriptionDetail(Integer id, String dosage, String instruction, Prescription prescription, Drug drug) {
        this.id = id;
        this.dosage = dosage;
        this.instruction = instruction;
        this.prescription = prescription;
        this.drug = drug;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionDetail that = (PrescriptionDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(dosage, that.dosage) && Objects.equals(instruction, that.instruction) && Objects.equals(prescription, that.prescription) && Objects.equals(drug, that.drug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dosage, instruction, prescription, drug);
    }
}
