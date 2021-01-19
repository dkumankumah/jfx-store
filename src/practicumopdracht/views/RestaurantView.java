package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import practicumopdracht.models.Restaurant;


public class RestaurantView extends View {
    private TextField naamRestaurant;
    private TextField adres;
    private TextField postcode;
    private TextField locatie;
    private TextArea beschrijving;
    private ListView<Restaurant> listRestaurant;

    private Label naamLabel;
    private Label adresLabel;
    private Label postcodeLabel;
    private Label locatieLabel;
    private Label beschrijvingLabel;

    private Button saveButton;
    private Button newButton;
    private Button deleteButton;
    private Button gerechtButton;

    private MenuBar menuBar;
    private Menu bestand;
    private Menu sorteren;
    private MenuItem opslaan;
    private MenuItem laden;
    private MenuItem afsluiten;
    private MenuItem ascending;
    private MenuItem descending;


    private GridPane gridPane;
    private BorderPane borderPane;
    private HBox buttonGroup;
    private VBox root;

    public RestaurantView() {
        initializeRoot();
    }

    protected void initializeRoot(){
        naamRestaurant = new TextField();
        adres = new TextField();
        postcode = new TextField();
        locatie = new TextField();
        beschrijving = new TextArea();
        listRestaurant = new ListView<Restaurant>();

        naamLabel = new Label("Restaurant");
        adresLabel = new Label("Adres");
        postcodeLabel = new Label("Postcode");
        locatieLabel = new Label("Plaats");
        beschrijvingLabel = new Label("Beschrijving");

        saveButton = new Button("Opslaan");
        saveButton.prefWidthProperty().bind(listRestaurant.widthProperty());
        newButton = new Button("Nieuw");
        newButton.prefWidthProperty().bind(listRestaurant.widthProperty());
        deleteButton = new Button("Verwijder");
        deleteButton.prefWidthProperty().bind(listRestaurant.widthProperty());
        gerechtButton = new Button("Naar Gerechten");
        gerechtButton.prefWidthProperty().bind(listRestaurant.widthProperty());

        deleteButton.setVisible(false);
        gerechtButton.setVisible(false);

        borderPane = new BorderPane();
        menuBar = new MenuBar();
        bestand = new Menu("Bestand");
        opslaan = new MenuItem("Opslaan");
        laden = new MenuItem("Laden");
        afsluiten = new MenuItem("Afsluiten");
        bestand.getItems().addAll(opslaan, laden, new SeparatorMenuItem(), afsluiten);

        sorteren = new Menu("Sorteren");
        ascending = new MenuItem("Naam (A-Z)");
        descending = new MenuItem("Naam (Z-A)");
        sorteren.getItems().addAll(ascending, descending);


        menuBar.getMenus().addAll(bestand, sorteren);
        borderPane.setTop(menuBar);

        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(postcode, locatieLabel, locatie);

        gridPane.add(naamLabel, 0,1);
        gridPane.add(naamRestaurant,1, 1);
        gridPane.add(adresLabel, 0,2);
        gridPane.add(adres,1, 2);
        gridPane.add(postcodeLabel, 0,3);
        gridPane.add(hBox,1,3 );
        gridPane.add(beschrijvingLabel, 0,4);
        gridPane.add(beschrijving,1, 4);

        buttonGroup = new HBox(10);
        buttonGroup.getChildren().addAll(newButton, saveButton, deleteButton, gerechtButton);

        root = new VBox(10);
        root.setPadding(new Insets(5));
        root.getChildren().addAll(borderPane, gridPane, listRestaurant, buttonGroup);

    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getNewButton() {
        return newButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getGerechtButton() {
        return gerechtButton;
    }

    public MenuItem getOpslaan() {
        return opslaan;
    }

    public MenuItem getLaden() {
        return laden;
    }

    public MenuItem getAfsluiten() {
        return afsluiten;
    }

    public MenuItem getAscending() {
        return ascending;
    }

    public MenuItem getDescending() {
        return descending;
    }

    public TextField getNaamRestaurant() {
        return naamRestaurant;
    }

    public TextField getAdres() {
        return adres;
    }

    public TextField getPostcode() {
        return postcode;
    }

    public TextField getLocatie() {
        return locatie;
    }

    public TextArea getBeschrijving() {
        return beschrijving;
    }

    public ListView<Restaurant> getListRestaurant() {
        return listRestaurant;
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
