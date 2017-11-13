import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Connection conn;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "inf127239");
        connectionProps.put("password", "KUBAzdan000");
        try {
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    connectionProps);
            System.out.println("Połączono z bazą danych");
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(
                        "SELECT RPAD(nazwisko, 10), nazwa " +
                                "from pracownicy natural join zespoly");
                int rowcount = 0;
                int i = 0;
                while (rs.next()) {
                    System.out.println(
                            rs.getString(1) + " w zespole " +
                                    rs.getString(2));
                    i++;
                }
                System.out.println("zatrudniono " + i + " pracownikow");
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
