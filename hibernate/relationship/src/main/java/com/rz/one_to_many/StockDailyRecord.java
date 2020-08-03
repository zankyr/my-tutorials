package com.rz.one_to_many;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "stock_daily_record")
public class StockDailyRecord {

    private Integer recordId;
    private Stock stock;
    private Date date;

    public StockDailyRecord() {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_ID")
    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE", nullable = false, length = 10)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
