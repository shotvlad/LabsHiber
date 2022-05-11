package hiberlab.runner;

import hiberlab.entity.Group;
import hiberlab.entity.Student;
import hiberlab.utils.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class Lab2 {

    public static void main(String[] args) {
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        //Update
        //Установить статус «расформирована» для групп, сформированных более 4 лет назад,
        //у студентов данных групп установить статус «выпускник»
        //Примечание: не забудьте обновить также поля StatusDate.
        List<Group> groups = session.createQuery("FROM Group").list();
        Date curDate = new Date();
        for(Group group : groups)
        {
            if (curDate.getYear() - group.getCreateDate().getYear() > 4) {
                group.setStatus("Расформирована");
                group.setStatusDate(curDate);
                for (Student student : group.getStudents())
                {
                    student.setStatus("Выпускник");
                    student.setStatusDate(curDate);
                    session.update(student);
                }
                session.update(group);
            } else {
                group.setStatus("Сформирована");
                group.setStatusDate(curDate);
                for (Student student : group.getStudents())
                {
                    student.setStatus("Зачислен");
                    student.setStatusDate(curDate);
                    session.update(student);
                }
                session.update(group);
            }
        }

        session.flush();
        transaction.commit();
        session.close();
        sf.close();
    }
}
