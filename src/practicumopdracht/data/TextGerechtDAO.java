package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Gerecht;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class TextGerechtDAO extends GerechtDAO {
    public static final String FILENAME = "Gerechtlijst";
    @Override
    public boolean save() {
        try(PrintWriter printWriter = new PrintWriter(new File(FILENAME+".txt"))){
            for (Gerecht gerecht:gerechtList){
                printWriter.print(gerecht.getGerechtNaam() + ",");
                printWriter.print(gerecht.getGerechtBeschrijving() + ",");
                printWriter.print(gerecht.getPrijs() + ",");
                printWriter.print(gerecht.isVegatarisch() + ",");
                printWriter.print(gerecht.getStartDate() + ",");
                printWriter.print(gerecht.getEndDate() + ",");
                printWriter.println(MainApplication.getRestaurantDAO().getIdFor(gerecht.getHoortbij()));
            }
        }catch (FileNotFoundException e){
            System.err.println("Bestand niet gevonden");
            return false;
        }catch (Exception e){
            System.err.println("Bestand opgeslagen worden gewonden");
            return false;
        }
        return true;
    }

    @Override
    public boolean load() {
        try(Scanner scanner = new Scanner(new File(FILENAME+".txt"))){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] gerechtInfo = line.split(",");
                Gerecht gerecht = new Gerecht(gerechtInfo[0], gerechtInfo[1], Double.parseDouble(gerechtInfo[2]),
                        Boolean.valueOf(gerechtInfo[3]), LocalDate.parse(gerechtInfo[4]), LocalDate.parse(gerechtInfo[5]),
                        MainApplication.getRestaurantDAO().getById(Integer.valueOf(gerechtInfo[6])));
                addOrUpdate(gerecht);
            }

        }catch (FileNotFoundException e){
            System.err.println("Bestand niet gevonden");
            return false;
        }catch (Exception e){
            System.err.println("Gerecht bestand kan niet gelezen worden");
            return false;
        }
        return true;
    }
}
