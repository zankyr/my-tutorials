package com.rz;

import com.rz.one_to_many.Stock;
import com.rz.one_to_many.StockDailyRecord;
import com.rz.one_to_many.StockMonthlyRecord;
import com.rz.one_to_one.foreign_key.bidirectional.User2;
import com.rz.one_to_one.foreign_key.bidirectional.UserDetail2;
import com.rz.one_to_one.foreign_key.unidirectional.User;
import com.rz.one_to_one.foreign_key.unidirectional.UserDetail;
import com.rz.one_to_one.join_table.Account;
import com.rz.one_to_one.join_table.Employee;
import com.rz.one_to_one.shared_key.Student;
import com.rz.one_to_one.shared_key.StudentAccount;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        HibernateUtil.sessionFactory = sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            initSessionFactory();

        return sessionFactory;

    }

    private static void initSessionFactory() {
        try {
            // Create registry
            standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();

            // Create MetadataSources
            MetadataSources metadataSources = new MetadataSources(standardServiceRegistry)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(UserDetail.class)
                    .addAnnotatedClass(User2.class)
                    .addAnnotatedClass(UserDetail2.class)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Account.class)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(StudentAccount.class)
                    .addAnnotatedClass(Stock.class)
                    .addAnnotatedClass(StockDailyRecord.class)
                    .addAnnotatedClass(StockMonthlyRecord.class);


            // Create metadata
            Metadata metadata = metadataSources.getMetadataBuilder().build();

            // Create SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Exception ex) {
            ex.printStackTrace();
            destroyRegistry();
        }
    }

    public static void shutdown() {
        destroyRegistry();
    }

    private static void destroyRegistry() {
        if (standardServiceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
        }
    }
}
