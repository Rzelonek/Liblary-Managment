package rzelonek.libsys.controlers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.DoubleBinaryOperator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import rzelonek.libsys.App;
import rzelonek.libsys.auth.Auth;
import rzelonek.libsys.db.BooksData;
import rzelonek.libsys.db.UserData;
import rzelonek.libsys.model.Book;
import rzelonek.libsys.model.User;

public class UserProfileController {

      @FXML
      private TableView<Book> bookTable;

      @FXML
      private TableColumn<Book, String> rentedBookId;

      @FXML
      private TableColumn<Book, String> rentedBookName;

      @FXML
      private TableColumn<Book, String> date;

      @FXML
      private TableColumn<Book, String> enddate;

      @FXML
      private TableColumn<Book, String> fine;

      @FXML
      private ObservableList<Book> bookList;

      @FXML
      private TableColumn<User, Integer> bookCount;

      @FXML
      private TableColumn<User, Integer> totalFine;

      @FXML
      private Label bookcounter;

      @FXML
      private Label totalfineamount;

      @FXML
      private void initialize() {

            final KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
            bookTable.setOnKeyPressed(event -> {
                  if (keyCodeCopy.match(event)) {
                        copySelectionToClipboard(bookTable);
                  }
            });

            ObservableList<Book> dummyData2 = BooksData.getAllBooks();
            bookList = FXCollections.observableArrayList();

            Auth auth = new Auth();

            rentedBookId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
            rentedBookName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            date.setCellValueFactory(cellData -> cellData.getValue().dateOfRentProperty().asString());
            enddate.setCellValueFactory(cellData -> cellData.getValue().EnddateOfRentProperty().asString());
            fine.setCellValueFactory(cellData -> {
                  Book book = cellData.getValue();
                  double fineAmount = calculateFine(book);
                  return new SimpleStringProperty(String.valueOf(fineAmount));
            });

            bookList.addAll(dummyData2);
            displayBooksRentedByUser(auth.loggedUser.getLogin());
            bookcounter.setText(gettotalbookamount().toString());
            totalfineamount.setText(totalFineAmount222().getValue());

      }

      @FXML
      private SimpleStringProperty totalFineAmount222() {
            SimpleStringProperty totalFineAmount2;
            totalFineAmount2 = new SimpleStringProperty(String.valueOf(calculateTotalFine()));
            System.err.println("total fine  2" + totalFineAmount2);
            return totalFineAmount2;

      }

      @FXML
      private double calculateTotalFine() {
            double totalFineAmount = 0;

            for (Book book : bookList) {
                  if (book.getUsername().equals(Auth.loggedUser.getLogin())) {
                        totalFineAmount += calculateFine(book);
                  }
            }

            System.err.println("total fine " + totalFineAmount);
            return totalFineAmount;

      }

      @FXML
      private Integer gettotalbookamount() {
            int rentedBooksCount = getRentedBooksCount(Auth.loggedUser.getLogin());
            return rentedBooksCount;
      }

      @FXML
      private void goback() throws IOException {
            App.setRoot("secondary");
      }

      private void displayBooksRentedByUser(String username) {

            // Filter the list of books based on the selected user's username
            ObservableList<Book> filteredList = FXCollections.observableArrayList();
            for (Book book : bookList) {
                  if (book.getUsername().equals(username)) {
                        filteredList.add(book);
                  }
            }
            bookTable.setItems(filteredList);
      }

      private int getRentedBooksCount(String username) {
            int count = 0;

            for (Book book : bookList) {
                  if (book.getUsername().equals(username)) {
                        count++;
                  }
            }
            return count;
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
      public void copySelectionToClipboard(final TableView<?> table) {
            final Set<Integer> rows = new TreeSet<>();
            for (final TablePosition tablePosition : table.getSelectionModel().getSelectedCells()) {
                  rows.add(tablePosition.getRow());
            }
            final StringBuilder strb = new StringBuilder();
            boolean firstRow = true;
            for (final Integer row : rows) {
                  if (!firstRow) {
                        strb.append('\n');
                  }
                  firstRow = false;
                  boolean firstCol = true;
                  for (final TableColumn<?, ?> column : table.getColumns()) {
                        if (!firstCol) {
                              strb.append('\t');
                        }
                        firstCol = false;
                        final Object cellData = column.getCellData(row);
                        strb.append(cellData == null ? "" : cellData.toString());
                  }
            }
            final ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(strb.toString());
            Clipboard.getSystemClipboard().setContent(clipboardContent);
      }
}
