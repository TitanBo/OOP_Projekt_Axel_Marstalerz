import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBank
{
    Vehicle[] fzg = new Vehicle[50];

    public Vehicle getFzg(int index)
    {
        return fzg[index];
    }
    public void setFzg(int index, Vehicle eingabe)
    {
        fzg[index] = eingabe;
    }

    Connection conn;
    int row, col;

    private void dbRun() throws SQLException {
        String url = "C:\\Users\\paxel\\Desktop\\Java\\Projekte\\OOP_Projekt_Axel_Marstalerz";
        conn = DriverManager.getConnection(url);
    }
    private void checkTabel()  // or create first time
    {
        System.out.println("checkTab");
        String sql = "CREATE TABLE IF NOT EXIST tbl_User (" +
                "   id integer PRIMARY KEY AUTOINCREMENT," +
                "   user text NOT NULL," +
                "   userPw text NOT NULL," +
                "   typs text NOT NULL" +
                ");";
        try
        {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
        catch (Exception error)
        {
            System.out.println("Error");
        }

    }
}
