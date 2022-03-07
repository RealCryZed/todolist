import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class MainPageController extends MovableApplication {

    @FXML
    private Button closeButton;

    @FXML
    private Text taskDate;

    @FXML
    private Text taskName;

    @FXML
    private TextArea taskText;

    @FXML
    private Label clock;

    @FXML
    private DatePicker calendar;

    @FXML
    public void initialize() {
        calendar.setValue(LocalDate.now());
        startClock();
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void calendarAction(ActionEvent event) {

    }

    private void startClock() {
        final DateFormat format = new SimpleDateFormat( "HH:mm:ss" );
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler() {
            @Override
            public void handle(Event event) {
                final Calendar cal = Calendar.getInstance();
                clock.setText(format.format(cal.getTime()));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
