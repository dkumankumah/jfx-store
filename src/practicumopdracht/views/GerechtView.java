package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import practicumopdracht.models.Gerecht;
import practicumopdracht.models.Restaurant;

public class GerechtView extends View {
    private ComboBox<Restaurant> restaurant;
    private TextField naamGerecht;
    private TextField prijs;
    private TextArea beschrijving;
    private DatePicker startDatum;
    private DatePicker eindDatum;
    private CheckBox veganBox;
    private ListView<Gerecht> gerechtListView;

    private Label restaurantLabel;
    private Label gerechtLabel;
    private Label beschrijvingLabel;
    private Label prijsLabel;
    private Label startLabel;
    private Label eindLabel;
    private Label vegatarischLabel;
    private Label datumLabel;
    private Label sortering;

    private Button saveButton;
    private Button newButton;
    private Button deleteButton;
    private Button overzichtButton;

    private RadioButton sortType1;
    private RadioButton sortType2;
    private RadioButton sortType3;
    private RadioButton sortType4;
    private ToggleGroup toggleGroup;

    private GridPane gridPane;

    private HBox buttonGroup;
    private HBox dateBox;
    private HBox radioGroup;
    private VBox root;

    public GerechtView() {
        initializeRoot();
    }

    private void initializeRoot() {
        restaurant = new ComboBox<>();
        naamGerecht = new TextField();
        prijs = new TextField();
        beschrijving = new TextArea();
        startDatum = new DatePicker();
        eindDatum = new DatePicker();
        veganBox = new CheckBox();
        gerechtListView = new ListView<Gerecht>();

        restaurantLabel = new Label("Restaurant");
        gerechtLabel = new Label("Gerecht");
        beschrijvingLabel = new Label("Beschrijving");
        prijsLabel = new Label("Prijs");
        datumLabel = new Label("Datum");
        startLabel = new Label("Start");
        eindLabel = new Label("Eind");
        vegatarischLabel = new Label("Vegatarisch?");

        restaurantLabel.setMinSize(80,10);

        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        dateBox = new HBox(10);
        dateBox.getChildren().addAll(startLabel, startDatum, eindLabel, eindDatum);

        gridPane.add(restaurantLabel, 0, 0);
        gridPane.add(restaurant, 1,0);
        gridPane.add(gerechtLabel, 0,1);
        gridPane.add(naamGerecht, 1,1);
        gridPane.add(beschrijvingLabel, 0, 2);
        gridPane.add(beschrijving, 1,2);
        gridPane.add(prijsLabel, 0,3);
        gridPane.add(prijs,1,3);
        gridPane.add(datumLabel, 0, 4);
        gridPane.add(dateBox, 1,4);
        gridPane.add(vegatarischLabel, 0,5);
        gridPane.add(veganBox, 1,5);

        restaurant.prefWidthProperty().bind(gerechtListView.widthProperty());

        saveButton = new Button("Opslaan");
        saveButton.prefWidthProperty().bind(gerechtListView.widthProperty());
        newButton = new Button("Nieuw");
        newButton.prefWidthProperty().bind(gerechtListView.widthProperty());
        deleteButton = new Button("Verwijder");
        deleteButton.prefWidthProperty().bind(gerechtListView.widthProperty());
        overzichtButton = new Button("Naar Restaurants");
        overzichtButton.prefWidthProperty().bind(gerechtListView.widthProperty());

        deleteButton.setVisible(false);

        buttonGroup = new HBox(10);
        buttonGroup.getChildren().addAll(newButton, deleteButton, overzichtButton);

        sortering = new Label("Sortering:");
        sortType1 = new RadioButton("Type #1 (A-Z)");
        sortType2 = new RadioButton("Type #1 (Z-A)");
        sortType3 = new RadioButton("Type #2 (A-Z)");
        sortType4 = new RadioButton("Type #2 (Z-A)");

        sortering.prefWidthProperty().bind(gerechtListView.widthProperty());
        sortType1.prefWidthProperty().bind(gerechtListView.widthProperty());
        sortType2.prefWidthProperty().bind(gerechtListView.widthProperty());
        sortType3.prefWidthProperty().bind(gerechtListView.widthProperty());
        sortType4.prefWidthProperty().bind(gerechtListView.widthProperty());

        toggleGroup = new ToggleGroup();
        sortType1.setToggleGroup(toggleGroup);
        sortType2.setToggleGroup(toggleGroup);
        sortType3.setToggleGroup(toggleGroup);
        sortType4.setToggleGroup(toggleGroup);

        radioGroup = new HBox(10);
        radioGroup.getChildren().addAll(sortering, sortType1, sortType2, sortType3, sortType4);


        root = new VBox(10);
        root.setPadding(new Insets(5));
        root.getChildren().addAll(gridPane, saveButton, gerechtListView, radioGroup, buttonGroup);

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

    public Button getOverzichtButton() {
        return overzichtButton;
    }

    public RadioButton getSortType1() {
        return sortType1;
    }

    public RadioButton getSortType2() {
        return sortType2;
    }

    public RadioButton getSortType3() {
        return sortType3;
    }

    public RadioButton getSortType4() {
        return sortType4;
    }

    public TextField getNaamGerecht() {
        return naamGerecht;
    }

    public ComboBox<Restaurant> getRestaurant() {
        return restaurant;
    }

    public TextField getPrijs() {
        return prijs;
    }

    public TextArea getBeschrijving() {
        return beschrijving;
    }

    public DatePicker getStartDatum() {
        return startDatum;
    }

    public DatePicker getEindDatum() {
        return eindDatum;
    }

    public CheckBox getVeganBox() {
        return veganBox;
    }

    public ListView<Gerecht> getGerechtListView() {
        return gerechtListView;
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
