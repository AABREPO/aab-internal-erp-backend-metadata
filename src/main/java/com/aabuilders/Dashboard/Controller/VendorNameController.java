package com.aabuilders.Dashboard.Controller;

import com.aabuilders.Dashboard.Entity.VendorNames;
import com.aabuilders.Dashboard.Service.VendorNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vendor_Names")
public class VendorNameController {
    @Autowired
    private VendorNameService vendorNameService;

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public VendorNames saveVendorNames(
            @RequestPart("vendor") VendorNames vendorNames,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return vendorNameService.saveVendorName(vendorNames, file);
    }
    @GetMapping("/getAll")
    public List<VendorNames> getAllVendorName(){
        return vendorNameService.getAllVendorNames();
    }
    @PutMapping(value = "/edit/{id}", consumes = {"multipart/form-data"})
    public VendorNames updateVendor(
            @PathVariable Long id,
            @RequestPart("vendor") VendorNames vendorNames,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return vendorNameService.updateVendorNames(id, vendorNames, file);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<VendorNames> getVendorNameById(@PathVariable Long id){
        return vendorNameService.getVendorNameById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/bulk_upload")
    public String uploadVendorNameData(@RequestParam("file")MultipartFile file){
        return vendorNameService.uploadVendorNameData(file);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteVendor(@PathVariable Long id){
        vendorNameService.deleteVendorName(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllVendorName(){
        vendorNameService.deleteAllVendorNames();
        return "All Vendor Name deleted";
    }
    @PutMapping("/{id}/make-store")
    public VendorNames updateMakeAsServiceStore(@PathVariable Long id, @RequestParam boolean makeAsServiceStore){
        return vendorNameService.updateServiceShopStatus(id, makeAsServiceStore);
    }
}
