package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.*;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE `pp-1.1.4`.`users` (\n" +
                "  `id` BIGINT(45) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NOT NULL,\n" +
                "  `age` TINYINT(45) NOT NULL,\n" +
                "  PRIMARY KEY (`id`));";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            System.out.println("The table has been created");
        } catch (SQLSyntaxErrorException ignore) { }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE users";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            System.out.println("The table has been deleted");
        } catch (SQLSyntaxErrorException ignore) { }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand = "INSERT INTO users (name, lastName, age) VALUES ('"
                + name + "', '" + lastName + "', '" + age + "')";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlCommand = "DELETE FROM users WHERE id=" + id;
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            System.out.println("User ID" + id + " has been deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sqlCommand = "SELECT * FROM users";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCommand);
            List<User> resultList = new ArrayList<>();

            while (rs.next()) {
                String retrievedName = rs.getString("name");
                String retrievedLastName = rs.getString("lastname");
                Byte retrievedAge = rs.getByte("age");
                User user = new User(retrievedName, retrievedLastName, retrievedAge);
                resultList.add(user);
                System.out.println(user.toString());
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void cleanUsersTable() {
        String sqlCommand = "TRUNCATE TABLE users";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            System.out.println("The table has been cleaned");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
