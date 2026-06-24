package com.aabuilders.Dashboard.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rcc_beam_name")
public class RccBeamName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String beamName;
    private String formula;
    private String rate;
    private String a;
    @Column(nullable = false)
    private boolean isAEditable = false;
    @Column(nullable = false)
    private boolean isAMultiple = false;
    private String b;
    @Column(nullable = false)
    private boolean isBEditable = false;
    @Column(nullable = false)
    private boolean isBMultiple = false;
    private String c;
    @Column(nullable = false)
    private boolean isCEditable = false;
    @Column(nullable = false)
    private boolean isCMultiple = false;
    private String steelConfiguration;
    @Lob
    @Column(name = "measurement_image", columnDefinition = "LONGBLOB")
    private byte[] measurementImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeamName() {
        return beamName;
    }

    public void setBeamName(String beamName) {
        this.beamName = beamName;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public boolean isAEditable() {
        return isAEditable;
    }

    public void setAEditable(boolean AEditable) {
        isAEditable = AEditable;
    }

    public boolean isAMultiple() {
        return isAMultiple;
    }

    public void setAMultiple(boolean AMultiple) {
        isAMultiple = AMultiple;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public boolean isBEditable() {
        return isBEditable;
    }

    public void setBEditable(boolean BEditable) {
        isBEditable = BEditable;
    }

    public boolean isBMultiple() {
        return isBMultiple;
    }

    public void setBMultiple(boolean BMultiple) {
        isBMultiple = BMultiple;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public boolean isCEditable() {
        return isCEditable;
    }

    public void setCEditable(boolean CEditable) {
        isCEditable = CEditable;
    }

    public boolean isCMultiple() {
        return isCMultiple;
    }

    public void setCMultiple(boolean CMultiple) {
        isCMultiple = CMultiple;
    }

    public String getSteelConfiguration() {
        return steelConfiguration;
    }

    public void setSteelConfiguration(String steelConfiguration) {
        this.steelConfiguration = steelConfiguration;
    }

    public byte[] getMeasurementImage() {
        return measurementImage;
    }

    public void setMeasurementImage(byte[] measurementImage) {
        this.measurementImage = measurementImage;
    }
}
