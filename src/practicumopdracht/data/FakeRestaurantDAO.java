package practicumopdracht.data;

import practicumopdracht.models.Restaurant;

public class FakeRestaurantDAO extends RestaurantDAO {
    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean load() {
        addOrUpdate(new Restaurant("Wok2Walk", "Kolksteeg 8", "1012PT",
                "Amsterdam", "Wok restaurant - Chinees, Fastfood, Aziatisch"));
        addOrUpdate(new Restaurant("Phuket", "Schokkerweg 35", "2583BH",
                "Den Haag", "Restaurant Phuket heeft heerlijke Thaise gerechten met verse locale ingrediënten en uit Thailand."));
        addOrUpdate(new Restaurant("Vapiano", "Plaza 24", "3012CW",
                "Rotterdam", "Chique keten voor pasta's, pizza's en meer, te bestellen in cafetariastijl " +
                "in een modern interieur met bar."));

        return true;
    }
}
