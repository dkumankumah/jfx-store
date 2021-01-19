package practicumopdracht.models;

import practicumopdracht.MainApplication;

import java.io.Serializable;
import java.time.LocalDate;

public class Gerecht implements Serializable {
    private String gerechtNaam;
    private String gerechtBeschrijving;
    private double prijs;
    private boolean isVegatarisch;
    private LocalDate startDate;
    private LocalDate endDate;
    private transient Restaurant hoortbij;
    private int restaurantIdx;

    public Gerecht(String gerechtNaam, String gerechtBeschrijving, double prijs, boolean isVegatarisch,
                   LocalDate startDate, LocalDate endDate, Restaurant hoortbij) {
        this.gerechtNaam = gerechtNaam;
        this.gerechtBeschrijving = gerechtBeschrijving;
        this.prijs = prijs;
        this.isVegatarisch = isVegatarisch;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hoortbij = hoortbij;
        restaurantIdx = MainApplication.getRestaurantDAO().getIdFor(hoortbij);
    }

    public Restaurant getHoortbij() {
        return hoortbij;
    }
    public void setHoortbij(Restaurant hoortbij) {
        this.hoortbij = hoortbij;
    }


    public int getRestaurantIdx() {
        return restaurantIdx;
    }

    public String getGerechtNaam() {
        return gerechtNaam;
    }

    public String getGerechtBeschrijving() {
        return gerechtBeschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public boolean isVegatarisch() {
        return isVegatarisch;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setGerechtNaam(String gerechtNaam) {
        this.gerechtNaam = gerechtNaam;
    }

    public void setGerechtBeschrijving(String gerechtBeschrijving) {
        this.gerechtBeschrijving = gerechtBeschrijving;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public void setVegatarisch(boolean vegatarisch) {
        isVegatarisch = vegatarisch;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return String.format("%s\nPrijs: %.2f\n%s", gerechtNaam, prijs, gerechtBeschrijving);
    }
}
