package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import practicumopdracht.MainApplication;
import practicumopdracht.comparators.GerechtNaamComparator;
import practicumopdracht.comparators.StartdatumComparator;
import practicumopdracht.data.GerechtDAO;
import practicumopdracht.data.RestaurantDAO;
import practicumopdracht.models.Gerecht;
import practicumopdracht.models.Restaurant;
import practicumopdracht.views.GerechtView;
import practicumopdracht.views.View;

import java.time.LocalDate;

public class GerechtController extends Controller {
    private GerechtView gerechtView;
    private GerechtDAO gerechtDAO = MainApplication.getGerechtDAO();
    private RestaurantDAO restaurantDAO = MainApplication.getRestaurantDAO();
    private Gerecht gerecht;
    private Alert alert;
    private StartdatumComparator startdatumComparator;
    private GerechtNaamComparator gerechtNaamComparator;

    public GerechtController(Restaurant restaurant) {
        gerechtView = new GerechtView();
        gerechtView.getRestaurant().getItems().addAll(restaurantDAO.getAll());
        alert = new Alert(Alert.AlertType.NONE);

        gerechtView.getRestaurant().setOnAction(actionEvent -> restaurantSelectie());
        gerechtView.getNewButton().setOnAction(actionEvent -> nieuwGerecht());
        gerechtView.getDeleteButton().setOnAction(actionEvent -> deleteGerecht());
        gerechtView.getOverzichtButton().setOnAction(actionEvent -> schakel());
        gerechtView.getSaveButton().setOnAction(actionEvent -> opslaan());
        gerechtView.getGerechtListView().setOnMouseClicked(e -> selectedGerecht());
        gerechtView.getSortType1().setOnAction(actionEvent -> naamAscending());
        gerechtView.getSortType2().setOnAction(actionEvent -> naamDescending());
        gerechtView.getSortType3().setOnAction(actionEvent -> datumAsending());
        gerechtView.getSortType4().setOnAction(actionEvent -> datumDesending());

        refreshData(restaurant);

        gerechtView.getSortType1().setSelected(true);
        naamAscending();

    }

    //Data ophalen met de gegeven restaurant ophalen
    private void refreshData(Restaurant restaurant) {
        ObservableList<Gerecht> gerechtObservableList = FXCollections.observableArrayList(gerechtDAO.getAllFor(restaurant));
        gerechtView.getGerechtListView().setItems(gerechtObservableList);

        gerechtView.getRestaurant().setValue(restaurant);
        clearFields();


    }

    //De geselecteerde restaurant uit de combox halen
    private void restaurantSelectie() {
        Restaurant restaurant = gerechtView.getRestaurant().getSelectionModel().getSelectedItem();
        refreshData(restaurant);
    }

    //Een gerecht selecteren vanuit de listview
    private void selectedGerecht() {
        gerecht = gerechtView.getGerechtListView().getSelectionModel().getSelectedItem();
        gerechtView.getNaamGerecht().setText(gerecht.getGerechtNaam());
        gerechtView.getBeschrijving().setText(gerecht.getGerechtBeschrijving());
        gerechtView.getPrijs().setText(String.valueOf(gerecht.getPrijs()));
        gerechtView.getStartDatum().setValue(gerecht.getStartDate());
        gerechtView.getEindDatum().setValue(gerecht.getEndDate());
        gerechtView.getVeganBox().setSelected(gerecht.isVegatarisch());

        gerechtView.getDeleteButton().setVisible(true);

    }

    //Velden legen en mocht er een gerecht aangeklikt zijn, deze deselecteren
    private void nieuwGerecht() {
        clearFields();

        gerechtView.getNaamGerecht().setStyle(null);
        gerechtView.getBeschrijving().setStyle(null);
        gerechtView.getPrijs().setStyle(null);
        gerechtView.getStartDatum().setStyle(null);
        gerechtView.getEindDatum().setStyle(null);
        gerechtView.getGerechtListView().getSelectionModel().select(null);
        gerechtView.getDeleteButton().setVisible(false);

    }

