/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ghinDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author robertpepple
 */

public class GolfCourseDAO {
        private Connection connect = null;
        private Statement statement = null;
        private PreparedStatement preparedStatement = null;
        private ResultSet resultSet = null;

        public List<GolfCourse> getAllGolfCourses() throws SQLException {
                try {
                        List gcList = new ArrayList();

                        connect = DriverManager
                                    .getConnection("jdbc:mysql://localhost:3306/handicap_data?user=root&password=Dbleagle2");

                        statement = connect.createStatement();
                        resultSet = statement.executeQuery("select * from golf_courses");

                        while(resultSet.next()) {
                              int gcId = resultSet.getInt("id");
                              String gcName = resultSet.getString("name");
                              float gcRating = resultSet.getFloat("rating");
                              short gcSlope = resultSet.getShort("slope");
			      GolfCourse gc = new GolfCourse(gcId,gcName,gcRating,gcSlope);
			
                              gcList.add(gc);      
                        }
                        return gcList;
                } catch (SQLException e) {
                        throw e;
                } finally {
                        close();
                }
        }

        public GolfCourse getGolfCourseParams(String gcName) throws SQLException {
                try {                        
                        connect = DriverManager
                                    .getConnection("jdbc:mysql://localhost:3306/handicap_data?user=root&password=Dbleagle2");

                        statement = connect.createStatement();


                        preparedStatement = connect
                                        .prepareStatement("select * from golf_courses where name = ?");

                        preparedStatement.setString(1, gcName);
                        resultSet = preparedStatement.executeQuery();
                        
                        GolfCourse gc = new GolfCourse();
                        while(resultSet.next()) {
                              gc.setId(resultSet.getInt("id"));
                              gc.setName(resultSet.getString("name"));
                              gc.setRating(resultSet.getFloat("rating"));
                              gc.setSlope(resultSet.getShort("slope"));
                        }
                        return gc;                        

                 } catch (SQLException e) {
                        throw e;
                } finally {
                        close();
                }
        }             

        public boolean checkForGolfCourse(String gcName) throws SQLException {
                try {                        
                        connect = DriverManager
                                    .getConnection("jdbc:mysql://localhost:3306/handicap_data?user=root&password=Dbleagle2");

                        statement = connect.createStatement();


                        preparedStatement = connect
                                        .prepareStatement("select * from golf_courses where name = ?");

                        preparedStatement.setString(1, gcName);
                        resultSet = preparedStatement.executeQuery();

                        GolfCourse gc = new GolfCourse();
                        gc.setName("No Entry");
                        while(resultSet.next()) {
                              gc.setId(resultSet.getInt("id"));
                              gc.setName(resultSet.getString("name"));
                              gc.setRating(resultSet.getFloat("rating"));
                              gc.setSlope(resultSet.getShort("slope"));
                        }
                        if (gc.getName().matches("^No Entry")) {
                            return false;
                        } else {
                            return true;                        
                        }
                 } catch (SQLException e) {
                        throw e;
                } finally {
                        close();
                }
        }
                
        public boolean addNewGolfCourse(String gcName, float gcRating, short gcSlope) throws SQLException {
                try {                        
                    connect = DriverManager
                                    .getConnection("jdbc:mysql://localhost:3306/handicap_data?user=root&password=Dbleagle2");

                    statement = connect.createStatement();
                    
                    preparedStatement = connect
                                        .prepareStatement("insert into golf_courses values (default,?, ?, ?)");

                        preparedStatement.setString(1, gcName);
                        preparedStatement.setShort(2, gcSlope);
                        preparedStatement.setFloat(3, gcRating);
                        
                        preparedStatement.executeUpdate();
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
