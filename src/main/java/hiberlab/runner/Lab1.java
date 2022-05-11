package hiberlab.runner;

import hiberlab.entity.Group;
import hiberlab.entity.Specialty;
import hiberlab.entity.Student;
import hiberlab.utils.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class Lab1 {

    public static void main(String[] args) {
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        //Для БД: SET GLOBAL time_zone = '+3:00';

        //Вариант 1
        //Выборка
        //1. Вывести таблицу: ФИО студента, название группы.
        List<Student> students = session.createQuery("FROM Student").list();
        System.out.println("Задание 1. ФИО и группа студента");
        for(Student student : students)
            System.out.println(student);

        //2. Вывести сведения о количестве студентов, обучающихся по каждой специальности.
        List<Specialty> specialties = session.createQuery("FROM Specialty").list();
        int studs = 0;
        System.out.println("Задание 2. Количество студентов, обучающихся на каждой специальности");
        for(Specialty specialty : specialties)
        {
            for(Group group : specialty.getGroups())
                studs += group.getStudents().stream().filter(student -> student.getStatus().equals("Зачислен")).toList().size();
            System.out.println(specialty.getName() + ": " + studs + " человек.");
            studs = 0;
        }

        session.flush();
        transaction.commit();
        session.close();
        sf.close();
    }
}