    //gerecht verwijderen uit de lijst
    private void deleteGerecht() {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Wilt u dit echt verwijderen");
        Restaurant restaurant = gerechtView.getRestaurant().getSelectionModel().getSelectedItem();

        ButtonType jaButton = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        ButtonType neeButton = new ButtonType("Nee", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(jaButton, neeButton);

        ButtonType result = alert.showAndWait().orElse(jaButton);

        if(jaButton.getButtonData() == result.getButtonData()){
            gerechtDAO.remove(gerechtView.getGerechtListView().getSelectionModel().getSelectedItem());
            refreshData(restaurant);
            clearFields();
            gerechtView.getDeleteButton().setVisible(false);
        }

    }

    //schakelen van restaurant- naar gerechtview
    private void schakel() {
        MainApplication.switchController(new RestaurantController());
    }

    //Gerecht opslaan, mocht het gerecht geselecteerd zijn dan deze updaten
    private void opslaan() {
        if(validatie()){
            if (gerechtView.getGerechtListView().getSelectionModel().getSelectedItem() != null) {
                gerecht = gerechtView.getGerechtListView().getSelectionModel().getSelectedItem();
                gerecht.setGerechtNaam(gerechtView.getNaamGerecht().getText());
                gerecht.setGerechtBeschrijving(gerechtView.getBeschrijving().getText());
                gerecht.setPrijs(Double.parseDouble(gerechtView.getPrijs().getText()));
                gerecht.setStartDate(gerechtView.getStartDatum().getValue());
                gerecht.setEndDate(gerechtView.getEindDatum().getValue());
                gerecht.setVegatarisch(gerechtView.getVeganBox().isSelected());
                alert.setContentText("Gerecht is gewijzigd");

            }
            else {

                Restaurant restaurant = gerechtView.getRestaurant().getSelectionModel().getSelectedItem();
                String naamGerecht = gerechtView.getNaamGerecht().getText();
                String beschrijving = gerechtView.getBeschrijving().getText();
                Double prijs = Double.parseDouble(gerechtView.getPrijs().getText());
                LocalDate startDate = gerechtView.getStartDatum().getValue();
                LocalDate eindDatum = gerechtView.getEindDatum().getValue();
                Boolean vegatarisch = gerechtView.getVeganBox().isSelected();

                gerecht = new Gerecht(naamGerecht, beschrijving, prijs, vegatarisch, startDate, eindDatum, restaurant);
                alert.setContentText("Gerecht is toegevoegd en opgeslagen");

            }
            gerechtDAO.addOrUpdate(gerecht);
            nieuwGerecht();
            refreshData(gerecht.getHoortbij());

        }
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.show();
    }

    private void naamAscending() {
        if(gerechtView.getSortType1().isSelected()){
            gerechtView.getGerechtListView().getItems().sort(new GerechtNaamComparator(false));
        }

    }

    private void naamDescending() {
        if(gerechtView.getSortType2().isSelected()){
            gerechtView.getGerechtListView().getItems().sort(new GerechtNaamComparator(true));
        }
    }

    private void datumAsending() {
        if(gerechtView.getSortType3().isSelected()){
            gerechtView.getGerechtListView().getItems().sort(new StartdatumComparator(false));
        }
    }

    private void datumDesending() {
        if(gerechtView.getSortType4().isSelected()){
            gerechtView.getGerechtListView().getItems().sort(new StartdatumComparator(true));
        }
    }

    private boolean validatie(){
        StringBuilder stringBuilder = new StringBuilder();

        gerechtView.getNaamGerecht().setStyle(null);
        gerechtView.getBeschrijving().setStyle(null);
        gerechtView.getPrijs().setStyle(null);
        gerechtView.getStartDatum().setStyle(null);
        gerechtView.getEindDatum().setStyle(null);

        if(gerechtView.getNaamGerecht().getText().length() == 0 ||
                gerechtView.getNaamGerecht().getText().matches("^\\s") ||
                gerechtView.getNaamGerecht().getText().matches("\\s\\s") ||
                gerechtView.getNaamGerecht().getText().matches("\\s$")){
            stringBuilder.append("Gerecht-veld is leeg, begint met een spatie of bevat meerdere spaties\n");
            gerechtView.getNaamGerecht().setStyle("-fx-border-color: red");
        }

        if(gerechtView.getBeschrijving().getText().length() == 0 ||
                gerechtView.getBeschrijving().getText().matches("^\\s") ||
                gerechtView.getBeschrijving().getText().matches("\\s\\s") ||
                gerechtView.getBeschrijving().getText().matches("\\s$")){
            stringBuilder.append("Er is geen beschrijving, begint met een spatie of bevat meerdere spaties\n");
            gerechtView.getBeschrijving().setStyle("-fx-border-color: red");
        }

        if(!(gerechtView.getPrijs().getText().matches("^\\d+\\.[0-9]{2}"))){
            stringBuilder.append("Prijs is ongeldig\n");
            gerechtView.getPrijs().setStyle("-fx-border-color: red");
        }

        if(gerechtView.getStartDatum().getValue() == null){
            stringBuilder.append("Controleer de datumvelden\n");
            gerechtView.getStartDatum().setStyle("-fx-border-color: red");
        }

        if(gerechtView.getEindDatum().getValue() == null){
            stringBuilder.append("Controleer de datumvelden\n");
            gerechtView.getEindDatum().setStyle("-fx-border-color: red");
        }

        if(gerechtView.getStartDatum().getValue() != null && gerechtView.getEindDatum().getValue() != null){
            if (gerechtView.getStartDatum().getValue().compareTo(gerechtView.getEindDatum().getValue()) > 0){
                stringBuilder.append("Eind datum is voor de start datum\n");
                gerechtView.getEindDatum().setStyle("-fx-border-color: red");
            }
            if(gerechtView.getStartDatum().getValue().compareTo(LocalDate.now()) < 0){
                stringBuilder.append("Startdatum is in het verleden");
                gerechtView.getStartDatum().setStyle("-fx-border-color: red");
            }
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
        gerechtView.getNaamGerecht().clear();
        gerechtView.getPrijs().clear();
        gerechtView.getStartDatum().setValue(null);
        gerechtView.getEindDatum().setValue(null);
        gerechtView.getBeschrijving().clear();
        gerechtView.getVeganBox().setSelected(false);

    }

    @Override
    public View getView() {
        return gerechtView;
    }
}
