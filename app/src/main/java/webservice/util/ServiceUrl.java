package webservice.util;


public class ServiceUrl {

    private static final String URL_WSDL = "http://sslapidev.mypush.com.br/world/countries/active";

    private static final String URL_IMAGE = "http://sslapidev.mypush.com.br/world/countries/";


    public static String getUrlWsdl() {
        return URL_WSDL;
    }

    public static String getUrlImage() {
        return URL_IMAGE;
    }
}
