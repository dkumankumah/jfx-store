package practicumopdracht.data;

import practicumopdracht.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public abstract class RestaurantDAO implements DAO<Restaurant> {
    protected List<Restaurant> restaurantList;

    public RestaurantDAO() {
        this.restaurantList = new ArrayList<>();
//        load();
    }

    public Restaurant getById(int id) {
        if (restaurantList.get(id) != null){
            return restaurantList.get(id);
        }
        else return null;
    }

    public int getIdFor(Restaurant restaurant){
        if(restaurantList.contains(restaurant)){
            return restaurantList.indexOf(restaurant);
        }
        else return -1;
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantList;
    }

    @Override
    public void addOrUpdate(Restaurant object) {
        if(restaurantList.contains(object)){
            int i = restaurantList.indexOf(object);
            restaurantList.set(i, object);
        }
        else {
            restaurantList.add(object);
        }
    }

    @Override
    public void remove(Restaurant object) {
        if (restaurantList.contains(object)){
            restaurantList.remove(object);
        }
    }

    @Override
    public abstract boolean save();

    @Override
    public abstract boolean load();

}
