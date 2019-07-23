package com.sanjnan.shopping.apps;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vinaytrueqare on 25/10/17.
 */

public enum States {

    AN("35", "AN", "Andaman and Nicobar Islands"),
    //    AP("28", "AP", "Andhra Pradesh"),
    AP("37", "AP", "Andhra Pradesh"),
    AR("12", "AR", "Arunachal Pradesh"),
    AS("18", "AS", "Assam"),
    BR("10", "BR", "Bihar"),
    CH("04", "CH", "Chandigarh"),
    CG("22", "CG", "Chhattisgarh"),
    DH("26", "DH", "Dadra and Nagar Haveli"),
    DD("25", "DD", "Daman and Diu"),
    DL("07", "DL", "Delhi", "NDLS", "New Delhi"),
    GA("30", "GA", "Goa"),
    GJ("24", "GJ", "Gujarat", "GJ", "Gujrat"),
    HR("06", "HR", "Haryana"),
    HP("02", "HP", "Himachal Pradesh"),
    JK("01", "JK", "Jammu and Kashmir"),
    JH("20", "JH", "Jharkhand"),
    KA("29", "KA", "Karnataka"),
    KL("32", "KL", "Kerala"),
    LD("31", "LD", "Lakshadweep"),
    MP("23", "MP", "Madhya Pradesh"),
    MH("27", "MH", "Maharashtra"),
    MN("14", "MN", "Manipur"),
    ML("17", "ML", "Meghalaya"),
    MZ("15", "MZ", "Mizoram"),
    NL("13", "NL", "Nagaland"),
    OD("21", "OD", "Odisha", "OR", "Orissa"),
    PB("03", "PB", "Punjab"),
    PY("34", "PY", "Pondicherry"),
    RJ("08", "RJ", "Rajasthan"),
    SK("11", "SK", "Sikkim"),
    TN("33", "TN", "Tamil Nadu"),
    TS("36", "TS", "Telangana"),
    TR("16", "TR", "Tripura"),
    UP("09", "UP", "Uttar Pradesh"),
    UK("05", "UK", "Uttarakhand"),
    WB("19", "WB", "West Bengal"),
    OT("97", "OT", "Other Territory");

    States(String stateCode, String acronym, String name) {
        this.stateCode = stateCode;
        this.acronym = acronym;
        this.name = name;
        names = new HashSet<>();
        acronyms = new HashSet<>();
    }

    States(String stateCode, String acronym, String name, String acronym1, String name1) {
        this(stateCode, acronym, name);
        acronyms.add(acronym1.toUpperCase());
        names.add(name1.toUpperCase());
    }
    States(String stateCode, String acronym, String name, String acronym1, String name1, String acronym2, String name2) {
        this(stateCode, acronym, name, acronym1, name1);
        acronyms.add(acronym2.toUpperCase());
        names.add(name2.toUpperCase());
    }
    public static States create(String name) {
        name = name.toUpperCase();
        for (States v : values()) {
            if (v.name.toUpperCase().equals(name) || v.names.contains(name) || v.acronym.equalsIgnoreCase(name) || v.acronyms.contains(name)) {
                return v;
            }
        }
        throw new IllegalArgumentException(String.format("%s is an invalid state.", name));
    }
    public static States createFromName(String name) {
        name = name.toUpperCase();
        for (States v : values()) {
            if (v.name.toUpperCase().equals(name) || v.names.contains(name)) {
                return v;
            }
        }
        throw new IllegalArgumentException(String.format("%s is an invalid state.", name));
    }
    public static States createFromAcronym(String acronym) {
        acronym = acronym.toUpperCase();
        for (States v : values()) {
            if (v.acronym.equalsIgnoreCase(acronym) || v.acronyms.contains(acronym)) {
                return v;
            }
        }
        throw new IllegalArgumentException(String.format("%s is an invalid state.", acronym));
    }
    public static int getPosition(String state) {

        int i = 0;
        state = state.toUpperCase();
        for (States v : values()) {
            if (v.name().toUpperCase().equals(state)
                    || v.acronym.toUpperCase().equals(state)
                    || v.name.toUpperCase().equals(state)
                    || v.acronyms.contains(state)
                    || v.names.contains(state)) {
                return i;
            }
            ++i;
        }
        return MP.ordinal();
    }
    public String getName() {
        return name;
    }
    public String getAcronym() {
        return acronym;
    }

    public String getStateCode() {
        return stateCode;
    }
    public static String getPlace(String state) {
        States s = create(state);
        return String.format("%s-%s", s.getStateCode(), s.getName());
    }

    private String acronym;
    private String name;
    private String stateCode;
    private Set<String> acronyms;
    private Set<String> names;

}