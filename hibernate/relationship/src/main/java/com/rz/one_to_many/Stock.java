package com.rz.one_to_many;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "stock", uniqueConstraints = {
        @UniqueConstraint(columnNames = "STOCK_NAME"),
        @UniqueConstraint(columnNames = "STOCK_CODE")})
public class Stock {

    private Integer stockId;
    private String stockCode;
    private String stockName;
    private Set<StockDailyRecord> stockDailyRecordSet = new HashSet<>(0);

    private Set<StockMonthlyRecord> stockMonthlyRecords = new HashSet<>(0);
    private List<StockMonthlyRecord> stockMonthlyRecordList = new ArrayList<>();

    public Stock() {
    }

    public Stock(String stockCode, String stockName) {
        this.stockCode = stockCode;
        this.stockName = stockName;
    }

    public Stock(String stockCode, String stockName,
                 Set<StockDailyRecord> stockDailyRecords) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.stockDailyRecordSet = stockDailyRecords;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "STOCK_ID", unique = true, nullable = false)
    public Integer getStockId() {
        return this.stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    @Column(name = "STOCK_CODE", unique = true, nullable = false)
    public String getStockCode() {
        return this.stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    @Column(name = "STOCK_NAME", unique = true, nullable = false)
    public String getStockName() {
        return this.stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
    public Set<StockDailyRecord> getStockDailyRecordSet() {
        return this.stockDailyRecordSet;
    }

    public void setStockDailyRecordSet(Set<StockDailyRecord> stockDailyRecords) {
        stockDailyRecords.forEach(stockDailyRecord -> stockDailyRecord.setStock(this));

        this.stockDailyRecordSet = stockDailyRecords;
    }

    @OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "STOCK_ID")
    @JoinTable(name = "stock_month",
            joinColumns = {@JoinColumn(name = "STOCK_ID", referencedColumnName = "STOCK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MONTH_RECORD_ID", referencedColumnName = "RECORD_ID")})
    public Set<StockMonthlyRecord> getStockMonthlyRecords() {
        return stockMonthlyRecords;
    }

    public void setStockMonthlyRecords(Set<StockMonthlyRecord> stockMonthlyRecords) {
        this.stockMonthlyRecords = stockMonthlyRecords;
    }

    @OneToMany(mappedBy = "stock",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "STOCK_ID")
    public List<StockMonthlyRecord> getStockMonthlyRecordList() {
        return stockMonthlyRecordList;
    }

    public void setStockMonthlyRecordList(List<StockMonthlyRecord> stockMonthlyRecordList) {
        this.stockMonthlyRecordList = stockMonthlyRecordList;
    }

    public void addStockMonthlyRecord(StockMonthlyRecord record){
        this.stockMonthlyRecordList.add(record);
        record.setStock(this);
    }

}
