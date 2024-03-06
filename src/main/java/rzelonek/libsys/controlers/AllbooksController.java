package rzelonek.libsys.controlers;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import rzelonek.libsys.App;
import rzelonek.libsys.db.BooksData;
import rzelonek.libsys.model.Book;

public class AllbooksController {

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
      private TextField LookForidField;

      @FXML
      private TextField LookFornameField;

      @FXML
      private TextField LookForauthorField;

      @FXML
      private TextField searchField;

      private ObservableList<Book> bookList;

      @FXML
      private CheckBox availabilityFilter;

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

            searchField.textProperty().addListener((observable, oldValue, newValue) -> searchBooks(newValue));
            availabilityFilter.selectedProperty().addListener((observable, oldValue, newValue) -> searchBooks());
      }

      @FXML
      private void searchBooks() {
            String idText = LookForidField.getText().toLowerCase();
            String nameText = LookFornameField.getText().toLowerCase();
            String authorText = LookForauthorField.getText().toLowerCase();
            boolean showAvailableOnly = availabilityFilter.isSelected(); // Check if availability filter is selected

            ObservableList<Book> filteredList = FXCollections.observableArrayList();

            for (Book book : bookList) {
                  boolean matches = true;

                  if (!idText.isEmpty() && !book.idProperty().toString().contains(idText)) {
                        matches = false;
                  }
                  if (!nameText.isEmpty() && !book.getName().toLowerCase().contains(nameText)) {
                        matches = false;
                  }
                  if (!authorText.isEmpty() && !book.getAuthor().toLowerCase().contains(authorText)) {
                        matches = false;
                  }
                  if (showAvailableOnly && book.getRentStatus()) { // Check availability only if filter is selected
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
            ObservableList<Book> filteredList = FXCollections.observableArrayList();

            if (query == null || query.isEmpty()) {
                  // If the search query is empty, show all books
                  filteredList.addAll(bookList);
            } else {
                  for (Book book : bookList) {
                        if (bookMatchesQuery(book, query)) {
                              filteredList.add(book);
                        }
                  }
            }

            // Apply availability filter
            boolean showAvailableOnly = availabilityFilter.isSelected();
            if (showAvailableOnly) {
                  filteredList.removeIf(Book::getRentStatus); // Remove rented books
            }

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

      @FXML
      private void goback() throws IOException {
            App.setRoot("secondary");

            // Clear existing items in the table
            bookList.clear();
      }
}
