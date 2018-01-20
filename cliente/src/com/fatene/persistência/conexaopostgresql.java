package com.fatene.persistência;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author cryst
 */
public class conexaopostgresql {
   public static Connection conectabdpostegresql() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chat_spd", "postgres", "camaro2014");
            JOptionPane.showMessageDialog(null, "Banco de Dados PostgreSQL Conectado com Sucesso!");
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
