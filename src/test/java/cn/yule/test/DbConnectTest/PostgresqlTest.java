package cn.yule.test.DbConnectTest;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.sql.*;
import java.util.List;

/**
 * Created by john on 2016/6/1.
 */
public class PostgresqlTest {
    @Test
    public void connectPostgresql() throws ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection("jdbc:postgresql://127.0.1.1:5432/web", "sa", "postgres123");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
//        testBatchInsert(connection);
        try {
            PreparedStatement ps = connection.prepareStatement("select count(*) from r_ball");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer count = rs.getInt(1);
                System.out.println(count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testInteger(){
        System.out.println(Math.pow(2,32)/10000);
    }
}
