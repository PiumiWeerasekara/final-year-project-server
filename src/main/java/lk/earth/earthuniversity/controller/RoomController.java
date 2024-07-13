package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.PatientDao;
import lk.earth.earthuniversity.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/patient")
public class PatientController {

    @Autowired
    private PatientDao patientDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('employee-select')")
    public List<Patient> get(@RequestParam HashMap<String, String> params) {

        List<Patient> patients = this.patientDao.findAll();

        if(params.isEmpty())  return patients;

        String name= params.get("name");
        String nic= params.get("nic");
        Stream<Patient> estream = patients.stream();

        if(nic!=null) estream = estream.filter(e -> e.getNic().contains(nic));
        if(name!=null) estream = estream.filter(e -> e.getFirstName().contains(name) || e.getLastName().contains(name));

        return estream.collect(Collectors.toList());

    }
    @GetMapping(path ="/list",produces = "application/json")
    public List<Patient> get() {

        List<Patient> patients = this.patientDao.findAllNameId();

        patients = patients.stream().map(
                patient -> {
                    Patient p = new Patient(patient.getId(), patient.getTitle(), patient.getFirstName(), patient.getLastName(), patient.getDob(), patient.getNic(), patient.getAddress(), patient.getContactNo(), patient.getEmail(), patient.getGender(), patient.getGuardianName(), patient.getGuardianContactNo());
                    return  p;
                }
        ).collect(Collectors.toList());

        return patients;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String,String> save(@RequestBody Patient patient){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Patient pat = patientDao.findByNic(patient.getNic());

//        if(emp1!=null && employee.getId()!=emp1.getId())
//            errors = errors+"<br> Existing Number";
        if(pat!=null && patient.getId()!=pat.getId())
            errors = errors+"<br> Existing NIC";

        if(errors=="") patientDao.save(patient);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(patient.getId()));
        responce.put("url","/patient/"+patient.getId());
        responce.put("errors",errors);

        return responce;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Patient pat = patientDao.findByMyId(id);

        if(pat==null)
            errors = errors+"<br> Patient Does Not Existed";

        if(errors=="") patientDao.delete(pat);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/patient/"+id);
        responce.put("errors",errors);

        return responce;
    }
}




