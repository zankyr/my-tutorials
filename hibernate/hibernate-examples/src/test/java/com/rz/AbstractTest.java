
package com.rz;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractTest {

    protected static SessionFactory sessionFactory;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected Session session;

    @BeforeClass
    public static void beforeClass() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Before
    public void setUp() {
        beginSessionTransactionAndSaveToHolder();
    }

    private void beginSessionTransactionAndSaveToHolder() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        SessionHolder.set(session);
    }

    @After
    public void tearDown() {
        sessionRollbackAndClose();
    }

    private void sessionRollbackAndClose() {
        session.getTransaction().rollback();
        session.close();
        SessionHolder.set(null);
    }

    protected void showTableStructure(String tableName) {
        Session session = SessionHolder.get();

        List<Object[]> result = session
                .createNativeQuery("SHOW COLUMNS FROM " + tableName)
                .getResultList();

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("\n------------------------< SHOW TABLE %s >-------------------", tableName));
        result.forEach(obj -> {
            sb.append("\nfield: " + obj[0]);
            sb.append("\n\ttype: " + obj[1]);
            sb.append("\n\tnullable: " + obj[2]);
            sb.append("\n\tkey: " + obj[3]);
            sb.append("\n\tdefault: " + obj[4]);
        });

        sb.append("\n======================================\n");

        logger.info(sb.toString());
    }

    protected void showTableContent(String tableName) {
        Session session = SessionHolder.get();

        List<Object> results = session
                .createNativeQuery("SELECT * FROM " + tableName)
                .list();

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("\n------------------------< SELECT * FROM %s >-------------------", tableName));
        int count = 0;

        results.forEach(result -> {
            sb.append(String.format("\nRow #%s:", results.indexOf(result)));
            sb.append("\t");
            sb.append(Arrays.toString((Object[]) result));
        });

        sb.append("\n======================================\n");

        logger.info(sb.toString());
    }
}
