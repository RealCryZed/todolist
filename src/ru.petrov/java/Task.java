import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table (name = "TASK")
@Data
public class Task {

    @Id
    @GeneratedValue
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
}
