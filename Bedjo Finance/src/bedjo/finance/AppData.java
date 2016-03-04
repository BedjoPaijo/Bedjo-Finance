/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bedjo.finance;

/**
 *
 * @author root
 */
public class AppData {

    public static class db {
        
        public static String FINANCE_CODE = "id";
        public static String FINANCE_DESC = "desc";
        public static String FINANCE_AMOUNT = "amount";
        public static String FINANCE_DATE = "date";
    
    }
    
    public static class query {

        public static String selectFinaceByDate(String date) {
            String query = "select * from finance where date = '"+date+"'";
            return query;
        }

        public static String insertFinanceByDate(String desc, String amount, String date) {
            String query = "insert into finance values(null,'" + desc + "','" + amount + "','" + date + "')";
            return query;
        }

        public static String updateFinanceByDate(String code, String desc, String amount, String date) {
            String query = "update finance "
                    + "set "
                    + "desc ='" + desc + "',"
                    + "amount ='" + amount + "',"
                    + "date ='" + date + "'"
                    + "where id ='" + code + "'";
            return query;
        }

        public static String deleteFinanceByDate(String code) {
            String query = "delete from finance where id ='" + code + "'";
            return query;
        }

    }

}
