package webservice.util;


import java.io.Serializable;

public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String iso;
    private String shortname;
    private String longname;
    private String callingCode;
    private int status;
    private String culture;
    private String startDate;
    private String endDate;
    private String idFacebook;
    private String imageUrl;
    private String rating;

    public Country() {
    }

    public Country(int id, String shortname, String iso, String longname, String callingCode, int status, String culture, String startDate, String endDate, String idFacebook, String imageUrl, String rating) {
        this.id = id;
        this.shortname = shortname;
        this.iso = iso;
        this.longname = longname;
        this.callingCode = callingCode;
        this.status = status;
        this.culture = culture;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idFacebook = idFacebook;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
