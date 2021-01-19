package practicumopdracht.data;

import practicumopdracht.models.Gerecht;
import practicumopdracht.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public abstract class GerechtDAO implements DAO<Gerecht> {
    protected List<Gerecht> gerechtList;

    public GerechtDAO() {
        gerechtList = new ArrayList<>();
    }

    public List<Gerecht> getAllFor(Restaurant object){
        ArrayList<Gerecht> list = new ArrayList<>();

        for (Gerecht gerecht : gerechtList) {
            if(gerecht.getHoortbij() == object){
                list.add(gerecht);
            }

        }
        return list;
    }

    @Override
    public List<Gerecht> getAll() {
        return gerechtList;
    }

    @Override
    public void addOrUpdate(Gerecht object) {
        if(gerechtList.contains(object)){
            int i = gerechtList.indexOf(object);
            gerechtList.set(i, object);
        }
        else {
            gerechtList.add(object);
        }
    }

    @Override
    public void remove(Gerecht object) {
        if (gerechtList.contains(object)){
            gerechtList.remove(object);
        }
    }

    @Override
    public abstract boolean save();

    @Override
    public abstract boolean load();

}
