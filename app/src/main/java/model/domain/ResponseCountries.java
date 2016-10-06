package model.domain;


import java.util.List;

import webservice.util.Country;

public class ResponseCountries {

    private boolean sucesso;
    private String msg;
    private List<Country> countryList;

    public ResponseCountries() {
    }

    public ResponseCountries(boolean sucesso, String msg, List<Country> countryList) {
        this.sucesso = sucesso;
        this.msg = msg;
        this.countryList = countryList;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
