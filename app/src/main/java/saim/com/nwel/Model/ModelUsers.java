package saim.com.nwel.Model;

import java.io.Serializable;

public class ModelUsers implements Serializable {
    public String ID, NAME, USER_NAME, EMAIL, PHONE, DIVISION_ID, PASSWORD, REMARKS, STATUS, PASS, IMEI, SERIAL, MODEL, BRAND;

    public ModelUsers(String ID, String NAME, String USER_NAME, String EMAIL, String PHONE, String DIVISION_ID, String PASSWORD, String REMARKS, String STATUS, String PASS, String IMEI, String SERIAL, String MODEL, String BRAND) {
        this.ID = ID;
        this.NAME = NAME;
        this.USER_NAME = USER_NAME;
        this.EMAIL = EMAIL;
        this.PHONE = PHONE;
        this.DIVISION_ID = DIVISION_ID;
        this.PASSWORD = PASSWORD;
        this.REMARKS = REMARKS;
        this.STATUS = STATUS;
        this.PASS = PASS;
        this.IMEI = IMEI;
        this.SERIAL = SERIAL;
        this.MODEL = MODEL;
        this.BRAND = BRAND;
    }

    public String getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public String getDIVISION_ID() {
        return DIVISION_ID;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getPASS() {
        return PASS;
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public String getMODEL() {
        return MODEL;
    }

    public String getBRAND() {
        return BRAND;
    }


    public void setID(String ID) {
        this.ID = ID;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public void setDIVISION_ID(String DIVISION_ID) {
        this.DIVISION_ID = DIVISION_ID;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }
}
