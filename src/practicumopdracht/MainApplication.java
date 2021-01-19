package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import practicumopdracht.controllers.Controller;
import practicumopdracht.controllers.RestaurantController;
import practicumopdracht.data.*;


public class MainApplication extends Application {

    private final String TITEL = String.format("Practicumopdracht OOP2 - %s", Main.studentNaam);
    private final int WIDTH = 640;
    private final int HEIGHT = 520;

    private static Stage stage;
//    private static RestaurantDAO restaurantDAO = new TextRestaurantDAO();
//    private static GerechtDAO gerechtDAO = new TextGerechtDAO();
    private static RestaurantDAO restaurantDAO = new BinaryRestaurantDAO();
    private static GerechtDAO gerechtDAO = new ObjectGerechtDAO();

    @Override
    public void start(Stage stage) {
        if(!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }
        this.stage = stage;
        stage.setTitle(TITEL);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        switchController(new RestaurantController());
    }

    public static void switchController(Controller controller){
        stage.setScene(new Scene(controller.getView().getRoot()));
        stage.show();
    }

    public static RestaurantDAO getRestaurantDAO(){
        return restaurantDAO;
    }

    public static GerechtDAO getGerechtDAO(){
        return gerechtDAO;
    }

    public static void close(){
        stage.close();
    }
}
