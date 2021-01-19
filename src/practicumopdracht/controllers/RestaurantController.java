package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import practicumopdracht.MainApplication;
import practicumopdracht.comparators.RestaurantNaamComparator;
import practicumopdracht.data.GerechtDAO;
import practicumopdracht.data.RestaurantDAO;
import practicumopdracht.models.Gerecht;
import practicumopdracht.models.Restaurant;
import practicumopdracht.views.RestaurantView;
import practicumopdracht.views.View;

public class RestaurantController extends Controller {
    private RestaurantView restaurantView;
    private RestaurantDAO restaurantDAO = MainApplication.getRestaurantDAO();
    private GerechtDAO gerechtDAO = MainApplication.getGerechtDAO();
    private Alert alert;
    private Alert bevestiging;
    private ButtonType jaButton;
    private ButtonType neeButton;
    private ButtonType result;
    private Restaurant restaurant;

    private boolean reload;

    private RestaurantNaamComparator restaurantNaamComparator;

    public RestaurantController() {
        restaurantView = new RestaurantView();

        alert = new Alert(Alert.AlertType.NONE);
        bevestiging = new Alert(Alert.AlertType.NONE);
        jaButton = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        neeButton = new ButtonType("Nee", ButtonBar.ButtonData.NO);
        bevestiging.getButtonTypes().addAll(jaButton, neeButton);

        restaurantView.getNewButton().setOnAction(actionEvent -> newRestaurant());
        restaurantView.getDeleteButton().setOnAction(actionEvent -> deleteRestaurant());
        restaurantView.getGerechtButton().setOnAction(actionEvent -> schakel());
        restaurantView.getSaveButton().setOnAction(actionEvent -> opslaan());
        restaurantView.getListRestaurant().setOnMouseClicked(e -> selectedRestaurant());
        restaurantView.getOpslaan().setOnAction(actionEvent -> saveData());
        restaurantView.getLaden().setOnAction(actionEvent -> readData());
        restaurantView.getAfsluiten().setOnAction(actionEvent -> afsluiten());
        restaurantView.getAscending().setOnAction(actionEvent -> opLopend());
        restaurantView.getDescending().setOnAction(actionEvent -> afLopend());

        restaurantNaamComparator = new RestaurantNaamComparator(false);
        refreshData();
    }

    private void readData() {
        bevestiging.setContentText("Geeft u toestemming om de gegevens te lezen");
        result = bevestiging.showAndWait().orElse(jaButton);
        if (result.getButtonData() == jaButton.getButtonData()){
            if (reload == false){
                if(restaurantDAO.load() && gerechtDAO.load()) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Alle bestanden zijn geladen");
                    refreshData();
                }
                else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Bestanden kunnen niet geladen worden");
                }
                reload = true;
            }
            if (reload)refreshData();

