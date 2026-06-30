package com.aabuilders.Dashboard.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "rcc_form_work_height")
public class RccFormWorkHeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "rcc_form_work_id", unique = true)
    @JsonIgnore
    private RccFormWork rccFormWork;

    private String height1;
    private String height2;
    private String height3;
    private String height4;
    private String height5;
    private String height6;
    private String height7;
    private String height8;
    private String height9;
    private String height10;
    private String height11;
    private String height12;
    private String height13;
    private String height14;
    private String height15;
    private String height16;
    private String height17;
    private String height18;
    private String height19;
    private String height20;
    private String height21;
    private String height22;
    private String height23;
    private String height24;
    private String height25;
    private String height26;
    private String height27;
    private String height28;
    private String height29;
    private String height30;
    private String height31;
    private String height32;
    private String height33;
    private String height34;
    private String height35;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public RccFormWork getRccFormWork() { return rccFormWork; }
    public void setRccFormWork(RccFormWork rccFormWork) { this.rccFormWork = rccFormWork; }

    public String getHeight1() { return height1; }
    public void setHeight1(String height1) { this.height1 = height1; }
    public String getHeight2() { return height2; }
    public void setHeight2(String height2) { this.height2 = height2; }
    public String getHeight3() { return height3; }
    public void setHeight3(String height3) { this.height3 = height3; }
    public String getHeight4() { return height4; }
    public void setHeight4(String height4) { this.height4 = height4; }
    public String getHeight5() { return height5; }
    public void setHeight5(String height5) { this.height5 = height5; }
    public String getHeight6() { return height6; }
    public void setHeight6(String height6) { this.height6 = height6; }
    public String getHeight7() { return height7; }
    public void setHeight7(String height7) { this.height7 = height7; }
    public String getHeight8() { return height8; }
    public void setHeight8(String height8) { this.height8 = height8; }
    public String getHeight9() { return height9; }
    public void setHeight9(String height9) { this.height9 = height9; }
    public String getHeight10() { return height10; }
    public void setHeight10(String height10) { this.height10 = height10; }
    public String getHeight11() { return height11; }
    public void setHeight11(String height11) { this.height11 = height11; }
    public String getHeight12() { return height12; }
    public void setHeight12(String height12) { this.height12 = height12; }
    public String getHeight13() { return height13; }
    public void setHeight13(String height13) { this.height13 = height13; }
    public String getHeight14() { return height14; }
    public void setHeight14(String height14) { this.height14 = height14; }
    public String getHeight15() { return height15; }
    public void setHeight15(String height15) { this.height15 = height15; }
    public String getHeight16() { return height16; }
    public void setHeight16(String height16) { this.height16 = height16; }
    public String getHeight17() { return height17; }
    public void setHeight17(String height17) { this.height17 = height17; }
    public String getHeight18() { return height18; }
    public void setHeight18(String height18) { this.height18 = height18; }
    public String getHeight19() { return height19; }
    public void setHeight19(String height19) { this.height19 = height19; }
    public String getHeight20() { return height20; }
    public void setHeight20(String height20) { this.height20 = height20; }
    public String getHeight21() { return height21; }
    public void setHeight21(String height21) { this.height21 = height21; }
    public String getHeight22() { return height22; }
    public void setHeight22(String height22) { this.height22 = height22; }
    public String getHeight23() { return height23; }
    public void setHeight23(String height23) { this.height23 = height23; }
    public String getHeight24() { return height24; }
    public void setHeight24(String height24) { this.height24 = height24; }
    public String getHeight25() { return height25; }
    public void setHeight25(String height25) { this.height25 = height25; }
    public String getHeight26() { return height26; }
    public void setHeight26(String height26) { this.height26 = height26; }
    public String getHeight27() { return height27; }
    public void setHeight27(String height27) { this.height27 = height27; }
    public String getHeight28() { return height28; }
    public void setHeight28(String height28) { this.height28 = height28; }
    public String getHeight29() { return height29; }
    public void setHeight29(String height29) { this.height29 = height29; }
    public String getHeight30() { return height30; }
    public void setHeight30(String height30) { this.height30 = height30; }
    public String getHeight31() { return height31; }
    public void setHeight31(String height31) { this.height31 = height31; }
    public String getHeight32() { return height32; }
    public void setHeight32(String height32) { this.height32 = height32; }
    public String getHeight33() { return height33; }
    public void setHeight33(String height33) { this.height33 = height33; }
    public String getHeight34() { return height34; }
    public void setHeight34(String height34) { this.height34 = height34; }
    public String getHeight35() { return height35; }
    public void setHeight35(String height35) { this.height35 = height35; }
}
