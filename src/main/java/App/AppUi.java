package App;

import App.frontend.Home_Page;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppUi extends Application {
    @Override
    public void start(Stage stage) {
        new Home_Page(stage);
    }
}
