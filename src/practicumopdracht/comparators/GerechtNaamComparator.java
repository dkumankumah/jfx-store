package practicumopdracht.comparators;

import practicumopdracht.models.Gerecht;

import java.util.Comparator;

public class GerechtNaamComparator implements Comparator<Gerecht> {
    private boolean sortDescending;

    public GerechtNaamComparator(boolean sortDescending) {
        this.sortDescending = sortDescending;
    }

    @Override
    public int compare(Gerecht o1, Gerecht o2) {
        int compareNaam = o1.getGerechtNaam().compareTo(o2.getGerechtNaam());
        if(sortDescending){
            return -compareNaam;
        }
        return compareNaam;
    }
}
