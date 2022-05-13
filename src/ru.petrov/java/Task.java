import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table (name = "TASK")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column (name = "taskName")
    private String taskName;

    @Column (name = "taskText")
    private String taskText;

    @Column (name = "date")
    private LocalDate date;

    @Column (name = "time")
    private Time time;

    public Task() {
    }

    public Task(int id, String taskName, String taskText, LocalDate date, Time time) {
        this.id = id;
        this.taskName = taskName;
        this.taskText = taskText;
        this.date = date;
        this.time = time;
    }

    public Task(String taskName) {
        this.taskName = taskName;
    }
}
