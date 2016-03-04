/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bedjo.finance.controler;

import bedjo.finance.AppData;
import bedjo.finance.connection.DbConnect;
import bedjo.finance.model.DayFinanceData;
import bedjo.finance.view.ListPerDayDialog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class ListOfDayDialogControl {

    ListPerDayDialog frame;
    ArrayList<DayFinanceData> dayFinanceDatas;
    DefaultTableModel model;
    Connection c;
    boolean isNew = true;

    String[] columns = {"Desc", "Amount"};

    public ListOfDayDialogControl(ListPerDayDialog frame) {
        this.frame = frame;
        c = DbConnect.getConnection();
    }

    public void createTable() {
        loadFinance();
        model = new DefaultTableModel(columns, 0);
        Object[] data = new Object[2];
        for (DayFinanceData dayFinanceData : dayFinanceDatas) {
            data[0] = dayFinanceData.getDesc();
            data[1] = dayFinanceData.getAmount();
            model.addRow(data);
        }
        frame.getTable().setModel(model);
    }

    public void loadFinance() {
        dayFinanceDatas = new ArrayList<>();
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(AppData.query.selectFinaceByDate(frame.date));

            String id, desc, amount, date;
            System.out.println("Loading Data...");
            System.out.println("Query : " + AppData.query.selectFinaceByDate(frame.date));
            while (rs.next()) {
                id = rs.getString(AppData.db.FINANCE_CODE);
                desc = rs.getString(AppData.db.FINANCE_DESC);
                amount = rs.getString(AppData.db.FINANCE_AMOUNT);
                date = rs.getString(AppData.db.FINANCE_DATE);

                DayFinanceData data = new DayFinanceData(id, desc, amount, date);
                dayFinanceDatas.add(data);
            }

            rs.close();
            s.close();

            countTotal();
        } catch (SQLException ex) {
            Logger.getLogger(ListOfDayDialogControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        try {
            Statement s = c.createStatement();
            if (isNew == true) {
                s.executeUpdate(AppData.query.insertFinanceByDate(frame.getTxtDesc().getText(), frame.getTxtAmount().getText(), frame.date));
            } else {
                s.executeUpdate(AppData.query.updateFinanceByDate(dayFinanceDatas.get(frame.getTable().getSelectedRow()).getId(), frame.getTxtDesc().getText(), frame.getTxtAmount().getText(), frame.date));
            }
            s.close();
            
            isNew = true;
            
            createTable();
            frame.getAddPanel().setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(ListOfDayDialogControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void countTotal() {
        long i = 0;

        for (DayFinanceData dayFinanceData : dayFinanceDatas) {
            i = i + dayFinanceData.getAmount();
        }

        frame.getTotal().setText(formatNumber(i));
    }

    public String formatNumber(long i) {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols format = new DecimalFormatSymbols();
        format.setCurrencySymbol("Rp. ");
        format.setDecimalSeparator('.');
        format.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(format);
        String value = df.format(i);
        return value;
    }
    
    public void loadFinanceData(){
        int row = frame.getTable().getSelectedRow();
        frame.getTxtAmount().setText(dayFinanceDatas.get(row).getAmount()+"");
        frame.getTxtDesc().setText(dayFinanceDatas.get(row).getDesc());
        frame.getAddPanel().setVisible(true);
        isNew = false;
    }
    
    public void delete(){
        int row = frame.getTable().getSelectedRow();
        String id = dayFinanceDatas.get(row).getId();
        
        int i = JOptionPane.showConfirmDialog(frame, "Are you sure ?","Qustion",JOptionPane.YES_NO_OPTION);
        
        if(i == 1){
            return;
        }
        
        try{
            Statement s = c.createStatement();
            s.executeUpdate(AppData.query.deleteFinanceByDate(id));
            s.close();
            createTable();
        } catch (SQLException ex) {
            Logger.getLogger(ListOfDayDialogControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
