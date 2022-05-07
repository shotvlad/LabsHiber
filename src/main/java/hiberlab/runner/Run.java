package hiberlab.runner;

import hiberlab.entity.Group;
import hiberlab.entity.Specialty;
import hiberlab.entity.Student;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import hiberlab.utils.NewHibernateUtil;

public class Run {

    public static void main(String[] args) {
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        //Для БД: SET GLOBAL time_zone = '+3:00';

//        //Вариант 1
//        //Выборка
//        //1. Вывести таблицу: ФИО студента, название группы.
//        List<Student> students = session.createQuery("FROM Student").list();
//        for(Student student : students)
//            System.out.println(student);
//
//        //2.	Вывести сведения о количестве студентов, обучающихся по каждой специальности.
//        List<Specialty> specialties = session.createQuery("FROM Specialty").list();
//        int studs = 0;
//        for(Specialty specialty : specialties)
//        {
//            for(Group group : specialty.getGroups())
//                studs += group.getStudents().stream().filter(student -> student.getStatus().equals("Зачислен")).toList().size();
//            System.out.println(specialty.getName() + ": " + studs + " человек.");
//            studs = 0;
//        }
//        //Update
//        //Установить статус «расформирована» для групп, сформированных более 4 лет назад,
//        //у студентов данных групп установить статус «выпускник»
//        //Примечание: не забудьте обновить также поля StatusDate.
//        List<Group> groups = session.createQuery("FROM Group").list();
//        Date curDate = new Date();
//        for(Group group : groups)
//        {
//            if (curDate.getYear() - group.getCreateDate().getYear() > 4) {
//                group.setStatus("Расформирована");
//                group.setStatusDate(curDate);
//                for (Student student : group.getStudents())
//                {
//                    student.setStatus("Выпускник");
//                    student.setStatusDate(curDate);
//                    session.update(student);
//                }
//                session.update(group);
//            } else {
//                group.setStatus("Сформирована");
//                group.setStatusDate(curDate);
//                for (Student student : group.getStudents())
//                {
//                    student.setStatus("Зачислен");
//                    student.setStatusDate(curDate);
//                    session.update(student);
//                }
//                session.update(group);
//            }
//        }


//        //Вариант 3
//        //Выборка
//        //1. Для каждой группы вывести количество отчисленных студентов.
//        List<Group> Group = session.createQuery("FROM Group").list();
//        int studs = 0;
//        for(Group group : Group)
//        {
//            studs += group.getStudents().stream().filter(student -> student.getStatus().equals("Отчислен")).toList().size();
//            System.out.println("В группе " + group.getName() + " отчисленно " + studs + " человек.");
//            studs = 0;
//        }
//
//        //2. Вывести сведения о студентах, обучающихся более 4 лет.
//        List<Student> students = session.createQuery("FROM Student").list();
//        Date curDate = new Date();
//        for(Student student : students)
//        {
//            if(curDate.getYear() - student.getGroup().getCreateDate().getYear() > 4)
//                System.out.println(student);
//        }
//

        session.flush();
        transaction.commit();
        session.close();
        sf.close();
    }

}
