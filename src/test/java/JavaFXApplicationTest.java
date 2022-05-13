import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;

    /*
     *  Чтобы работало должным образом и не сохраняло
     *  ненужные задачи - запускать все тесты одновременно (запускать сам класс)
     */

public class JavaFXApplicationTest {

    /*
     *  Тестируется добавление задачи в базу данных
     */
    @Test
    public void addNewTaskTest() {
        Task task = addTask("Buy some food", "2 carrots, 5 packs of milk");

        String taskName = task.getTaskName();

        SessionFactory sf =  SessionFactoryConfiguration.getSessionFactory();
        Session session = sf.openSession();

        Task savedTask = session.createQuery("FROM Task a where a.taskName = :taskName", Task.class).setParameter("taskName", taskName).getSingleResult();
        System.out.println(savedTask);

        assertNotNull(savedTask);
        assertEquals(task.getTaskName(), savedTask.getTaskName());
        assertEquals(task.getTaskText(), savedTask.getTaskText());

        session.close();
    }

    /*
     *  Тестируется удаление задачи из базы данных
     */
    @Test
    public void deleteTaskTest() throws Exception {
        SessionFactory sessionFactory = SessionFactoryConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();

        String taskName = "Buy some food";

        Task savedTask = session.createQuery("FROM Task a where a.taskName = :taskName", Task.class).setParameter("taskName", taskName).getSingleResult();

        if (taskName.equals(savedTask.getTaskName())) {
            session.beginTransaction();
            session.delete(savedTask);
            session.getTransaction().commit();

            Task deletedTask = new Task();
            try {
                deletedTask = session.createQuery("FROM Task a where a.taskName = :taskName", Task.class).setParameter("taskName", taskName).getSingleResult();
            } catch (NoResultException nre) {}

            assertNull(deletedTask.getTaskName());
            assertNull(deletedTask.getTaskText());
            assertNull(deletedTask.getDate());
            assertNull(deletedTask.getTime());

            session.close();
        } else throw new Exception("Tasks are not similar. Test is not passed!");
    }

    /*
     *  Тестируется обновление теста задачи в базе данных
     */
    @Test
    public void updateTaskTest() {
        Task task = addTask("Погулять с подругами", "Встретиться со своими любимыми подругами");

        task.setTaskText("Я передумала, гулять не пойду");

        SessionFactory sessionFactory = SessionFactoryConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();

        Task updatedTask = session.createQuery("FROM Task a where a.taskName = :taskName", Task.class).setParameter("taskName", task.getTaskName()).getSingleResult();

        assertNotNull(updatedTask);
        assertEquals(task.getTaskName(), updatedTask.getTaskName());
        assertEquals(task.getTaskText(), updatedTask.getTaskText());

        session.beginTransaction();
        session.delete(task);
        session.getTransaction().commit();
        session.close();
    }

    private Task addTask(String taskName, String taskText) {
        SessionFactory sf =  SessionFactoryConfiguration.getSessionFactory();
        Session session = sf.openSession();

        Task task = new Task(999,
                taskName,
                taskText,
                LocalDate.now(),
                Time.valueOf(LocalTime.of(Integer.parseInt("15"), Integer.parseInt("00"), 0)));

        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();

        return task;
    }
}
