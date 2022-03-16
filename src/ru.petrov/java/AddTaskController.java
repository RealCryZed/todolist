import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AddTaskController extends MovableApplication {

    @FXML
    private AnchorPane addTaskScreen;

    @FXML
    private Button closeButton;

    @FXML
    private TextField taskNameField;

    @FXML
    private TextArea taskText;

    @FXML
    private DatePicker calendar;

    @FXML
    private TextField hours;

    @FXML
    private TextField minutes;

    @FXML
    private Button newTaskButton;

    @FXML
    private Button backButton;

    @FXML
    private Label clock;

    @FXML
    public void initialize() {
        calendar.setValue(LocalDate.now());
        Functions.startClock(clock);
    }

    @FXML
    public void addNewTask(ActionEvent event) {

    }

    @FXML
    public void calendarAction(ActionEvent event) {

    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void returnBack(ActionEvent event) throws IOException {
        Parent addTaskPage = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        Scene addTaskScene = new Scene(addTaskPage);

        Stage window = (Stage) addTaskScreen.getScene().getWindow();

        window.setScene(addTaskScene);
        makeWindowMovable(addTaskPage, window);
        window.show();
    }
}
