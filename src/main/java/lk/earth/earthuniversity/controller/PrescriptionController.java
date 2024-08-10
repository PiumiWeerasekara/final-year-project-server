package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.AppointmentDao;
import lk.earth.earthuniversity.dao.PrescriptionDao;
import lk.earth.earthuniversity.dao.PrescriptionDetailDao;
import lk.earth.earthuniversity.entity.Prescription;
import lk.earth.earthuniversity.entity.PrescriptionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionDao prescriptionDao;

    @Autowired
    private PrescriptionDetailDao prescriptionDetailDao;

    @Autowired
    private AppointmentDao appointmentDao;

    @GetMapping(produces = "application/json")
    public List<Prescription> get(@RequestParam HashMap<String, String> params) {

        List<Prescription> prescriptions = this.prescriptionDao.findAll();

        if (params.isEmpty()) return prescriptions;

        String name = params.get("name");
        String nic = params.get("nic");
        String specialityId = params.get("specialityId");
        Stream<Prescription> estream = prescriptions.stream();

//        if(specialityId!=null) estream = estream.filter(e -> e.getSpeciality().getId()==Integer.parseInt(specialityId));
//        if(nic!=null) estream = estream.filter(e -> e.getNic().contains(nic));
//        if(name!=null) estream = estream.filter(e -> e.getFirstName().contains(name) || e.getLastName().contains(name));

        return estream.collect(Collectors.toList());

    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<Prescription> get() {
        List<Prescription> prescriptions = this.prescriptionDao.findAllNameId();
        return prescriptions;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> save(@RequestBody Prescription prescription) {

        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        for (PrescriptionDetail detail : prescription.getPrescriptionDetails()) {
            detail.setPrescription(prescription);
        }

        if (errors.isEmpty()) {
            Prescription savedPrescription = prescriptionDao.save(prescription);
            appointmentDao.updateStatusAsPrescribed(prescription.getAppointment().getId());
            response.put("id", String.valueOf(savedPrescription.getId()));
            response.put("url", "/payments/" + savedPrescription.getId());
        } else {
            errors = "Server Validation Errors: <br> " + errors;
            response.put("errors", errors);
        }

        response.put("id", String.valueOf(prescription.getId()));
        response.put("url", "/prescription/" + prescription.getId());
        response.put("errors", errors);

        return response;
    }

    @GetMapping(path = "/getRefNumber", produces = "text/plain")
    public String getRefNumber() {


        String lastNumber = prescriptionDao.findLastNumber();

        if (lastNumber == null || lastNumber.isEmpty()) {
            return "00000001";
        }

        int nextNumberInt;
        try {
            nextNumberInt = Integer.parseInt(lastNumber) + 1;
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid Ref number format: " + lastNumber, e);
        }

        // Format the new room number to 8 digits, zero-padded
        return String.format("%08d", nextNumberInt);
    }

    @GetMapping("/{id}")
    public Optional<Prescription> get(@PathVariable Integer id) {
        Optional<Prescription> prescription = prescriptionDao.findByAppointmentId(id);
        return prescription;
    }

    @GetMapping(value = "/currentPrescriptionsToPay", produces = "application/json")
    public List<Prescription> currentPrescriptionsToPay(@RequestParam HashMap<String, String> params) {


        List<Prescription> prescriptions = this.prescriptionDao.findAll();

        List<Prescription> filteredPrescriptions = prescriptions.stream()
                .filter(pat -> pat.getStatus() == 1)
                .collect(Collectors.toList());

        if (params.isEmpty()) return filteredPrescriptions;

        String name = params.get("name");
        String ref = params.get("ref");
        String appointmentNo = params.get("appointmentNo");
        Stream<Prescription> estream = filteredPrescriptions.stream();

        if (ref != null) estream = estream.filter(e -> e.getReferenceNo().contains(ref));
        if (name != null)
            estream = estream.filter(e -> e.getAppointment().getPatient().getFirstName().toLowerCase().contains(name.toLowerCase()) || e.getAppointment().getPatient().getLastName().toLowerCase().contains(name.toLowerCase()));
        if (appointmentNo != null)
            estream = estream.filter(e -> e.getAppointment().getAppointmentNo() == (Integer.parseInt(appointmentNo)));

        return estream.collect(Collectors.toList());

    }

    //report
    @GetMapping(path = "/prescriptionListByPatient/{id}", produces = "application/json")
    public List<Prescription> prescriptionListByPatient(@PathVariable Integer id) {
        List<Prescription> prescriptions = this.prescriptionDao.prescriptionListByPatient(id);
        return prescriptions;

    }
}




