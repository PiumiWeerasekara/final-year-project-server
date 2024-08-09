package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.AppointmentDao;
import lk.earth.earthuniversity.dao.PaymentDao;
import lk.earth.earthuniversity.dao.PrescriptionDao;
import lk.earth.earthuniversity.dao.PrescriptionDetailDao;
import lk.earth.earthuniversity.entity.Patient;
import lk.earth.earthuniversity.entity.Payment;
import lk.earth.earthuniversity.entity.Prescription;
import lk.earth.earthuniversity.entity.PrescriptionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private PrescriptionDao prescriptionDao;

    @Autowired
    private AppointmentDao appointmentDao;


    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('employee-select')")
    public List<Payment> get(@RequestParam HashMap<String, String> params) {

        List<Payment> payments = this.paymentDao.findAll();

        if (params.isEmpty()) return payments;

//        String name= params.get("name");
//        String nic= params.get("nic");
//        String specialityId= params.get("specialityId");
        Stream<Payment> estream = payments.stream();

//        if(specialityId!=null) estream = estream.filter(e -> e.getSpeciality().getId()==Integer.parseInt(specialityId));
//        if(nic!=null) estream = estream.filter(e -> e.getNic().contains(nic));
//        if(name!=null) estream = estream.filter(e -> e.getFirstName().contains(name) || e.getLastName().contains(name));

        return estream.collect(Collectors.toList());

    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<Payment> get() {

        List<Payment> payments = this.paymentDao.findAllNameId();
//
//        doctors = doctors.stream().map(
//                doctor -> {
//                    Doctor d = new Doctor(doctor.getId(), doctor.getTitle(), doctor.getFirstName(), doctor.getLastName(), doctor.getPhoto(), doctor.getDob(), doctor.getNic(), doctor.getAddress(), doctor.getContactNo(), doctor.getEmail(), doctor.getGender(), doctor.getSpeciality(), doctor.getMedicalLicenseNo(), doctor.getLicenseEXPDate());
//                    return  d;
//                }
//        ).collect(Collectors.toList());
//
        return payments;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String, String> save(@RequestBody Payment payment) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";
        payment.setBillTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        if (errors == "") {
            paymentDao.save(payment);
            prescriptionDao.updateStatusAsPayed(payment.getPrescription().getId());
            appointmentDao.updateStatusAsPayed(payment.getPrescription().getAppointment().getId());
            responce.put("id", String.valueOf(payment.getId()));
            responce.put("url", "/payments/" + payment.getId());
            responce.put("errors", errors);
        } else {
            errors = "Server Validation Errors : <br> " + errors;

            responce.put("id", String.valueOf(payment.getId()));
            responce.put("url", "/patient/" + payment.getId());
            responce.put("errors", errors);
        }
        return responce;
    }

//    @GetMapping(path = "/getRefNumber", produces = "text/plain")
//    public String getRefNumber() {
//
//
//        String lastNumber = prescriptionDao.findLastNumber();
//
//        if (lastNumber == null || lastNumber.isEmpty()) {
//            return "00000001";
//        }
//
//        int nextNumberInt;
//        try {
//            nextNumberInt = Integer.parseInt(lastNumber) + 1;
//        } catch (NumberFormatException e) {
//            throw new IllegalStateException("Invalid Ref number format: " + lastNumber, e);
//        }
//
//        // Format the new room number to 8 digits, zero-padded
//        return String.format("%08d", nextNumberInt);
//    }

    @GetMapping("/{id}")
    public Optional<Payment> get(@PathVariable Integer id) {
//        Optional<Prescription> prescription = this.prescriptionDao.findByAppointment(id);
        // Assuming 'prescription' is an Optional<Prescription>
//        Optional<Prescription> newPrescription = prescription.map(p -> new Prescription(p.getId(), p.getReferenceNo(), p.getPrescribedDate(), p.getDescription(), p.getStatus(), p.getAppointment(), p.getPrescriptionDetails()));
        Optional<Payment> payment = paymentDao.findByPrescriptionId(id);
        return payment;
        //.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


//        return newPrescription;
    }
}




