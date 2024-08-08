package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.PrescriptionDetailDao;
import lk.earth.earthuniversity.entity.PrescriptionDetail;
import lk.earth.earthuniversity.entity.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/prescriptionDetail")
public class PrescriptionDetailController {

    @Autowired
    private PrescriptionDetailDao prescriptionDetailDao;

    @GetMapping(path ="/list/{id}",produces = "application/json")
    public List<PrescriptionDetail> get(@PathVariable Integer id) {

        List<PrescriptionDetail> prescriptionDetails = this.prescriptionDetailDao.findAllNameId(id);

        prescriptionDetails = prescriptionDetails.stream().map(
                prescriptionDetail -> {
                    PrescriptionDetail p = new PrescriptionDetail(prescriptionDetail.getId(), prescriptionDetail.getDosage(), prescriptionDetail.getInstruction(), prescriptionDetail.getDrug());
                    return  p;
                }
        ).collect(Collectors.toList());

        return prescriptionDetails;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String,String> save(@RequestBody PrescriptionDetail prescriptionDetail){

        HashMap<String,String> responce = new HashMap<>();
//        String errors="";
//
//        if(errors=="") prescriptionDetailDao.save(prescriptionDetail);
//        else errors = "Server Validation Errors : <br> "+errors;
//
//        responce.put("id",String.valueOf(prescriptionDetail.getId()));
//        responce.put("url","/prescriptionDetail/"+prescriptionDetail.getId());
//        responce.put("errors",errors);

        return responce;
    }
}




