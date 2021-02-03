/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Metier.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mohamed
 */
public class DbConnect {
     private static Connection conn;
  private Statement st;
  private ResultSet res;
  public DbConnect() {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/chifetnbr","root","");
            st= conn.createStatement();
        }
        catch(Exception ex)
        {
            System.out.println("erreur de connexion BD "+ex.getMessage());
        }
        }
   
    public static Connection getConnection()
    {
        return conn;
    }
}
