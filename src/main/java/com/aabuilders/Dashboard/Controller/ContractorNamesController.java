package com.aabuilders.Dashboard.Controller;

import com.aabuilders.Dashboard.Entity.ContractorNames;
import com.aabuilders.Dashboard.Entity.VendorNames;
import com.aabuilders.Dashboard.Service.ContractorNameService;
import com.aabuilders.Dashboard.Service.VendorNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/contractor_Names")
public class ContractorNamesController {
    @Autowired
    private ContractorNameService contractorNameService;

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public ContractorNames saveContractor(
            @RequestPart("contractor") ContractorNames contractor,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return contractorNameService.saveContractorName(contractor, file);
    }

    @GetMapping("/getAll")
    public List<ContractorNames> getAllContractorName(){
        return contractorNameService.getAllContractorNames();
    }

    @PutMapping(value = "/edit/{id}", consumes = {"multipart/form-data"})
    public ContractorNames updateContractor(
            @PathVariable Long id,
            @RequestPart("contractor") ContractorNames contractor,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return contractorNameService.updateContractorNames(id, contractor, file);
    }

    @PostMapping("/bulk_upload")
    public String uploadContractorNameData(@RequestParam("file") MultipartFile file){
        return contractorNameService.uploadContractorNameData(file);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteContractor(@PathVariable Long id){
        contractorNameService.deleteContractorName(id);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllContractorName(){
        contractorNameService.deleteAllContractorNames();
        return "All Contractor Name deleted";
    }
}
