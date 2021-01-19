package practicumopdracht.comparators;

import practicumopdracht.models.Restaurant;

import java.util.Comparator;

public class RestaurantNaamComparator implements Comparator<Restaurant> {
    private boolean sortDescending;

    public RestaurantNaamComparator(boolean sortDescending) {
        this.sortDescending = sortDescending;
    }


    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        int compareNaam = o1.getNaamRestaurant().compareTo(o2.getNaamRestaurant());
        if(compareNaam == 0){
            compareNaam = o1.getLocatie().compareTo(o2.getLocatie());
        }
        if(sortDescending){
            return -compareNaam;
        }
        return compareNaam;
    }
}
