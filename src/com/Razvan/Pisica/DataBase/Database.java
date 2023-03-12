//package com.Razvan.Pisica.DataBase;
//
//import com.Razvan.Pisica.objects.Player;
//import com.Razvan.Pisica.window.Game;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//public class Database {
//    private Player player;
//    public Database(Player player){
//        this.player=player;
//    }
//    public void saveToDB() {
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:PAOO_game.db");
//            stmt = c.createStatement();
//            /*String sql = "CREATE TABLE Pisica_Curajoasa " +
//                    "( 'Score'       INT     NOT NULL," +
//                    "  'Dead'                 INT     NOT NULL," +
//                    " 'Win'        INT     NOT NULL)";
//            stmt.execute(sql);
//            sql = "INSERT INTO Pisica_Curajoasa ('Score', 'Dead', 'Win') " +
//                    "VALUES (0, 0, 0);";
//            stmt.execute(sql);*/
//            String sql = "UPDATE Pisica_Curajoasa set 'Score' =\"" + Player.Score + "\", " +
//                    "Dead = \"" + Player.dead + "\", " +
//                    "Win = \"" + Player.win + "\" WHERE rowid=1";
//            stmt.execute(sql);
//
//            stmt.close();
//            c.close();
//        } catch (Exception ex) {
//            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Baza de date a fost actualizata cu succes!");
//    }
//
//    public void loadFromDB() {
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:PAOO_game.db");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * from Pisica_Curajoasa");
//            Player.Score=rs.getInt("Score");
//            Player.dead=rs.getBoolean("Dead");
//            Player.win=rs.getBoolean("Win");
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch (Exception ex) {
//            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Loaded successfully from database");
//    }
//}
