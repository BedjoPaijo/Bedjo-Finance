/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bedjo.finance.model;

/**
 *
 * @author root
 */
public class DayFinanceData {

    String id;
    String desc;
    Long amount;
    String date;

    public DayFinanceData() {

    }

    public DayFinanceData(String id, String desc, String amount, String date) {
        this.id = id;
        this.desc = desc;
        this.amount = Long.parseLong(amount);
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public long getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
