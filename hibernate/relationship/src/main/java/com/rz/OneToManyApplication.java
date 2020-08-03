package com.rz;

import com.rz.one_to_many.Stock;
import com.rz.one_to_many.StockDailyRecord;
import com.rz.one_to_many.StockMonthlyRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.Month;
import java.util.*;

public class OneToManyApplication {
    public static void main(String[] args) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            showDatabase(session);
            //foreignKeyExample(session);
            //joinTableExample(session);
            bp(session);

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
        // Foreign key
        nativeQuery(session, "SHOW COLUMNS FROM stock");
        nativeQuery(session, "SHOW COLUMNS FROM stock_daily_record");

        // join table
        nativeQuery(session, "SHOW COLUMNS FROM stock_monthly_record");
        nativeQuery(session, "SHOW COLUMNS FROM stock_month");
    }

    private static void foreignKeyExample(Session session) {
        Stock stock = new Stock("FK1", "foreign key 1");
        session.save(stock);
        session.flush();

        nativeQuery(session, "SELECT * FROM stock");

        Stock stock2 = new Stock("FK2", "foreign key 2");
        session.save(stock);
        session.flush();

        StockDailyRecord stockDailyRecord = new StockDailyRecord();
        stockDailyRecord.setDate(new Date());
        StockDailyRecord stockDailyRecord1 = new StockDailyRecord();
        stockDailyRecord1.setDate(new Date());

        Set<StockDailyRecord> stockDailyRecords = new HashSet<>();
        stockDailyRecords.add(stockDailyRecord);
        stockDailyRecords.add(stockDailyRecord1);

        stock2.setStockDailyRecordSet(stockDailyRecords);

        session.save(stock2);
        stockDailyRecords.forEach(session::save);
        session.flush();

        nativeQuery(session, "SELECT * FROM stock");
        nativeQuery(session, "SELECT * FROM stock_daily_record");

    }

    private static void joinTableExample(Session session) {
        Stock stock2 = new Stock("JT2", "join table 2");
        session.save(stock2);
        session.flush();

        Stock stock = new Stock("JT1", "join table 1");
        session.save(stock);
        session.flush();


        nativeQuery(session, "SELECT * FROM stock");
        nativeQuery(session, "SELECT * FROM stock_monthly_record");
        nativeQuery(session, "SELECT * FROM stock_month");

        StockMonthlyRecord stockMonthlyRecord = new StockMonthlyRecord();
        stockMonthlyRecord.setMonth(Month.FEBRUARY);
        stock.getStockMonthlyRecords().add(stockMonthlyRecord);
        session.save(stock);
        session.flush();

        nativeQuery(session, "SELECT * FROM stock");
        nativeQuery(session, "SELECT * FROM stock_monthly_record");
        nativeQuery(session, "SELECT * FROM stock_month");


    }

    private static void bp(Session session) {
        Stock stock = new Stock("JT_BP", "best pratices 1");

        // This shows that thera are no difference between Set and List
        //stock.getStockMonthlyRecords().add(new StockMonthlyRecord(Month.FEBRUARY));
        //stock.getStockMonthlyRecords().add(new StockMonthlyRecord(Month.APRIL));
        //stock.getStockMonthlyRecords().add(new StockMonthlyRecord(Month.JUNE));

        stock.getStockMonthlyRecordList().add(new StockMonthlyRecord(Month.JUNE));
        stock.getStockMonthlyRecordList().add(new StockMonthlyRecord(Month.FEBRUARY));
        stock.getStockMonthlyRecordList().add(new StockMonthlyRecord(Month.MARCH));

        session.save(stock);
        session.flush();
    }


    public static void nativeQuery(Session session, String query) {
        List<Object> list = session.createNativeQuery(query).list();
        for (Object o : list) {
            System.out.println(Arrays.toString((Object[]) o));
        }
    }
}
