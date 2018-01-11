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
            Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement pstmt = null;
            try {
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement("INSERT INTO pracownicy"
                        + "(id_prac) VALUES(?)");

                long start = System.nanoTime();
                for(int i = 300; i < 2300; i++) {
                    pstmt.setInt(1, i);
                    pstmt.executeQuery();
                }
                long czas = System.nanoTime() - start;
                System.out.println("Zwykłe: " + czas);

                conn.rollback();

                long start2 = System.nanoTime();
                for(int i = 300; i < 2300; i++) {
                    pstmt.setInt(1, i);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                long czas2 = System.nanoTime() - start2;

                System.out.println("Wbudowane " + czas2);

                conn.rollback();

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