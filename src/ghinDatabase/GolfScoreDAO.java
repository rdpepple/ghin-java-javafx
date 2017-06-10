/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ghinDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robertpepple
 */
public class GolfScoreDAO {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public List<GolfScore> getAllGolfScores() throws SQLException {
            try {
                    List scrList = new ArrayList();

                    connect = DriverManager
                                .getConnection("jdbc:mysql://localhost:3306/handicap_data?user=root&password=Dbleagle2");

                    statement = connect.createStatement();
                    resultSet = statement.executeQuery("select * from score_history order by id desc limit 20");

                    while(resultSet.next()) {
                          int scrId = resultSet.getInt("id");
                          String scrDatePlayed = resultSet.getString("date_played");
                          String scrGcName = resultSet.getString("course");
                          float scrRating = resultSet.getFloat("rating");
                          short scrSlope = resultSet.getShort("slope");
                          short score = resultSet.getShort("score");
                          GolfScore scr = new GolfScore(scrId, scrGcName, scrRating, scrSlope, scrDatePlayed, score);
                          scrList.add(scr);      
                    }
                    return scrList;
            } catch (SQLException e) {
                    throw e;
            } finally {
                    close();
            }
    }

    public boolean addNewGolfScore(String gcName, float gcRating, short gcSlope, String date_played, short score) throws SQLException {
            try {                        
                connect = DriverManager
                                .getConnection("jdbc:mysql://localhost:3306/handicap_data?user=root&password=Dbleagle2");

                statement = connect.createStatement();

                preparedStatement = connect
                                    .prepareStatement("insert into score_history values (default, ?, ?, ?, ?, ?)");
                
                preparedStatement.setString(1, date_played);
                preparedStatement.setString(2, gcName);
                preparedStatement.setShort(3, gcSlope);
                preparedStatement.setFloat(4, gcRating);
                preparedStatement.setShort(5, score);

                int insertResult;
                insertResult = preparedStatement.executeUpdate();
                return true;
             } catch (SQLException e) {
                    throw e;
            } finally {
                    close();
            }
    }             

        // You need to close the resultSet
        private void close() throws SQLException {
                try {
                        if (resultSet != null) {
                                resultSet.close();
                        }

                        if (statement != null) {
                                statement.close();
                        }

                        if (connect != null) {
                                connect.close();
                        }
                } catch (SQLException e) {
                       throw e;
                }
        }
    
}
