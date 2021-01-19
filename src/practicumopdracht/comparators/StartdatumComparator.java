package practicumopdracht.comparators;

import practicumopdracht.models.Gerecht;

import java.util.Comparator;

public class StartdatumComparator implements Comparator<Gerecht> {
    private boolean sortDescending;

    public StartdatumComparator(boolean sortDescending) {
        this.sortDescending = sortDescending;
    }


    @Override
    public int compare(Gerecht o1, Gerecht o2) {
        int compareDate = o1.getStartDate().compareTo(o2.getStartDate());
        if(compareDate == 0){
            if(o2.getPrijs() < o1.getPrijs()){
                 compareDate = 1;
            }
            else compareDate = -1;
        }
        if(sortDescending){
            return -compareDate;
        }
        return compareDate;
    }
}
