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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class AddTaskController extends MovableApplication {

    @FXML
    private AnchorPane addTaskScreen;

    @FXML
    private Button closeButton;

    @FXML
    private TextField taskNameField;

    @FXML
    private TextArea taskTextArea;

    @FXML
    private DatePicker taskDate;

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
        taskDate.setValue(LocalDate.now());
        Functions.startClock(clock);
    }

    @FXML
    public void addNewTask(ActionEvent event) {
        SessionFactory sf =  new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();

        Task task = new Task();

        task.setTaskName(taskNameField.getText());
        task.setTaskText(taskTextArea.getText());
        task.setDate(taskDate.getValue());

        task.setTime(Time.valueOf(LocalTime.of(Integer.parseInt(hours.getText()), Integer.parseInt(minutes.getText()), 0)));

        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
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
