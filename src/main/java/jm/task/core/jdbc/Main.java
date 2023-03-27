package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Corvus", "Corax", (byte) 24);
        userService.saveUser("Roboute", "Gilliman", (byte) 42);
        userService.saveUser("Ferrus", "Manus", (byte) 32);
        userService.saveUser("Jagatai", "Khan", (byte) 7);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
