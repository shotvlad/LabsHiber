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

        //Update
        //Разделить группу, численностью более 25 человек на 2 отдельные группы
        List<Group> groups = session.createQuery("FROM Group").list();
        int stud = 0;

        for (Group group : groups) {
            System.out.println(group.getStudents().size());
            if (group.getStudents().size() > 25)
            {
                Group group1 = new Group(group.getName() + "(1)", group.getCreateDate(), group.getPlanCode(), group.getStatus(), group.getSpecialty(), new Date());
                Group group2 = new Group(group.getName() + "(2)", group.getCreateDate(), group.getPlanCode(), group.getStatus(), group.getSpecialty(), new Date());
                session.save(group1);
                session.save(group2);

                for (Student student : group.getStudents()) {
                    if (stud < 15) {
                        student.setGroup(group1);
                        stud++;
                    }
                    else student.setGroup(group2);
                    session.update(student);
                }
                //session.delete(group);
            }
        }

        session.flush();
        transaction.commit();
        session.close();
        sf.close();
    }
}
