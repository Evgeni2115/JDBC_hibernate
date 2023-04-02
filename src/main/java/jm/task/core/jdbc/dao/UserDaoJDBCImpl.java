package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl  implements UserDao {
    private Connection connection;

    private static int id = 1;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (id INT, name VARCHAR(64), last_name VARCHAR(64), age INT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (id, name, last_name, age) VALUES (?, ?, ?, ?)");
            statement.setInt(1, id++);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setByte(4, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement  statement = connection.prepareStatement("SELECT * FROM user");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try {
            PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE user");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
