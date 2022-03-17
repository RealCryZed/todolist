import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class MainPageController extends MovableApplication {

    @FXML
    private AnchorPane mainScreen;

    @FXML
    private Button newTaskBtn;

    @FXML
    private Button closeButton;

    @FXML
    private SplitPane taskPane;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> tableColumn;

    @FXML
    private Text taskDate;

    @FXML
    private Text taskName;

    @FXML
    private TextArea taskText;

    @FXML
    private DatePicker calendar;

    @FXML
    private Label clock;

    @FXML
    public void initialize() {
        calendar.setValue(LocalDate.now());
        Functions.startClock(clock);

        tableColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskTable.setItems(getTasks(LocalDate.now()));
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void calendarAction(ActionEvent event) {

    }

    @FXML
    void addNewTask(ActionEvent event) throws IOException {

        Parent addTaskPage = FXMLLoader.load(getClass().getResource("fxml/addTask.fxml"));
        Scene addTaskScene = new Scene(addTaskPage);

        Stage window = (Stage) mainScreen.getScene().getWindow();

        window.setScene(addTaskScene);
        makeWindowMovable(addTaskPage, window);
        window.show();
    }

    private ObservableList<Task> getTasks(LocalDate date) {
        SessionFactory sf =  new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();

        List<Task> tempList = session.createQuery("SELECT a FROM Task a where a.date = date", Task.class).getResultList();
        ObservableList<Task> observableList = FXCollections.observableArrayList();

        for (Task task : tempList) {
            observableList.add(new Task(task.getTaskName()));
        }

        return observableList;
    }
}
