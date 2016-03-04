/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bedjo.finance.controler;

import bedjo.finance.view.ListPerDayDialog;
import bedjo.finance.view.MainFrame;
import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class MainFrameControl {

    MainFrame frame;
    DefaultTableModel model;
    String[] days = {"Ahad", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu"};
    String[] months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

    int realmonth = Calendar.getInstance().get(Calendar.MONTH);
    int realyear = Calendar.getInstance().get(Calendar.YEAR);
    int realday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    int year;
    int month;

    public MainFrameControl(MainFrame frame) {
        this.frame = frame;
        model = new DefaultTableModel(days, 6) {
            @Override
            public boolean isCellEditable(int row, int index) {
                return false;
            }
        };
        year = realyear;
        month = realmonth;
    }

    public void showFrame() {
        frame.setVisible(true);
    }

    public void createTable() {

        model = new DefaultTableModel(days, 6) {
            @Override
            public boolean isCellEditable(int row, int index) {
                return false;
            }
        };
        frame.getCTable().setModel(model);
        frame.getCTable().setDefaultRenderer(frame.getCTable().getColumnClass(0), new TableRenderer());
    }

    public void refreshCalendar() {
        createTable();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);

        int start = calendar.get(calendar.DAY_OF_WEEK);
        int maxDate = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
        start--;
        int j = 0;
        for (int i = 1; i <= maxDate; i++) {
            model.setValueAt(i, j, start);
            start++;
            if (start == 7) {
                start = 0;
                j++;
            }
        }

        frame.getTxtMonth().setText(months[month]);

    }

    public void setComboBoxData() {
        int year = 2000;
        frame.getCombo().removeAllItems();
        for (int i = year - 10; i < year + 100; i++) {
            frame.getCombo().addItem(i);
            if (i == this.year) {
                frame.getCombo().setSelectedItem(i);
            }
        }
    }

    public void nextMonth() {
        if (month == 11) {
            month = 0;
            frame.getCombo().setSelectedIndex(frame.getCombo().getSelectedIndex() + 1);
            year = (int) frame.getCombo().getSelectedItem();
        } else {
            month++;
        }

        refreshCalendar();
    }

    public void prevMonth() {
        if (month == 0) {
            month = 11;
            frame.getCombo().setSelectedIndex(frame.getCombo().getSelectedIndex() - 1);
            year = (int) frame.getCombo().getSelectedItem();
        } else {
            month--;
        }

        refreshCalendar();
    }

    public void changeYear() {
        if (frame.getCombo().getSelectedItem() != null) {
            year = (int) frame.getCombo().getSelectedItem();
        } else {
            setComboBoxData();
        }
        refreshCalendar();
    }

    class TableRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);

            if (column == 0 || column == 6) { //Week-end
                setBackground(new Color(255, 255, 255));
                setForeground(Color.RED);
            } else if (column == 5) {
                setBackground(new Color(255, 255, 255));
                setForeground(new Color(51, 204, 51));
            } else { //Week
                setBackground(new Color(255, 255, 255));
                setForeground(Color.black);
            }
            if (value != null) {
                if (Integer.parseInt(value.toString()) == realday && month == realmonth && year == realyear) { //Today
                    setBackground(new Color(220, 220, 255));
                    setForeground(Color.black);
                }
            }

            if (selected) {
                setForeground(new Color(255, 102, 0));
                setBackground(new Color(234, 226, 241));
            }

            setBorder(null);
            return this;
        }
    }

    public void showListOfDay() {
        ListPerDayDialog dialog = new ListPerDayDialog(frame, selectedDate());
        dialog.setVisible(true);
    }
    
    String selectedDate(){
        String date = year+"-"+month+"-"+model.getValueAt(frame.getCTable().getSelectedRow(), frame.getCTable().getSelectedColumn());
        return date;
    }
}
