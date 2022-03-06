import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private MovableApplication movableApplication;
    private MainPageController mainPageController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        movableApplication = new MovableApplication();

        Parent root = FXMLLoader.load((getClass().getResource("fxml/main.fxml")));
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);

        movableApplication.makeWindowMovable(root, primaryStage);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
