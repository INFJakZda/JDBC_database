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
            try {

                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                int changes = 0;
                int [] zwolnienia={150, 200, 230};
                String [] zatrudnienia={"Kandefer", "Rygiel", "Boczar"};

                for(int index : zwolnienia){
                    changes += stmt.executeUpdate(
                            "DELETE FROM pracownicy WHERE id_prac=" + index);
                }
                System.out.println("Usunieto " + changes + " pracowników");
                changes = 0;

                for(int i = 0; i < 3; i++){
                    changes += stmt.executeUpdate("INSERT INTO pracownicy(id_prac,nazwisko) "
                            + "VALUES(" + zwolnienia[i] + ", '" + zatrudnienia[i] + "')");
                }
                System.out.println("Wstawiono " + changes + " krotek.");


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