package saim.com.nwel.Model;

public class ModelAttendance {

    String ID;
    String USER_ID;
    String USER_NAME;
    String LATITUDE;
    String LONGITUDE;
    String ALTITUDE;
    String SERIAL;
    String IMEI;
    String MODEL;
    String BRAND;
    String INSERT_TIME;

    public ModelAttendance(String ID, String USER_ID, String USER_NAME, String LATITUDE, String LONGITUDE, String ALTITUDE, String SERIAL, String IMEI, String MODEL, String BRAND, String INSERT_TIME) {
        this.ID = ID;
        this.USER_ID = USER_ID;
        this.USER_NAME = USER_NAME;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.ALTITUDE = ALTITUDE;
        this.SERIAL = SERIAL;
        this.IMEI = IMEI;
        this.MODEL = MODEL;
        this.BRAND = BRAND;
        this.INSERT_TIME = INSERT_TIME;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getALTITUDE() {
        return ALTITUDE;
    }

    public void setALTITUDE(String ALTITUDE) {
        this.ALTITUDE = ALTITUDE;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getINSERT_TIME() {
        return INSERT_TIME;
    }

    public void setINSERT_TIME(String INSERT_TIME) {
        this.INSERT_TIME = INSERT_TIME;
    }
}
