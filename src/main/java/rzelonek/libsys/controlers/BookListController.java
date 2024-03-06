package rzelonek.libsys.controlers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import rzelonek.libsys.App;
import rzelonek.libsys.db.BooksData;
import rzelonek.libsys.model.Book;

public class BookListController {

      @FXML
      private TableView<Book> bookTable;

      @FXML
      private TableColumn<Book, String> id;

      @FXML
      private TableColumn<Book, String> name;

      @FXML
      private TableColumn<Book, String> author;

      @FXML
      private TableColumn<Book, String> rented;

      @FXML
      private TableColumn<Book, String> date;

      @FXML
      private TableColumn<Book, String> enddate;

      @FXML
      private TableColumn<Book, String> username;

      private ObservableList<Book> bookList;

      @FXML
      private TextField idField;

      @FXML
      private TextField nameField;

      @FXML
      private TextField authorField;

      @FXML
      private TextField usernameField;

      @FXML
      private DatePicker endDatePicker;

      @FXML
      private TableColumn<Book, String> fine;

      @FXML
      private TextField LookForidField;
      @FXML
      private TextField LookFornameField;
      @FXML
      private TextField LookForauthorField;
      @FXML
      private TextField LookForusernameField;
      @FXML
      private TextField searchField;

      @FXML
      private void initialize() {
            // Set up the table columns
            id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
            name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            author.setCellValueFactory(cellData -> cellData.getValue().authorProperty());

            rented.setCellValueFactory(cellData -> {
                  boolean rented = cellData.getValue().getRentStatus();
                  return new SimpleStringProperty(rented ? "Rented" : "Available");
            });

            date.setCellValueFactory(cellData -> {
                  LocalDate date = cellData.getValue().getDateOfRent();
                  return new SimpleStringProperty(date != null ? date.toString() : "X");
            });

            enddate.setCellValueFactory(cellData -> {
                  LocalDate endDate = cellData.getValue().getEnddateOfRent();
                  return new SimpleStringProperty(endDate != null ? endDate.toString() : "X");
            });
            username.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());

            fine.setCellValueFactory(cellData -> {
                  Book book = cellData.getValue();
                  double fineAmount = calculateFine(book);
                  return new SimpleStringProperty(String.valueOf(fineAmount));
            });

            // Create the book list
            bookList = FXCollections.observableArrayList();

            // Add dummy data to the table
            ObservableList<Book> dummyData = BooksData.getAllBooks();

            bookList.addAll(dummyData);
            bookTable.setItems(bookList);

            LookForidField.textProperty().addListener((observable, oldValue, newValue) -> searchBooks());
            LookFornameField.textProperty().addListener((observable, oldValue, newValue) -> searchBooks());
            LookForauthorField.textProperty().addListener((observable, oldValue,
                        newValue) -> searchBooks());
            LookForusernameField.textProperty().addListener((observable, oldValue,
                        newValue) -> searchBooks());

            searchField.textProperty().addListener((observable, oldValue, newValue) -> searchBooks(newValue));
      }

      @FXML
      private void addBook() {
            int bookId = Integer.parseInt(idField.getText());
            String bookName = nameField.getText();
            String bookAuthor = authorField.getText();

            Book newBook = new Book(bookId, bookName, bookAuthor, false, null, null, "");
            BooksData.addBook(newBook);
            bookList.add(newBook);

            // Clear input fields after adding the book
            idField.clear();
            nameField.clear();
            authorField.clear();
      }

      @FXML
      private void removeBook() {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                  BooksData.removeBook(selectedBook);
                  bookList.remove(selectedBook);
            }
      }

      @FXML
      private void refresh() throws IOException {
            // TODO: Implement refresh logic

      }

      @FXML
      private void goback() throws IOException {
            App.setRoot("secondary");

            // Clear existing items in the table
            bookList.clear();
      }

      @FXML
      private void rentBook() {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null && !selectedBook.getRentStatus()) {
                  String username = usernameField.getText();
                  if (!username.isEmpty()) {
                        selectedBook.setRentStatus(true);
                        selectedBook.setDateOfRent(LocalDate.now());
                        // Set end date based on your logic
                        selectedBook.setEnddateOfRent(LocalDate.now().plusDays(7));
                        selectedBook.setUsername(username);
                        bookTable.refresh(); // Update the table
                  } else {
                        // Handle empty username
                  }
            } else {
                  // Handle already rented or no book selected
            }
      }

      @FXML
      private void returnBook() {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null && selectedBook.getRentStatus()) {
                  selectedBook.setRentStatus(false);
                  selectedBook.setDateOfRent(null);
                  selectedBook.setEnddateOfRent(null);
                  selectedBook.setUsername("");
                  bookTable.refresh(); // Update the table
            } else {
                  // Handle no book selected or already returned
            }
      }
      // Inner class representing a book

      @FXML
      private void changeEndDate() {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                  LocalDate selectedDate = endDatePicker.getValue();
                  selectedBook.setEnddateOfRent(selectedDate);
                  bookTable.refresh(); // Update the table
            } else {
                  // Handle no book selected
            }
      }

      @FXML
      private double calculateFine(Book book) {
            LocalDate endDate = book.getEnddateOfRent();
            if (endDate != null) {
                  LocalDate currentDate = LocalDate.now();
                  long daysDifference = ChronoUnit.DAYS.between(endDate, currentDate);
                  if (daysDifference > 0) {
                        return daysDifference * 2; // $2 fine per day
                  }
            }
            return 0;
      }

      @FXML
      private void searchBooks() {
            String idText = LookForidField.getText().toLowerCase();
            String nameText = LookFornameField.getText().toLowerCase();
            String authorText = LookForauthorField.getText().toLowerCase();
            String usernameText = LookForusernameField.getText().toLowerCase();

            ObservableList<Book> filteredList = FXCollections.observableArrayList();

            for (Book book : bookList) {
                  boolean matches = true;

                  if (!idText.isEmpty() && !book.idProperty().toString().contains(idText)) {
                        matches = false;
                  }
                  if (!nameText.isEmpty() && !book.getName().toLowerCase().contains(nameText)) {
                        matches = false;
                  }
                  if (!authorText.isEmpty() &&
                              !book.getAuthor().toLowerCase().contains(authorText)) {
                        matches = false;
                  }
                  if (!usernameText.isEmpty() &&
                              !book.getUsername().toLowerCase().contains(usernameText)) {
                        matches = false;
                  }

                  if (matches) {
                        filteredList.add(book);
                  }
            }

            bookTable.setItems(filteredList);
      }

      @FXML
      private void searchBooks(String query) {
            if (query == null || query.isEmpty()) {
                  // If the search query is empty, show all books
                  bookTable.setItems(bookList);
                  return;
            }

            // Filter the book list based on the search query
            ObservableList<Book> filteredList = FXCollections.observableArrayList();
            for (Book book : bookList) {
                  if (bookMatchesQuery(book, query)) {
                        filteredList.add(book);
                  }
            }

            // Update the table with the filtered list
            bookTable.setItems(filteredList);
      }

      @FXML
      private boolean bookMatchesQuery(Book book, String query) {
            // Check if any field of the book contains the search query
            String lowerCaseQuery = query.toLowerCase();
            return String.valueOf(book.idProperty().toString()).toLowerCase().contains(lowerCaseQuery) ||
                        book.getName().toLowerCase().contains(lowerCaseQuery) ||
                        book.getAuthor().toLowerCase().contains(lowerCaseQuery) ||
                        book.getUsername().toLowerCase().contains(lowerCaseQuery);
      }

}
