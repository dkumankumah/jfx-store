package practicumopdracht.models;

public class Restaurant {
    private String naamRestaurant;
    private String adres;
    private String postcode;
    private String locatie;
    private String beschrijving;

    public Restaurant(String naamRestaurant, String adres, String postcode,
                      String locatie, String beschrijving) {
        this.naamRestaurant = naamRestaurant;
        this.adres = adres;
        this.postcode = postcode;
        this.locatie = locatie;
        this.beschrijving = beschrijving;
    }

    public String getNaamRestaurant() {
        return naamRestaurant;
    }

    public String getAdres() {
        return adres;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getLocatie() {
        return locatie;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setNaamRestaurant(String naamRestaurant) {
        this.naamRestaurant = naamRestaurant;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s %s\n%s", naamRestaurant, postcode, locatie, beschrijving);
    }
}
