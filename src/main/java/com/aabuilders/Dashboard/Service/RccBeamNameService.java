package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.Entity.RccBeamName;
import com.aabuilders.Dashboard.Repository.RccBeamNameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RccBeamNameService {

    @Autowired
    private RccBeamNameRepository rccBeamNameRepository;

    public RccBeamName saveRccBeamName(String beamName, String formula, String rate, byte[] measurementImage,
                                       String a, boolean isAEditable, boolean isAMultiple,
                                       String b, boolean isBEditable, boolean isBMultiple,
                                       String c, boolean isCEditable, boolean isCMultiple, String steelConfiguration){
        RccBeamName beamName1 = new RccBeamName();
        beamName1.setBeamName(beamName);
        beamName1.setFormula(formula);
        beamName1.setRate(rate);
        beamName1.setMeasurementImage(measurementImage);
        beamName1.setA(a);
        beamName1.setAEditable(isAEditable);
        beamName1.setAMultiple(isAMultiple);
        beamName1.setB(b);
        beamName1.setBEditable(isBEditable);
        beamName1.setBMultiple(isBMultiple);
        beamName1.setC(c);
        beamName1.setCEditable(isCEditable);
        beamName1.setCMultiple(isCMultiple);
        beamName1.setSteelConfiguration(steelConfiguration);
        return rccBeamNameRepository.save(beamName1);
    }

    public List<RccBeamName>getAllBeamNames(){return rccBeamNameRepository.findAll();}

    public void updatedBeamName(Long id, String beamName, String formula, String rate, byte[] imageData,
                               String a, boolean isAEditable, boolean isAMultiple,
                               String b, boolean isBEditable, boolean isBMultiple,
                               String c, boolean isCEditable, boolean isCMultiple, String steelConfiguration) {
        Optional<RccBeamName> optionalBeamName = rccBeamNameRepository.findById(id);
        if (optionalBeamName.isPresent()) {
            RccBeamName beamNameEntity = optionalBeamName.get();
            beamNameEntity.setBeamName(beamName);
            beamNameEntity.setFormula(formula);
            beamNameEntity.setRate(rate);
            if (imageData != null) {
                beamNameEntity.setMeasurementImage(imageData);
            }
            beamNameEntity.setA(a);
            beamNameEntity.setAEditable(isAEditable);
            beamNameEntity.setAMultiple(isAMultiple);
            beamNameEntity.setB(b);
            beamNameEntity.setBEditable(isBEditable);
            beamNameEntity.setBMultiple(isBMultiple);
            beamNameEntity.setC(c);
            beamNameEntity.setCEditable(isCEditable);
            beamNameEntity.setCMultiple(isCMultiple);
            beamNameEntity.setSteelConfiguration(steelConfiguration);
            rccBeamNameRepository.save(beamNameEntity);
        } else {
            throw new EntityNotFoundException("Rcc Beam Name not found for id: " + id);
        }
    }


    public void deleteRccBeamName(Long id){
        rccBeamNameRepository.deleteById(id);
    }
    public void deleteAllRccBeamNames(){
        rccBeamNameRepository.deleteAll();
    }
    public void saveAll(List<RccBeamName> beamNames){
        rccBeamNameRepository.saveAll(beamNames);
    }
}
