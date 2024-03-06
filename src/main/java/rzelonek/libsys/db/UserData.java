package rzelonek.libsys.db;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.ArrayList;

import rzelonek.libsys.model.Book;
import rzelonek.libsys.model.User;

public class UserData {

      // Get user by login
      private static final Map<String, User> users = new HashMap<>();
      private static ObservableList<User> dummyDataUsers = FXCollections.observableArrayList();

      static {
            // Initialize sample users
            initializeUsers();
      }

      // Initialize sample users
      public static void initializeUsers() {
            // users.put("admin",
            // new User(1, "admin",
            // "d21674e85d9271a492986ebb09d9e6c0df2d0120ce418e6549b5f41ebb00a0c0",
            // "ADMIN"));
            // users.put("janusz", new User(2, "janusz", "5550b60e1d08b7c2e88d146c0721de00",
            // "USER"));

            dummyDataUsers.add(new User(1, "admin", "d21674e85d9271a492986ebb09d9e6c0df2d0120ce418e6549b5f41ebb00a0c0",
                        "ADMIN"));
            dummyDataUsers.add(new User(2, "admin2", "d21674e85d9271a492986ebb09d9e6c0df2d0120ce418e6549b5f41ebb00a0c0",
                        "ADMIN"));
            dummyDataUsers.add(new User(3, "Janusz", "d21674e85d9271a492986ebb09d9e6c0df2d0120ce418e6549b5f41ebb00a0c0",
                        "USER"));
            dummyDataUsers.add(new User(4, "Adam", "d21674e85d9271a492986ebb09d9e6c0df2d0120ce418e6549b5f41ebb00a0c0",
                        "USER"));

            for (User user : dummyDataUsers) {
                  users.put(user.getLogin(), user);
            }
      }

      public User getByLogin(String login) {
            return users.get(login);
      }

      // // Add a new user
      // public void addUser(User user) {
      // users.put(user.getLogin(), user);
      // }

      // Add a new user
      public static void addUser(User user) {
            dummyDataUsers.add(user);
            users.put(user.getLogin(), user);
      }

      // Get all users as an ObservableList
      // public static ObservableList<User> getAllUsers() {
      // return FXCollections.observableArrayList(users.values());
      // }

      public static ObservableList<User> getAllUsers() {
            return dummyDataUsers;
      }

}
