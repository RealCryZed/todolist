import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainPageController extends MovableApplication {

    @FXML
    private AnchorPane mainScreen;

    @FXML
    private Button newTaskBtn;

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane backgroundPane;

    @FXML
    private AnchorPane taskPane;

    @FXML
    private Text taskDate;

    @FXML
    private Text taskName;

    @FXML
    private Button editTaskBtn;

    @FXML
    private TextArea taskText;

    @FXML
    private DatePicker calendar;

    @FXML
    private Label clock;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> tableColumn;

    ObservableList<Task> currentDayTasks;

    @FXML
    public void initialize() {
        taskPane.setVisible(false);
        calendar.setValue(LocalDate.now());
        Functions.startClock(clock);
        loadTable(LocalDate.now());
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void calendarAction(ActionEvent event) {
        loadTable(calendar.getValue());
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

    @FXML
    void selectTask(MouseEvent event) {
        try {
            if (taskTable.getItems().size() != 0) {
                taskPane.setVisible(true);
                taskName.setText(taskTable.getSelectionModel().selectedItemProperty().get().getTaskName());
                taskText.setText(taskTable.getSelectionModel().selectedItemProperty().get().getTaskText());
                taskDate.setText(taskTable.getSelectionModel().selectedItemProperty().get().getDate().format(DateTimeFormatter.ofPattern("dd.MM.YYYY")) + ", "
                        + taskTable.getSelectionModel().selectedItemProperty().get().getTime());
            }
        } catch (NullPointerException e) {
            taskPane.setVisible(false);
            System.err.println("Trying to select row with no value in it");
        }
    }

    @FXML
    void editTask(ActionEvent event) {
        if (!(taskTable.getSelectionModel().selectedItemProperty().get().getTaskText().equals(taskText.getText()))) {
            SessionFactory sessionFactory = SessionFactoryConfiguration.getSessionFactory();
            Session session = sessionFactory.openSession();

            Task task = taskTable.getSelectionModel().selectedItemProperty().get();
            task.setTaskText(taskText.getText());

            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            session.close();
        }
    }

    private ObservableList<Task> loadTable(LocalDate date) {
        SessionFactory sf = SessionFactoryConfiguration.getSessionFactory();
        Session session = sf.openSession();
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        String string_date = date.toString() + " +06";
        List<Task> tempList = session.createQuery("FROM Task a where a.date = :string_date", Task.class).setString("string_date", string_date).getResultList();

        ObservableList<Task> observableList = FXCollections.observableArrayList();

        for (Task task : tempList) {
            observableList.add(new Task(task.getId(), task.getTaskName(), task.getTaskText(), task.getDate(), task.getTime()));
        }
        currentDayTasks = observableList;
        taskTable.setItems(observableList);
        return observableList;
    }
}