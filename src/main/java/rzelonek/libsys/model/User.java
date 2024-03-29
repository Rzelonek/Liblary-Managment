package rzelonek.libsys.model;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class User {
    private SimpleIntegerProperty id;
    private StringProperty login;
    private StringProperty password;
    private StringProperty role;

    public User(int id, String login, String password, String role) {
        this.id = new SimpleIntegerProperty(id);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public int getid() {
        return id.get();
    }

    public void setid(int id) {
        this.id.set(id);
    }

    public StringProperty loginProperty() {
        return login;
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty roleProperty() {
        return role;
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String toString() {
        return "User [login=" + login + ", password=" + password + ", role=" + role + "]";
    }
}
