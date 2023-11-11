package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        UserServiceImpl USI = new UserServiceImpl();
        ArrayList<User> UsersList = new ArrayList<>();
        User user1 = new User("Ivan", "Ivanov", (byte) 19);
        User user2 = new User("Petr", "Petrov", (byte) 20);
        User user3 = new User("Semen", "Semenov", (byte) 22);
        User user4 = new User("Sidor", "Sidorov", (byte) 25);
        UsersList.add(user1);
        UsersList.add(user2);
        UsersList.add(user3);
        UsersList.add(user4);
        USI.createUsersTable();

        for (User u : UsersList) {
            USI.saveUser(u.getName(), u.getLastName(), u.getAge());
            System.out.println("Пользователь с именем : " + u.getName() + " добавлен в таблицу");
        }
        USI.getAllUsers();
        USI.cleanUsersTable();
        USI.dropUsersTable();
        try {
            Util.getConnection().close();
            Util.getStatement().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
