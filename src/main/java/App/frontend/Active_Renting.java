package App.frontend;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Active_Renting {
    public Active_Renting(Stage primaryStage) {
        BorderPane mainBox = new BorderPane();
        mainBox.setStyle("-fx-background-color: #f9fafb;");

        // --- Start of Navigation Bar ---
        BorderPane navigationMainBox = new BorderPane();
        navigationMainBox.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");
        navigationMainBox.setPadding(new Insets(10, 20, 10, 20));

        HBox navigationLeftBar = new HBox(20);
        navigationLeftBar.setAlignment(Pos.CENTER_LEFT);

        Label pageTitle = new Label("EquipRent");
        pageTitle.setStyle("-fx-font-size:22px; -fx-font-weight:bold; -fx-text-fill:#0ea5e9; -fx-padding: 0 2 0 0;");

        Button homeButton = new Button("Home");
        Button rentingButton = new Button("My Renting");
        Button cartButton = new Button("My Cart");
        Button rentalsButton = new Button("My Rentals");

        Button[] buttons = {homeButton, rentingButton, cartButton, rentalsButton};
        for (Button button : buttons) {
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 13px; -fx-font-weight: bold;");
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 13px; -fx-font-weight: bold;"));
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #111827; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 5;"));
        }

        navigationLeftBar.getChildren().addAll(pageTitle, homeButton, rentingButton, cartButton, rentalsButton);

        HBox navigationRightBar = new HBox(15);
        navigationRightBar.setAlignment(Pos.CENTER_RIGHT);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setPrefWidth(250);
        searchBar.setStyle("-fx-background-color: #f3f4f6; -fx-border-color: #d1d5db; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 5 10;");

        Button profileButton = new Button("💀");
        profileButton.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; -fx-background-radius: 20; -fx-min-width: 40; -fx-min-height: 40; -fx-max-width: 40; -fx-max-height: 40; -fx-font-size: 16px; -fx-padding: 0; -fx-alignment: center;");

        navigationRightBar.getChildren().addAll(searchBar, profileButton);

        navigationMainBox.setLeft(navigationLeftBar);
        navigationMainBox.setRight(navigationRightBar);
        mainBox.setTop(navigationMainBox);
        // --- End of Navigation Bar ---

        // --- Navigation Button Actions ---
        cartButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new My_Cart(new Stage());
        });
        rentalsButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new New_Rental(new Stage());
        });
        homeButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new Home_Page(new Stage());
        });

        // --- Main Content Area (Active Renting) ---
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(30, 40, 30, 40));

        Label reviewLabel = new Label("Active Renting");
        reviewLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827; -fx-padding: 0 2 0 0;");
        Label reviewSubtitle = new Label("Manage the equipment you are currently renting from others.");
        reviewSubtitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");
        VBox titleBox = new VBox(5, reviewLabel, reviewSubtitle);

        HBox toolbar = new HBox(10);
        toolbar.setAlignment(Pos.CENTER_RIGHT);

        TextField tableSearch = new TextField();
        tableSearch.setPromptText("Search");
        tableSearch.setPrefWidth(200);
        tableSearch.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 4; -fx-padding: 6 10;");

        Button filterBtn = new Button("Filter");
        filterBtn.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 4; -fx-padding: 6 15; -fx-cursor: hand;");

        Button exportBtn = new Button("Export");
        exportBtn.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 4; -fx-padding: 6 15; -fx-cursor: hand;");

        Region toolSpacer = new Region();
        HBox.setHgrow(toolSpacer, Priority.ALWAYS);
        toolbar.getChildren().addAll(toolSpacer, tableSearch, filterBtn, exportBtn);

        VBox tableContainer = new VBox();
        tableContainer.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;");

        HBox tableHeader = new HBox(15);
        tableHeader.setPadding(new Insets(15, 20, 15, 20));
        tableHeader.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0; -fx-background-radius: 8 8 0 0;");

        Label col1 = createHeaderLabel("Item Name", 250);
        Label col2 = createHeaderLabel("Owner ID", 120);
        Label col3 = createHeaderLabel("Expected Return Date", 160);
        Label col4 = createHeaderLabel("Security Deposit Held", 160);
        Label col5 = createHeaderLabel("Status", 100);

        tableHeader.getChildren().addAll(col1, col2, col3, col4, col5);

        VBox tableRows = new VBox();
        tableRows.getChildren().addAll(
                createTableRow("Manfrotto Professional Tripod", "Camera Gear", "USR-8921", "Oct 24, 2026", "$150.00", "ACTIVE"),
                createTableRow("Tektronix Digital Oscilloscope", "Electronics Testing", "USR-3304", "Oct 28, 2026", "$800.00", "ACTIVE"),
                createTableRow("DeWalt 20V Max Drill Set", "Power Tools", "USR-1198", "Nov 02, 2026", "$75.00", "ACTIVE"),
                createTableRow("Industrial Laser Level", "Surveying", "USR-5542", "Today", "$250.00", "DUE")
        );

        HBox tableFooter = new HBox();
        tableFooter.setPadding(new Insets(15, 20, 15, 20));
        tableFooter.setAlignment(Pos.CENTER_LEFT);
        Label showingLabel = new Label("Showing 1-4 of 4 active rentals");
        showingLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px;");
        tableFooter.getChildren().add(showingLabel);

        tableContainer.getChildren().addAll(tableHeader, tableRows, tableFooter);
        contentBox.getChildren().addAll(titleBox, toolbar, tableContainer);

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f9fafb; -fx-border-color: transparent;");

        mainBox.setCenter(scrollPane);

        Scene scene = new Scene(mainBox, 1200, 700);
        primaryStage.setTitle("EquipRent - Active Renting");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createHeaderLabel(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #6b7280; -fx-font-size: 12px;");
        return label;
    }

    private HBox createTableRow(String title, String category, String ownerId, String date, String deposit, String status) {
        HBox row = new HBox(15);
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");

        VBox itemBox = new VBox(3);
        itemBox.setPrefWidth(250);
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 14px; -fx-padding: 0 2 0 0;");
        Label catLabel = new Label(category);
        catLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px;");
        itemBox.getChildren().addAll(titleLabel, catLabel);

        Label ownerLabel = new Label(ownerId);
        ownerLabel.setPrefWidth(120);
        ownerLabel.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 14px;");

        Label dateLabel = new Label(date);
        dateLabel.setPrefWidth(160);
        if (date.equals("Today")) {
            dateLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-font-size: 14px;");
        } else {
            dateLabel.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 14px;");
        }

        Label depositLabel = new Label(deposit);
        depositLabel.setPrefWidth(160);
        depositLabel.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 14px;");

        Label statusBadge = new Label(status);
        statusBadge.setPrefWidth(80);
        statusBadge.setAlignment(Pos.CENTER);
        if (status.equals("ACTIVE")) {
            statusBadge.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #166534; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 12;");
        } else {
            statusBadge.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #991b1b; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 12;");
        }

        row.getChildren().addAll(itemBox, ownerLabel, dateLabel, depositLabel, statusBadge);

        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;"));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: transparent; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;"));

        return row;
    }
}