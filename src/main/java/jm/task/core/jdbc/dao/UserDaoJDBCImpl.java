package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.*;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    //Добавил транзакции только в INSERT/DELETE-методы
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlCommand = """
                CREATE TABLE IF NOT EXISTS `pp-1.1.4`.`users` (
                  `id` BIGINT(45) NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastname` VARCHAR(45) NOT NULL,
                  `age` TINYINT(45) NOT NULL,
                  PRIMARY KEY (`id`));""";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(sqlCommand);
            System.out.println("The table has been created");
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE users";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("The table has been deleted");
        } catch (SQLSyntaxErrorException ignore) { }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                connection.commit();
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlCommand = "DELETE FROM users WHERE id=" + id;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sqlCommand);
                connection.commit();
                System.out.println("User ID" + id + " has been deleted");
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sqlCommand = "SELECT * FROM users";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sqlCommand);
            List<User> resultList = new ArrayList<>();

            while (rs.next()) {
                String retrievedName = rs.getString("name");
                String retrievedLastName = rs.getString("lastname");
                Byte retrievedAge = rs.getByte("age");
                User user = new User(retrievedName, retrievedLastName, retrievedAge);
                resultList.add(user);
                System.out.println(user);
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void cleanUsersTable() {
        String sqlCommand = "TRUNCATE TABLE users";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("The table has been cleaned");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
