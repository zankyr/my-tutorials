package com.rz;

import com.rz.one_to_one.foreign_key.bidirectional.User2;
import com.rz.one_to_one.foreign_key.bidirectional.UserDetail2;
import com.rz.one_to_one.foreign_key.unidirectional.User;
import com.rz.one_to_one.foreign_key.unidirectional.UserDetail;
import com.rz.one_to_one.join_table.Account;
import com.rz.one_to_one.join_table.Employee;
import com.rz.one_to_one.shared_key.Student;
import com.rz.one_to_one.shared_key.StudentAccount;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class OneToOneApplication {
    public static void main(String[] args) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            showDatabase(session);
            unidirectionalExample(session);
            bidirectionalExample(session);
            joinTableExample(session);
            sharedKeyExample(session);


            transaction.commit();

        } catch (Exception ex) {
            ex.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
        }

        HibernateUtil.shutdown();
    }

    private static void showDatabase(Session session) {
        nativeQuery(session, "SHOW TABLES");

        // Unidirectional example
        nativeQuery(session, "SHOW COLUMNS FROM USER");
        nativeQuery(session, "SHOW COLUMNS FROM USER_DETAIL");

        // Join table example
        nativeQuery(session, "SHOW COLUMNS FROM T_EMPLOYEE");
        nativeQuery(session, "SHOW COLUMNS FROM T_ACCOUNT");
        nativeQuery(session, "SHOW COLUMNS FROM EMPLOYEE_ACCOUNT");

        // Shared key example
        nativeQuery(session, "SHOW COLUMNS FROM T_STUDENT");
        nativeQuery(session, "SHOW COLUMNS FROM T_STUDENT_ACCOUNT");

        // Bidirectional example
        nativeQuery(session, "SHOW COLUMNS FROM USER_2");
        nativeQuery(session, "SHOW COLUMNS FROM USER_DETAIL_2");


    }

    private static void unidirectionalExample(Session session) {
        User user = new User();
        user.setUsername("USR001");
        user.setPassword("abcd@xyz");

        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName("Michael");
        userDetail.setLastName("Smith");
        userDetail.setEmail("michael.smith@example.com");
        userDetail.setDob(LocalDate.of(1988, Month.FEBRUARY, 25));

        user.setUserDetail(userDetail);

        session.saveOrUpdate(user);

        nativeQuery(session, "SELECT * FROM USER");
        nativeQuery(session, "SELECT * FROM USER_DETAIL");
    }

    private static void bidirectionalExample(Session session) {
        User2 user = new User2();
        user.setUsername("USR002");
        user.setPassword("mno@xyz.com");

        UserDetail2 userDetail = new UserDetail2();
        userDetail.setFirstName("Sammer");
        userDetail.setLastName("Dua");
        userDetail.setEmail("sammer.dua@example.com");
        userDetail.setDob(LocalDate.of(1985, Month.APRIL, 1));
        userDetail.setUser2(user);

        user.setUserDetail2(userDetail);

        session.saveOrUpdate(user);

        nativeQuery(session, "SELECT * FROM USER_2");
        nativeQuery(session, "SELECT * FROM USER_DETAIL_2");
    }

    private static void joinTableExample(Session session) {
        Employee employee = new Employee();
        employee.setEmail("demo-user@mail.com");
        employee.setFirstName("demo");
        employee.setLastName("user");

        Account account = new Account();
        account.setAccountNumber("123-345-65454");

        employee.setAccount(account);

        session.saveOrUpdate(employee);

        nativeQuery(session, "SELECT * FROM T_EMPLOYEE");
        nativeQuery(session, "SELECT * FROM T_ACCOUNT");
        nativeQuery(session, "SELECT * FROM EMPLOYEE_ACCOUNT");
    }

    private static void sharedKeyExample(Session session) {

        Student student1 = new Student();
        student1.setEmail("demo1-user@mail.com");
        student1.setFirstName("demo1");
        student1.setLastName("student1");

        session.saveOrUpdate(student1);

        Student student = new Student();
        student.setEmail("demo-user@mail.com");
        student.setFirstName("demo");
        student.setLastName("student");

        session.saveOrUpdate(student);

        nativeQuery(session, "SELECT * FROM T_STUDENT");
        nativeQuery(session, "SELECT * FROM T_STUDENT_ACCOUNT");

        StudentAccount studentAccount = new StudentAccount();
        studentAccount.setAccountNumber("1234567890");

        student.setStudentAccount(studentAccount);
        session.saveOrUpdate(student);

        session.flush();

        nativeQuery(session, "SELECT * FROM T_STUDENT");
        nativeQuery(session, "SELECT * FROM T_STUDENT_ACCOUNT");
    }


    public static void nativeQuery(Session session, String query) {
        List<Object> list = session.createNativeQuery(query).list();
        for (Object o : list) {
            System.out.println(Arrays.toString((Object[]) o));
        }
    }
}
