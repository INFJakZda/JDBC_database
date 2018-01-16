import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        Connection conn;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "inf127239");
        connectionProps.put("password", "inf127239");
        try {
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    connectionProps);

            System.out.println("Połączono z bazą danych");
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement pstmt = null;

            CallableStatement stmt = conn.prepareCall("{? = call ZamienLitery(?, ?)}");
            try {

                stmt.setInt(2, 100);
                stmt.setString(3, "Hapke");
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.execute();
                int vCzyUdalo = stmt.getInt(1);
                if(vCzyUdalo == 1)
                    System.out.println("Udalo sie");
                else
                    System.out.println("Nie udalo sie");
                stmt.close();
                //stmt.close();

            } catch (SQLException ex) {
                System.out.println("Bład wykonania polecenia" + ex.toString());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) { /* kod obsługi */ }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) { /* kod obsługi */ }
                }
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) { /* kod obsługi */ }
                }
            }
            try {
                conn.close();
                System.out.println("Rozłączono z bazą danych");
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(
                    Main.class.getName()).log(Level.SEVERE,"nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
            
        }
    }
}