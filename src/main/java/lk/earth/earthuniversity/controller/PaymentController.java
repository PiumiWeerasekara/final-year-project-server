package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.AppointmentDao;
import lk.earth.earthuniversity.dao.PaymentDao;
import lk.earth.earthuniversity.dao.PrescriptionDao;
import lk.earth.earthuniversity.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<Payment> get(@RequestParam HashMap<String, String> params) {

        List<Payment> payments = this.paymentDao.findAll();

        if (params.isEmpty()) return payments;
        Stream<Payment> estream = payments.stream();
        return estream.collect(Collectors.toList());

    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<Payment> get() {
        List<Payment> payments = this.paymentDao.findAllNameId();
        return payments;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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

    @GetMapping("/{id}")
    public Optional<Payment> get(@PathVariable Integer id) {
        Optional<Payment> payment = paymentDao.findByPrescriptionId(id);
        return payment;

    }

    @GetMapping(path = "/getTotalByMonth", produces = "application/json")
    public List<Object[]> getVisitCountForCurrentYear() {
        List<Object[]> obj = this.paymentDao.getTotalByMonth();
        return obj;

    }
}




