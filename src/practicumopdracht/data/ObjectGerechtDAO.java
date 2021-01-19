package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Gerecht;

import java.io.*;
import java.util.List;

public class ObjectGerechtDAO extends GerechtDAO {
    private static String FILENAME = "Gerecht";

    @Override
    public boolean save() {
        try(FileOutputStream outputStream = new FileOutputStream(new File(FILENAME+".dat"))){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(gerechtList);
            objectOutputStream.close();
            return true;
        }catch (Exception e){
            System.err.println("Bestand kan niet opgeslagen worden");
            return false;
        }
    }

    @Override
    public boolean load() {
        try(FileInputStream inputStream = new FileInputStream(new File(FILENAME+".dat"))){
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            gerechtList = (List<Gerecht>) objectInputStream.readObject();
            for (Gerecht gerecht : gerechtList) {
                gerecht.setHoortbij(MainApplication.getRestaurantDAO().getById(gerecht.getRestaurantIdx()));
            }
            objectInputStream.close();
        }
        catch (FileNotFoundException e){
            System.err.println("Gerecht-Bestand is niet gevonden");
            return false;
        }
        catch (Exception e){
            System.err.println("Gerecht-bestand kan niet gelezen worden");
            return false;
        }
        return true;
    }
}