            alert.show();
        }
    }

    private void saveData() {
        bevestiging.setContentText("Wilt u alles opslaan?");
        result = bevestiging.showAndWait().orElse(jaButton);
        if (result.getButtonData() == jaButton.getButtonData()){
            restaurantDAO.save();
            gerechtDAO.save();
            if(restaurantDAO.save() && gerechtDAO.save()) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Alle bestanden zijn opgeslagen");
            }
            else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Er is een fout opgestreden");
            }
            alert.show();
        }
    }

    private void afsluiten() {
        saveData();
        MainApplication.close();
    }

    private void selectedRestaurant() {
        restaurant = restaurantView.getListRestaurant().getSelectionModel().getSelectedItem();
        restaurantView.getNaamRestaurant().setText(restaurant.getNaamRestaurant());
        restaurantView.getAdres().setText(restaurant.getAdres());
        restaurantView.getPostcode().setText(restaurant.getPostcode());
        restaurantView.getLocatie().setText(restaurant.getLocatie());
        restaurantView.getBeschrijving().setText(restaurant.getBeschrijving());

        restaurantView.getGerechtButton().setVisible(true);
        restaurantView.getDeleteButton().setVisible(true);

    }

    private void refreshData() {
        ObservableList<Restaurant> restaurantObservableList = FXCollections.observableArrayList(restaurantDAO.getAll());
        FXCollections.sort(restaurantObservableList, restaurantNaamComparator);

        restaurantView.getListRestaurant().setItems(restaurantObservableList);

    }


    private void opslaan() {
       if(validatie()){
           if(restaurantView.getListRestaurant().getSelectionModel().getSelectedItem() != null ){
               restaurant = restaurantView.getListRestaurant().getSelectionModel().getSelectedItem();

               restaurant.setNaamRestaurant(restaurantView.getNaamRestaurant().getText());
               restaurant.setAdres(restaurantView.getAdres().getText());
               restaurant.setPostcode(restaurantView.getPostcode().getText());
               restaurant.setLocatie(restaurantView.getLocatie().getText());
               restaurant.setBeschrijving(restaurantView.getBeschrijving().getText());
               alert.setContentText("Restaurant is gewijzigd");

           }
           else {
               String naam = restaurantView.getNaamRestaurant().getText();
               String adres = restaurantView.getAdres().getText();
               String postcode = restaurantView.getPostcode().getText();
               String location = restaurantView.getLocatie().getText();
               String beschrijving = restaurantView.getBeschrijving().getText();
               restaurant = new Restaurant(naam, adres, postcode, location, beschrijving);
               alert.setContentText("Nieuwe restaurant toegevoegd");

           }


           restaurantDAO.addOrUpdate(restaurant);
           alert.setAlertType(Alert.AlertType.INFORMATION);
           alert.show();
           clearFields();
           refreshData();
       }

    }

    private void schakel() {
        Restaurant selectedRestaurant = restaurantView.getListRestaurant().getSelectionModel().getSelectedItem();
        MainApplication.switchController(new GerechtController(selectedRestaurant));
    }

    private void deleteRestaurant() {

        bevestiging.setContentText("Wilt u dit echt verwijderen");
        bevestiging.getButtonTypes().setAll(jaButton, neeButton);

        result = bevestiging.showAndWait().orElse(jaButton);
        if(result.getButtonData() == jaButton.getButtonData()){
            Restaurant restaurant = restaurantView.getListRestaurant().getSelectionModel().getSelectedItem();

            for (Gerecht gerecht:gerechtDAO.getAllFor(restaurant)) {
                gerechtDAO.remove(gerecht);
            }

            restaurantDAO.remove(restaurant);
            restaurantView.getGerechtButton().setVisible(false);
            restaurantView.getDeleteButton().setVisible(false);
            refreshData();
            clearFields();
        }


    }

    private void afLopend() {
        restaurantNaamComparator = new RestaurantNaamComparator(true);
        refreshData();
    }

    private void opLopend() {
        restaurantNaamComparator = new RestaurantNaamComparator(false);
        refreshData();
    }

    private void newRestaurant() {
        clearFields();
        restaurantView.getNaamRestaurant().setStyle(null);
        restaurantView.getAdres().setStyle(null);
        restaurantView.getPostcode().setStyle(null);
        restaurantView.getLocatie().setStyle(null);
        restaurantView.getBeschrijving().setStyle(null);
        restaurantView.getListRestaurant().getSelectionModel().select(null);
        restaurantView.getGerechtButton().setVisible(false);
        restaurantView.getDeleteButton().setVisible(false);
    }

    private boolean validatie(){
        StringBuilder stringBuilder = new StringBuilder();

        restaurantView.getNaamRestaurant().setStyle(null);
        restaurantView.getAdres().setStyle(null);
        restaurantView.getPostcode().setStyle(null);
        restaurantView.getLocatie().setStyle(null);
        restaurantView.getBeschrijving().setStyle(null);


        if(restaurantView.getNaamRestaurant().getText().length() == 0 ||
                restaurantView.getNaamRestaurant().getText().matches("^\\s") ||
                restaurantView.getNaamRestaurant().getText().matches("\\s\\s") ||
                restaurantView.getNaamRestaurant().getText().matches("\\s$")){
            stringBuilder.append("Restaurant is leeg, begint met een spatie of bevat meerdere spaties\n");
            restaurantView.getNaamRestaurant().setStyle("-fx-border-color: red");

        }

        if(restaurantView.getAdres().getText().length() == 0 ||
                restaurantView.getAdres().getText().matches("^\\s") ||
                restaurantView.getAdres().getText().matches("\\s\\s") ||
                restaurantView.getAdres().getText().matches("\\s$")){
            stringBuilder.append("Adres is leeg, begint met een spatie of bevat meerdere spaties\n");
            restaurantView.getAdres().setStyle("-fx-border-color: red");
        }

        if(!(restaurantView.getPostcode().getText().matches("^[1-9][0-9]{3}[A-Z]{2}$"))){
            stringBuilder.append("Postcode is leeg of voldoet niet aan de eisen\n");
            restaurantView.getPostcode().setStyle("-fx-border-color: red");

        }

        if(restaurantView.getLocatie().getText().length() == 0 ||
                restaurantView.getLocatie().getText().matches("^\\s") ||
                restaurantView.getLocatie().getText().matches("\\s\\s") ||
                restaurantView.getLocatie().getText().matches("\\s$")){
            stringBuilder.append("Restaurant is leeg, begint met een spatie of bevat meerdere spaties\n");
            restaurantView.getLocatie().setStyle("-fx-border-color: red");

        }

        if(restaurantView.getBeschrijving().getText().length() == 0 ||
                restaurantView.getBeschrijving().getText().matches("^\\s") ||
                restaurantView.getBeschrijving().getText().matches("\\s\\s") ||
                restaurantView.getBeschrijving().getText().matches("\\s$")){
            stringBuilder.append("Beschrijving is leeg, begint met een spatie of bevat meerdere spaties\n");
            restaurantView.getBeschrijving().setStyle("-fx-border-color: red");

        }

        if(stringBuilder.length() > 0){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText(stringBuilder.toString());
            alert.setTitle("Opslaan");
            alert.show();

            return false;
        }
        return true;

    }

    private void clearFields(){
        restaurantView.getNaamRestaurant().clear();
        restaurantView.getAdres().clear();
        restaurantView.getPostcode().clear();
        restaurantView.getLocatie().clear();
        restaurantView.getBeschrijving().clear();

    }

    @Override
    public View getView() {
        return restaurantView;
    }
}
