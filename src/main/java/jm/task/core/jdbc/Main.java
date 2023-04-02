package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Fedorov", (byte) 31);
        userService.saveUser("Eva", "Grin", (byte) 15);
        userService.saveUser("Евгений", "Решетников", (byte) 15);
        userService.saveUser("Семен", "Мурашов", (byte) 15);
        userService.removeUserById(1);
        Util.closeConnection();

    }
}
