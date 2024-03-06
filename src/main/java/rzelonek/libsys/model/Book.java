
package rzelonek.libsys.model;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Book {

      private SimpleIntegerProperty id;
      private StringProperty name;
      private StringProperty author;
      private ObjectProperty<LocalDate> dateOfRent;
      private ObjectProperty<LocalDate> EnddateOfRent;
      private SimpleBooleanProperty rentStatus;
      private StringProperty username;

      public Book(int id, String name, String author, boolean rented, LocalDate rentDate, LocalDate rentEndDate,
                  String username) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.author = new SimpleStringProperty(author);
            this.rentStatus = new SimpleBooleanProperty(rented);
            this.dateOfRent = new SimpleObjectProperty<LocalDate>(rentDate);
            this.EnddateOfRent = new SimpleObjectProperty<LocalDate>(rentEndDate);
            this.username = new SimpleStringProperty(username);

      }

      public StringProperty nameProperty() {
            return name;
      }

      public String getName() {
            return name.get();
      }

      public void setName(String name) {
            this.name.set(name);
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

      public StringProperty authorProperty() {
            return author;
      }

      public String getAuthor() {
            return author.get();
      }

      public void setAuthor(String author) {
            this.author.set(author);
      }

      public ObjectProperty<LocalDate> dateOfRentProperty() {
            return dateOfRent;
      }

      public LocalDate getDateOfRent() {
            return dateOfRent.get();
      }

      public void setDateOfRent(LocalDate dateOfRent) {
            this.dateOfRent.set(dateOfRent);
      }

      public ObjectProperty<LocalDate> EnddateOfRentProperty() {
            return EnddateOfRent;
      }

      public LocalDate getEnddateOfRent() {
            return EnddateOfRent.get();
      }

      public void setEnddateOfRent(LocalDate EnddateOfRent) {
            this.EnddateOfRent.set(EnddateOfRent);
      }

      public SimpleBooleanProperty rentStatusProperty() {
            return rentStatus;
      }

      public boolean getRentStatus() {
            return rentStatus.get();
      }

      public void setRentStatus(boolean rentStatus) {
            this.rentStatus.set(rentStatus);
      }

      public StringProperty usernameProperty() {
            return username;
      }

      public String getUsername() {
            return username.get();
      }

      public void setUsername(String username) {
            this.username.set(username);
      }

      public String toString() {
            return "Book [id=" + id + ", name=" + name + ", author=" + author + ", dateOfRent=" + dateOfRent
                        + ", EnddateOfRent=" + EnddateOfRent + ", rentStatus=" + rentStatus + ", username=" + username
                        + "]";
      }

}
