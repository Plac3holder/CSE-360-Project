package App.frontend;

import App.backend.DataManager;
import App.backend.Equipment;
import App.backend.RentalRecord;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Admin_Dashboard {

    public Admin_Dashboard(Stage primaryStage) {
        BorderPane mainBox = new BorderPane();
        mainBox.setStyle("-fx-background-color: #f3f4f6;");

        // --- 1. DARK SIDEBAR (Now extends full height!) ---
        VBox sidebar = new VBox(15);
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #1f2937; -fx-padding: 20;");

        VBox logoBox = new VBox(5);
        Label logoTitle = new Label("Admin Hub");
        logoTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label logoSub = new Label("Management Console");
        logoSub.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 11px;");
        logoBox.getChildren().addAll(logoTitle, logoSub);
        logoBox.setPadding(new Insets(0, 0, 30, 0));

        Button btnOverview = createSideButton("Overview", true);
        Button btnFeed = createSideButton("Rentals Feed", false);
        Button btnUsers = createSideButton("Users Directory", false);
        Button btnSettings = createSideButton("System Settings", false);

        Button btnExit = createSideButton("Exit to App", false);
        btnExit.setOnAction(e -> {
            primaryStage.close();
            new Home_Page(new Stage());
        });

        Region sideSpacer = new Region();
        VBox.setVgrow(sideSpacer, Priority.ALWAYS);

        Button newReportBtn = new Button("+ New Report");
        newReportBtn.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-background-radius: 4; -fx-cursor: hand;");
        newReportBtn.setMaxWidth(Double.MAX_VALUE);

        sidebar.getChildren().addAll(logoBox, btnOverview, btnFeed, btnUsers, btnSettings, btnExit, sideSpacer, newReportBtn);
        mainBox.setLeft(sidebar);

        // --- 2. TOP BAR (Now embedded only over the content) ---
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0; -fx-padding: 15 20;");

        TextField adminSearch = new TextField();
        adminSearch.setPromptText("Search ID, User, Equipment...");
        adminSearch.setPrefWidth(250);
        adminSearch.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #d1d5db; -fx-border-radius: 4; -fx-padding: 6 10;");

        topBar.getChildren().add(adminSearch);

        // --- 3. MAIN CONTENT AREA ---
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(25));

        ArrayList<RentalRecord> allRentals = DataManager.loadRentalRecords();
        ArrayList<Equipment> allEquipment = DataManager.loadEquipment();

        int activeRentals = 0;
        for (RentalRecord r : allRentals) {
            if (r.getStatus().equals("ACTIVE")) activeRentals++;
        }

        // STATS CARDS ROW
        HBox statsRow = new HBox(20);
        statsRow.getChildren().addAll(
                createStatCard("ACTIVE RENTALS TOTAL", String.valueOf(activeRentals), "+12.5% this week", "#10b981"),
                createStatCard("TOTAL REGISTERED STUDENTS", "8,045", "+3.2% this week", "#10b981"),
                createStatCard("OPEN RENTAL DISPUTES", "14", "+2 pending review", "#ef4444")
        );

        // CHARTS ROW
        HBox chartsRow = new HBox(20);

        // Modern Doughnut Chart (Using StackPane hack over PieChart)
        VBox pieBox = new VBox(10);
        pieBox.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 6; -fx-padding: 20;");
        pieBox.setPrefWidth(350);
        Label pieTitle = new Label("Category Volume");
        pieTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        PieChart categoryChart = new PieChart();
        int comp = 0, out = 0, lab = 0, av = 0, oth = 0;
        for (Equipment eq : allEquipment) {
            String c = eq.getCategory().toLowerCase();
            if (c.contains("computing")) comp++;
            else if (c.contains("outdoor")) out++;
            else if (c.contains("lab")) lab++;
            else if (c.contains("audio")) av++;
            else oth++;
        }
        if (comp > 0) categoryChart.getData().add(new PieChart.Data("Computing", comp));
        if (out > 0) categoryChart.getData().add(new PieChart.Data("Outdoor", out));
        if (lab > 0) categoryChart.getData().add(new PieChart.Data("Lab Tools", lab));
        if (av > 0) categoryChart.getData().add(new PieChart.Data("Audio/Visual", av));
        if (oth > 0) categoryChart.getData().add(new PieChart.Data("Other", oth));

        categoryChart.setLegendVisible(false);
        categoryChart.setLabelsVisible(false); // Hide the ugly default lines
        categoryChart.setPrefHeight(200);

        // Create the "Doughnut" hole
        Circle hole = new Circle(60, Color.WHITE);
        VBox innerPieText = new VBox(2);
        innerPieText.setAlignment(Pos.CENTER);
        Label totalL = new Label(String.valueOf(allEquipment.size()));
        totalL.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label subL = new Label("Total Items");
        subL.setStyle("-fx-font-size: 10px; -fx-text-fill: #6b7280;");
        innerPieText.getChildren().addAll(totalL, subL);
        StackPane doughnutWrapper = new StackPane(categoryChart, hole, innerPieText);

        // Custom Legend for Pie Chart
        HBox customLegend = new HBox(15);
        customLegend.setAlignment(Pos.CENTER);
        String[] colors = {"#3b82f6", "#10b981", "#f59e0b", "#8b5cf6", "#ec4899"};
        int colorIdx = 0;
        for (PieChart.Data d : categoryChart.getData()) {
            HBox legendItem = new HBox(5);
            legendItem.setAlignment(Pos.CENTER);
            Rectangle rect = new Rectangle(10, 10, Color.web(colors[colorIdx % colors.length]));
            Label l = new Label(d.getName());
            l.setStyle("-fx-font-size: 11px; -fx-text-fill: #4b5563;");
            legendItem.getChildren().addAll(rect, l);
            customLegend.getChildren().add(legendItem);
            colorIdx++;
        }

        pieBox.getChildren().addAll(pieTitle, doughnutWrapper, customLegend);

        // Bar Chart
        VBox barBox = new VBox(10);
        barBox.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 6; -fx-padding: 20;");
        HBox.setHgrow(barBox, Priority.ALWAYS);
        Label barTitle = new Label("14-Day Performance");
        barTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setTickMarkVisible(false);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickMarkVisible(false);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setLegendVisible(false);
        barChart.setHorizontalGridLinesVisible(true);
        barChart.setVerticalGridLinesVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("1", 100));
        series.getData().add(new XYChart.Data<>("2", 200));
        series.getData().add(new XYChart.Data<>("3", 150));
        series.getData().add(new XYChart.Data<>("4", 300));
        series.getData().add(new XYChart.Data<>("5", 250));
        series.getData().add(new XYChart.Data<>("6", 400));
        series.getData().add(new XYChart.Data<>("7", 450));
        barChart.getData().add(series);
        barChart.setPrefHeight(200);

        barBox.getChildren().addAll(barTitle, barChart);
        chartsRow.getChildren().addAll(pieBox, barBox);

        // TRANSACTIONS TABLE
        VBox tableBox = new VBox();
        tableBox.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 6;");

        HBox tableHeaderBox = new HBox();
        tableHeaderBox.setPadding(new Insets(15, 20, 15, 20));
        tableHeaderBox.setAlignment(Pos.CENTER_LEFT);
        tableHeaderBox.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");
        Label tblTitle = new Label("Active Transactions & Oversight");
        tblTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Region tblSpacer = new Region();
        HBox.setHgrow(tblSpacer, Priority.ALWAYS);
        Label viewAll = new Label("View All →");
        viewAll.setStyle("-fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-font-size: 12px;");
        tableHeaderBox.getChildren().addAll(tblTitle, tblSpacer, viewAll);

        HBox colHeaders = new HBox(15);
        colHeaders.setPadding(new Insets(10, 20, 10, 20));
        colHeaders.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");
        colHeaders.getChildren().addAll(
                createColLabel("RENTER ID", 120),
                createColLabel("OWNER ID", 120),
                createColLabel("EQUIPMENT / VALUE", 250),
                createColLabel("STATUS", 100),
                createColLabel("ACTIONS", 150)
        );

        VBox rowsContainer = new VBox();
        boolean hasItems = false;
        for (RentalRecord r : allRentals) {
            if (r.getStatus().equals("ACTIVE")) {
                hasItems = true;
                String eqName = "Unknown";
                double dailyRate = 0.0;
                for (Equipment e : allEquipment) {
                    if (e.getId().equals(r.getEquipmentId())) {
                        eqName = e.getName();
                        dailyRate = e.getDailyRate();
                        break;
                    }
                }
                rowsContainer.getChildren().add(createAdminRow(r.getRenterId(), r.getOwnerId(), eqName, "$" + String.format("%.2f", dailyRate) + " / day", "Active"));
            }
        }

        if (!hasItems) {
            rowsContainer.getChildren().add(createAdminRow("USR-8921-A", "OWN-4410-C", "Sony A7III Camera Body", "$45.00 / day", "Active"));
        }

        tableBox.getChildren().addAll(tableHeaderBox, colHeaders, rowsContainer);
        contentBox.getChildren().addAll(statsRow, chartsRow, tableBox);

        ScrollPane scroll = new ScrollPane(contentBox);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #f3f4f6; -fx-border-color: transparent;");

        // --- ASSEMBLE NEW LAYOUT ---
        VBox centerMain = new VBox();
        VBox.setVgrow(scroll, Priority.ALWAYS); // Ensure scroll pane takes up remaining space
        centerMain.getChildren().addAll(topBar, scroll);
        mainBox.setCenter(centerMain);

        Scene scene = new Scene(mainBox, 1200, 750);
        primaryStage.setTitle("Admin Hub");
        primaryStage.setScene(scene);
        primaryStage.show();

        // --- OVERRIDE JAVAFX DEFAULT COLORS ---
        Platform.runLater(() -> {
            // Style Bar Chart Blue
            for (Node node : barChart.lookupAll(".default-color0.chart-bar")) {
                node.setStyle("-fx-bar-fill: #3b82f6;");
            }
            // Style Pie Chart using our color array
            int i = 0;
            for (PieChart.Data data : categoryChart.getData()) {
                data.getNode().setStyle("-fx-pie-color: " + colors[i % colors.length] + ";");
                i++;
            }
            // Hide plot background
            Node plotArea = barChart.lookup(".chart-plot-background");
            if(plotArea != null) plotArea.setStyle("-fx-background-color: transparent;");
        });
    }

    private Button createSideButton(String text, boolean isActive) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        if (isActive) {
            btn.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 15; -fx-background-radius: 4;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #d1d5db; -fx-font-weight: bold; -fx-padding: 10 15; -fx-cursor: hand;");
        }
        return btn;
    }

    private VBox createStatCard(String title, String value, String sub, String subColor) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 6;");
        HBox.setHgrow(box, Priority.ALWAYS);

        Label tLabel = new Label(title);
        tLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px; -fx-font-weight: bold;");

        Label vLabel = new Label(value);
        vLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        Label sLabel = new Label(sub);
        sLabel.setStyle("-fx-text-fill: " + subColor + "; -fx-font-size: 12px; -fx-font-weight: bold;");

        box.getChildren().addAll(tLabel, vLabel, sLabel);
        return box;
    }

    private Label createColLabel(String text, double width) {
        Label l = new Label(text);
        l.setPrefWidth(width);
        l.setStyle("-fx-font-weight: bold; -fx-text-fill: #6b7280; -fx-font-size: 11px;");
        return l;
    }

    private HBox createAdminRow(String renter, String owner, String eqName, String eqVal, String status) {
        HBox row = new HBox(15);
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0; -fx-background-color: white;");

        Label rLabel = new Label(renter); rLabel.setPrefWidth(120); rLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #4b5563; -fx-font-size: 12px;");
        Label oLabel = new Label(owner); oLabel.setPrefWidth(120); oLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #4b5563; -fx-font-size: 12px;");

        VBox eqBox = new VBox(2);
        eqBox.setPrefWidth(250);
        Label eqN = new Label(eqName); eqN.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #111827;");
        Label eqV = new Label(eqVal); eqV.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        eqBox.getChildren().addAll(eqN, eqV);

        Label statBadge = new Label(status);
        statBadge.setPrefWidth(100);
        statBadge.setAlignment(Pos.CENTER);
        if (status.equals("Active")) {
            statBadge.setStyle("-fx-background-color: #d1fae5; -fx-text-fill: #059669; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 12;");
        } else {
            statBadge.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 12;");
        }

        Button actionBtn = new Button("Flag Listing");
        actionBtn.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-text-fill: #4b5563; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 4; -fx-border-radius: 4;");

        row.getChildren().addAll(rLabel, oLabel, eqBox, statBadge, actionBtn);
        return row;
    }
}