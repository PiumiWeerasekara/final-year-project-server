package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.AppointmentDao;
import lk.earth.earthuniversity.dao.PrescriptionDao;
import lk.earth.earthuniversity.dao.PrescriptionDetailDao;
import lk.earth.earthuniversity.entity.Prescription;
import lk.earth.earthuniversity.entity.PrescriptionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//    @PreAuthorize("hasAuthority('employee-select')")
    public List<Prescription> get(@RequestParam HashMap<String, String> params) {

        List<Prescription>  prescriptions = this.prescriptionDao.findAll();

        if(params.isEmpty())  return prescriptions;

        String name= params.get("name");
        String nic= params.get("nic");
        String specialityId= params.get("specialityId");
        Stream<Prescription> estream = prescriptions.stream();

//        if(specialityId!=null) estream = estream.filter(e -> e.getSpeciality().getId()==Integer.parseInt(specialityId));
//        if(nic!=null) estream = estream.filter(e -> e.getNic().contains(nic));
//        if(name!=null) estream = estream.filter(e -> e.getFirstName().contains(name) || e.getLastName().contains(name));

        return estream.collect(Collectors.toList());

    }
    @GetMapping(path ="/list",produces = "application/json")
    public List<Prescription> get() {

        List<Prescription> prescriptions = this.prescriptionDao.findAllNameId();
//
//        doctors = doctors.stream().map(
//                doctor -> {
//                    Doctor d = new Doctor(doctor.getId(), doctor.getTitle(), doctor.getFirstName(), doctor.getLastName(), doctor.getPhoto(), doctor.getDob(), doctor.getNic(), doctor.getAddress(), doctor.getContactNo(), doctor.getEmail(), doctor.getGender(), doctor.getSpeciality(), doctor.getMedicalLicenseNo(), doctor.getLicenseEXPDate());
//                    return  d;
//                }
//        ).collect(Collectors.toList());
//
        return prescriptions;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String,String> save(@RequestBody Prescription prescription){

        HashMap<String,String> response = new HashMap<>();
        String errors = "";

        for (PrescriptionDetail detail : prescription.getPrescriptionDetails()) {
            detail.setPrescription(prescription);
        }

        if (errors.isEmpty()) {
            Prescription savedPrescription = prescriptionDao.save(prescription);
            appointmentDao.updateStatusAsPrescribed(prescription.getAppointment().getId());
            response.put("id", String.valueOf(savedPrescription.getId()));
            response.put("url", "/prescriptions/" + savedPrescription.getId());
        } else {
            errors = "Server Validation Errors: <br> " + errors;
            response.put("errors", errors);
        }

        response.put("id", String.valueOf(prescription.getId()));
        response.put("url", "/prescription/" + prescription.getId());
        response.put("errors", errors);

        return response;
    }
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
////    @PreAuthorize("hasAuthority('Employee-Update')")
//    public HashMap<String, String> save(@RequestBody String prescription) {
//        HashMap<String, String> response = new HashMap<>();
////        String errors = "";
////
////        // Perform validation checks if needed
////        // if (emp1 != null && employee.getId() != emp1.getId()) errors += "<br> Existing Number";
////        // if (doc != null && doctor.getId() != doc.getId()) errors += "<br> Existing NIC";
////
////        if (errors.isEmpty()) {
////            // Save the prescription
////            Prescription savedPrescription = prescriptionDao.save(prescription);
////
////            // Save the prescription details
////            for (PrescriptionDetail detail : prescription.getPrescriptionDetails()) {
////                detail.setPrescription(savedPrescription);
////                prescriptionDetailDao.save(detail);
////            }
////        } else {
////            errors = "Server Validation Errors : <br> " + errors;
////        }
////
////        response.put("id", String.valueOf(prescription.getId()));
////        response.put("url", "/prescription/" + prescription.getId());
////        response.put("errors", errors);
//
//        return response;
//    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public HashMap<String,String> delete(@PathVariable Integer id){
//
//        HashMap<String,String> responce = new HashMap<>();
//        String errors="";
//
//        Doctor doc = doctorDao.findByMyId(id);
//
//        if(doc==null)
//            errors = errors+"<br> Doctor Does Not Existed";
//
//        if(errors=="") doctorDao.delete(doc);
//        else errors = "Server Validation Errors : <br> "+errors;
//
//        responce.put("id",String.valueOf(id));
//        responce.put("url","/doctor/"+id);
//        responce.put("errors",errors);
//
//        return responce;
//    }

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
//        Optional<Prescription> prescription = this.prescriptionDao.findByAppointment(id);
        // Assuming 'prescription' is an Optional<Prescription>
//        Optional<Prescription> newPrescription = prescription.map(p -> new Prescription(p.getId(), p.getReferenceNo(), p.getPrescribedDate(), p.getDescription(), p.getStatus(), p.getAppointment(), p.getPrescriptionDetails()));
            Optional<Prescription> prescription = prescriptionDao.findByAppointmentId(id);
            return prescription;
                    //.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());



//        return newPrescription;
    }
}




