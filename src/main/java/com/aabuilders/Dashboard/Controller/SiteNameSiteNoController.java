package com.aabuilders.Dashboard.Controller;

import com.aabuilders.Dashboard.Entity.SiteNamesWithSiteNo;
import com.aabuilders.Dashboard.Service.SiteNameSiteNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/project_Names")
public class SiteNameSiteNoController {
    @Autowired
    private SiteNameSiteNoService siteNameSiteNoService;

    @PostMapping("/save")
    public SiteNamesWithSiteNo saveSiteName(@RequestBody SiteNamesWithSiteNo siteNamesWithSiteNo){
        return siteNameSiteNoService.saveSiteName(siteNamesWithSiteNo);
    }
    @GetMapping("/getAll")
    public List<SiteNamesWithSiteNo> getAllSiteName(){
        return siteNameSiteNoService.getAllSiteNameWithSiteNo();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<SiteNamesWithSiteNo> getSiteWithId(@PathVariable Long id){
        return siteNameSiteNoService.getSiteNameById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/edit/{id}")
    public SiteNamesWithSiteNo updateSiteNameWithSiteNo(@PathVariable Long id, @RequestBody SiteNamesWithSiteNo siteNamesWithSiteNo){
        return siteNameSiteNoService.updateSiteNamesWithSiteNo(id, siteNamesWithSiteNo);
    }
    @PostMapping("/bulk_upload")
    public String uploadSiteNameData(@RequestParam("file")MultipartFile file){
        return siteNameSiteNoService.uploadSiteNameData(file);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllSiteNames(){
        siteNameSiteNoService.deleteAllSiteNames();
        return "All SiteName deleted";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteSiteNames(@PathVariable Long id){
        siteNameSiteNoService.deleteSiteNames(id);
        return "SiteName deleted Successfully!!!";
    }
    @PutMapping("/{id}/stocking-location")
    public SiteNamesWithSiteNo updateStockingLocation( @PathVariable Long id, @RequestParam boolean markedAsStockingLocation) {
        return siteNameSiteNoService.updateStockingLocationStatus(id, markedAsStockingLocation);
    }
}
