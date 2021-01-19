package practicumopdracht.data;

import practicumopdracht.models.Restaurant;

import java.io.*;

public class BinaryRestaurantDAO extends RestaurantDAO {
    private static String FILENAME = "Restaurant";

    @Override
    public boolean save() {
        try(FileOutputStream outputStream = new FileOutputStream(new File(FILENAME+".dat"))){
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            for (Restaurant restaurant:restaurantList){
                dataOutputStream.writeUTF(restaurant.getNaamRestaurant());
                dataOutputStream.writeUTF(restaurant.getAdres());
                dataOutputStream.writeUTF(restaurant.getPostcode());
                dataOutputStream.writeUTF(restaurant.getLocatie());
                dataOutputStream.writeUTF(restaurant.getBeschrijving());
            }
            dataOutputStream.close();
        }

        catch (IOException e){
            System.err.println("Restaurant-bestand kan niet opgeslagen worden");
            return false;
        }

        return true;
    }

    @Override
    public boolean load() {
        try(FileInputStream inputStream = new FileInputStream(new File(FILENAME+".dat"))){
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            while (true){
                Restaurant restaurant = new Restaurant(dataInputStream.readUTF(), dataInputStream.readUTF(),
                        dataInputStream.readUTF(), dataInputStream.readUTF(), dataInputStream.readUTF());
                addOrUpdate(restaurant);
            }

        }catch (EOFException ex) {
            System.out.println("Restaurant bestand is ingelezen");
            return true;
        }
        catch (FileNotFoundException e){
            System.err.println("Restaurant-Bestand is niet gevonden");
            return false;
        }
        catch (Exception e){
            System.err.println("Restaurant-Bestand kan niet gelezen worden");
            return false;
        }
    }
}
