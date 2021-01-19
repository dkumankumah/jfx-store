package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Gerecht;

import java.time.LocalDate;

public class FakeGerechtDAO extends GerechtDAO {
    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean load() {
        addOrUpdate(new Gerecht("Nummer 1", "Eiernoedels + rundvlees + broccoli + paprikamix + " +
                "cashewnoten + Tokyo-saus + Mix van sesamzaad", 15.75, false, LocalDate.now(),
                LocalDate.now().plusDays(7), MainApplication.getRestaurantDAO().getById(0)));

        addOrUpdate(new Gerecht("KAI YAD SAI", "Omelet gevuld met groenten", 16.00, true,
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(8), MainApplication.getRestaurantDAO().getById(1)));

        addOrUpdate(new Gerecht("Carbonara", "Pasta, ei, parmezaanse kaas en spekreepjes", 8.99, false,
                LocalDate.now().plusDays(3), LocalDate.now().plusDays(7), MainApplication.getRestaurantDAO().getById(2)));

        addOrUpdate(new Gerecht("Nummer 2", "Eiernoedels + tofu + broccoli + paprikamix + " +
                "cashewnoten + Tokyo-saus + Mix van sesamzaad", 15.75, true, LocalDate.now(),
                LocalDate.now().plusDays(7), MainApplication.getRestaurantDAO().getById(0)));
        return true;
    }
}
