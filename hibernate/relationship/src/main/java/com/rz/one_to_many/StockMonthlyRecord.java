package com.rz.one_to_many;

import javax.persistence.*;
import java.time.Month;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "stock_monthly_record")
public class StockMonthlyRecord {

    private Integer recordId;
    private Month month;
    private Stock stock;

    public StockMonthlyRecord() {
    }

    public StockMonthlyRecord(Month month) {
        this.month = month;
    }

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "RECORD_ID", unique = true, nullable = false)
    public Integer getRecordId() {
        return this.recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Column(name = "MONTH", unique = true)
    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockMonthlyRecord)) return false;
        return recordId != null && recordId.equals(((StockMonthlyRecord) o).getRecordId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
