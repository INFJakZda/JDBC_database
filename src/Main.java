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
                int [] indeksy = {300, 310, 320};
                String [] nazwiska={"Woźniak", "Dąbrowski", "Kozłowski"};
                int [] place={1300, 1700, 1500};
                String []etaty={"ASYSTENT", "PROFESOR", "ADIUNKT"};
                //*************************
                pstmt = conn.prepareStatement("select nazwisko from pracownicy");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString("NAZWISKO"));
                }
                System.out.println("**************************");
                //*************************
                pstmt = conn.prepareStatement("INSERT INTO pracownicy " +
                        "(id_prac, nazwisko, placa_pod, etat) VALUES(?, ?, ?, ?)");

                for(int i = 0; i < 3; i++) {
                    pstmt.setInt(1, indeksy[i]);
                    pstmt.setString(2, nazwiska[i]);
                    pstmt.setInt(3, place[i]);
                    pstmt.setString(4, etaty[i]);
                    pstmt.executeQuery();
                }
                //*************************
                pstmt = conn.prepareStatement("select nazwisko from pracownicy");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString("NAZWISKO"));
                }
                //*************************
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