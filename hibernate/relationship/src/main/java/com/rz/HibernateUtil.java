package com.rz;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry
                standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();

                // Create MetadataSources
                MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);

                // Create metadata
                Metadata metadata = metadataSources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception ex) {
                ex.printStackTrace();
                destroyRegistry();
            }
        }

        return sessionFactory;

    }

    public static void shutdown(){
       destroyRegistry();
    }

    private static void destroyRegistry(){
        if (standardServiceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
        }
    }
}
