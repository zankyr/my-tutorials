package com.rz;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        Student student1 = new Student("student1", "student1lastname", "student1@rz.com");
        Student student2 = new Student("student2", "student2lastname", "student2@rz.com");

        // This student violates the unique constraint on the email field
        Student student3 = new Student("student3", "student3lastname", "student1@rz.com");

        // This student violates the not-null constraint on the email field
        Student student4 = new Student();

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // save the objects
            session.save(student1);
            session.save(student2);

            // remove to see the unique constraint error
           // session.save(student3);

            // remove to see the null constraint error
            // session.save(student4);

            // commit
            transaction.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Student> students = session.createQuery("FROM s", Student.class).list();
            students.forEach(System.out::println);

        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }
}
