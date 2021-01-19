package practicumopdracht.data;

import practicumopdracht.models.Restaurant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextRestaurantDAO extends RestaurantDAO {
    private static String FILENAME = "Restaurantlijst";

    @Override
    public boolean save() {
        try(PrintWriter printWriter = new PrintWriter(new File(FILENAME+".txt"))){
            for (Restaurant restaurant:restaurantList){
                printWriter.print(restaurant.getNaamRestaurant() + ",");
                printWriter.print(restaurant.getAdres() + ",");
                printWriter.print(restaurant.getPostcode() + ",");
                printWriter.print(restaurant.getLocatie() + ",");
                printWriter.println(restaurant.getBeschrijving());
            }
        }catch (Exception e){
            System.err.println("Bestand kan niet opgeslagen worden");
            return false;
        }
        return true;
    }

    @Override
    public boolean load() {
        try(Scanner scanner = new Scanner(new File(FILENAME+".txt"))){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] restaurantInfo = line.split(",");
                Restaurant restaurant = new Restaurant(restaurantInfo[0], restaurantInfo[1], restaurantInfo[2],
                        restaurantInfo[3], restaurantInfo[4]);
                addOrUpdate(restaurant);
            }

        }catch (FileNotFoundException e) {
            System.err.println("Bestand niet gevonden");
            return false;
        }catch (Exception e){
            System.err.println("Restaurant bestand kan niet gelezen worden");
            return false;
        }
        return true;
    }
}
