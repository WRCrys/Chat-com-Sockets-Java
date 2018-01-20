package com.fatene.persistência;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author cryst
 */
public class conexaomysql {
   public static Connection conectabdmysql() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/chat_spd", "root", "camaro2014");
            JOptionPane.showMessageDialog(null, "Banco de Dados MySql Conectado com Sucesso!");
            return con;
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
            return null;
        }
    }
   public static void closeConnection(Connection conecta, Statement smt, ResultSet rs) throws Exception{
       closeConnection(conecta, smt, rs);
   }
   public static void closeConnection(Connection conecta, Statement smt) throws Exception{
       closeConnection(conecta, smt, null);
   }
   public static void closeConnection(Connection conecta) throws Exception{
       closeConnection(conecta, null, null);
   }
   public static void close(Connection conecta, Statement smt, ResultSet rs) throws Exception{
       try{
           if(rs!=null) rs.close();
       } catch(Exception e){
           throw new Exception (e.getMessage());
       }
   }
    
}
