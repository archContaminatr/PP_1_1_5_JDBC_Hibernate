package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao testUserDao;

    public UserServiceImpl() {
        this.testUserDao = new UserDaoJDBCImpl();
    }

    public void createUsersTable() {
        testUserDao.createUsersTable();
    }

    public void dropUsersTable() {
        testUserDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        testUserDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        testUserDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return testUserDao.getAllUsers();
    }

    public void cleanUsersTable() {
        testUserDao.cleanUsersTable();
    }

}
