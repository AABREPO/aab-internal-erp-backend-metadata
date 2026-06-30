package com.aabuilders.Dashboard.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "rcc_form_work_breadth")
public class RccFormWorkBreadth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "rcc_form_work_id", unique = true)
    @JsonIgnore
    private RccFormWork rccFormWork;

    private String breadth1;
    private String breadth2;
    private String breadth3;
    private String breadth4;
    private String breadth5;
    private String breadth6;
    private String breadth7;
    private String breadth8;
    private String breadth9;
    private String breadth10;
    private String breadth11;
    private String breadth12;
    private String breadth13;
    private String breadth14;
    private String breadth15;
    private String breadth16;
    private String breadth17;
    private String breadth18;
    private String breadth19;
    private String breadth20;
    private String breadth21;
    private String breadth22;
    private String breadth23;
    private String breadth24;
    private String breadth25;
    private String breadth26;
    private String breadth27;
    private String breadth28;
    private String breadth29;
    private String breadth30;
    private String breadth31;
    private String breadth32;
    private String breadth33;
    private String breadth34;
    private String breadth35;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public RccFormWork getRccFormWork() { return rccFormWork; }
    public void setRccFormWork(RccFormWork rccFormWork) { this.rccFormWork = rccFormWork; }

    public String getBreadth1() { return breadth1; }
    public void setBreadth1(String breadth1) { this.breadth1 = breadth1; }
    public String getBreadth2() { return breadth2; }
    public void setBreadth2(String breadth2) { this.breadth2 = breadth2; }
    public String getBreadth3() { return breadth3; }
    public void setBreadth3(String breadth3) { this.breadth3 = breadth3; }
    public String getBreadth4() { return breadth4; }
    public void setBreadth4(String breadth4) { this.breadth4 = breadth4; }
    public String getBreadth5() { return breadth5; }
    public void setBreadth5(String breadth5) { this.breadth5 = breadth5; }
    public String getBreadth6() { return breadth6; }
    public void setBreadth6(String breadth6) { this.breadth6 = breadth6; }
    public String getBreadth7() { return breadth7; }
    public void setBreadth7(String breadth7) { this.breadth7 = breadth7; }
    public String getBreadth8() { return breadth8; }
    public void setBreadth8(String breadth8) { this.breadth8 = breadth8; }
    public String getBreadth9() { return breadth9; }
    public void setBreadth9(String breadth9) { this.breadth9 = breadth9; }
    public String getBreadth10() { return breadth10; }
    public void setBreadth10(String breadth10) { this.breadth10 = breadth10; }
    public String getBreadth11() { return breadth11; }
    public void setBreadth11(String breadth11) { this.breadth11 = breadth11; }
    public String getBreadth12() { return breadth12; }
    public void setBreadth12(String breadth12) { this.breadth12 = breadth12; }
    public String getBreadth13() { return breadth13; }
    public void setBreadth13(String breadth13) { this.breadth13 = breadth13; }
    public String getBreadth14() { return breadth14; }
    public void setBreadth14(String breadth14) { this.breadth14 = breadth14; }
    public String getBreadth15() { return breadth15; }
    public void setBreadth15(String breadth15) { this.breadth15 = breadth15; }
    public String getBreadth16() { return breadth16; }
    public void setBreadth16(String breadth16) { this.breadth16 = breadth16; }
    public String getBreadth17() { return breadth17; }
    public void setBreadth17(String breadth17) { this.breadth17 = breadth17; }
    public String getBreadth18() { return breadth18; }
    public void setBreadth18(String breadth18) { this.breadth18 = breadth18; }
    public String getBreadth19() { return breadth19; }
    public void setBreadth19(String breadth19) { this.breadth19 = breadth19; }
    public String getBreadth20() { return breadth20; }
    public void setBreadth20(String breadth20) { this.breadth20 = breadth20; }
    public String getBreadth21() { return breadth21; }
    public void setBreadth21(String breadth21) { this.breadth21 = breadth21; }
    public String getBreadth22() { return breadth22; }
    public void setBreadth22(String breadth22) { this.breadth22 = breadth22; }
    public String getBreadth23() { return breadth23; }
    public void setBreadth23(String breadth23) { this.breadth23 = breadth23; }
    public String getBreadth24() { return breadth24; }
    public void setBreadth24(String breadth24) { this.breadth24 = breadth24; }
    public String getBreadth25() { return breadth25; }
    public void setBreadth25(String breadth25) { this.breadth25 = breadth25; }
    public String getBreadth26() { return breadth26; }
    public void setBreadth26(String breadth26) { this.breadth26 = breadth26; }
    public String getBreadth27() { return breadth27; }
    public void setBreadth27(String breadth27) { this.breadth27 = breadth27; }
    public String getBreadth28() { return breadth28; }
    public void setBreadth28(String breadth28) { this.breadth28 = breadth28; }
    public String getBreadth29() { return breadth29; }
    public void setBreadth29(String breadth29) { this.breadth29 = breadth29; }
    public String getBreadth30() { return breadth30; }
    public void setBreadth30(String breadth30) { this.breadth30 = breadth30; }
    public String getBreadth31() { return breadth31; }
    public void setBreadth31(String breadth31) { this.breadth31 = breadth31; }
    public String getBreadth32() { return breadth32; }
    public void setBreadth32(String breadth32) { this.breadth32 = breadth32; }
    public String getBreadth33() { return breadth33; }
    public void setBreadth33(String breadth33) { this.breadth33 = breadth33; }
    public String getBreadth34() { return breadth34; }
    public void setBreadth34(String breadth34) { this.breadth34 = breadth34; }
    public String getBreadth35() { return breadth35; }
    public void setBreadth35(String breadth35) { this.breadth35 = breadth35; }
}
