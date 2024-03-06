package rzelonek.libsys.controlers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.TreeSet;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import rzelonek.libsys.App;
import rzelonek.libsys.db.BooksData;
import rzelonek.libsys.db.UserData;
import rzelonek.libsys.model.Book;
import rzelonek.libsys.model.User;
import rzelonek.libsys.auth.Auth;

public class UserListController {

      @FXML
      private TableView<User> userTableView;

      @FXML
      private TextField searchField;

      @FXML
      private TableColumn<User, String> id;

      @FXML
      private TableColumn<User, String> name;

      @FXML
      private TableColumn<User, String> password;

      @FXML
      private TableColumn<User, String> role;

      @FXML
      private TextField NewPassword;

      @FXML
      private TextField ConfrimNewPassword;

      @FXML
      private TextField NewUserGroup;

      @FXML
      private TextField NewUserName;

      @FXML
      private TextField NewUserPassword;

      @FXML
      private ComboBox<String> roleComboBox;

      @FXML
      private TextField LookForidField;

      @FXML
      private TextField LookFornameField;

      @FXML
      private TextField LookForRoleField;

      private ObservableList<User> userList;

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
      private TableColumn<User, Integer> bookCounts;

      @FXML
      private TableColumn<User, Integer> bookCount;

      @FXML
      private TableColumn<User, Integer> totalFine;

      @FXML
      private void initialize() {
            id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
            name.setCellValueFactory(cellData -> cellData.getValue().loginProperty());
            password.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
            role.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

            userList = FXCollections.observableArrayList();
            ObservableList<User> dummyData = UserData.getAllUsers();
            userList.addAll(dummyData);
            userTableView.setItems(userList);

            final KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
            userTableView.setOnKeyPressed(event -> {
                  if (keyCodeCopy.match(event)) {
                        copySelectionToClipboard(userTableView);
                  }
            });

            searchField.textProperty().addListener((observable, oldValue, newValue) -> searchUsers(newValue));

            LookForidField.textProperty().addListener((observable, oldValue, newValue) -> searchUsers(newValue));
            LookFornameField.textProperty().addListener((observable, oldValue, newValue) -> searchUsers(newValue));
            LookForRoleField.textProperty().addListener((observable, oldValue, newValue) -> searchUsers(newValue));

            roleComboBox.setItems(FXCollections.observableArrayList("ADMIN", "USER"));

            ObservableList<Book> dummyData2 = BooksData.getAllBooks();
            bookList = FXCollections.observableArrayList();

            // Add listener to handle user selection
            userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                  if (newSelection != null) {

                        // Load and display books rented by the selected user
                        displayBooksRentedByUser(newSelection.getLogin());
                  }
            });

            rentedBookId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
            rentedBookName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            date.setCellValueFactory(cellData -> cellData.getValue().dateOfRentProperty().asString());
            enddate.setCellValueFactory(cellData -> cellData.getValue().EnddateOfRentProperty().asString());
            fine.setCellValueFactory(cellData -> {
                  Book book = cellData.getValue();
                  double fineAmount = calculateFine(book);
                  return new SimpleStringProperty(String.valueOf(fineAmount));
            });

            // Initialize bookCount column
            bookCounts.setCellValueFactory(cellData -> {
                  User user = cellData.getValue();
                  int rentedBooksCount = getRentedBooksCount(user.getLogin());
                  return new SimpleIntegerProperty(rentedBooksCount).asObject();
            });
            bookList.addAll(dummyData2);

            totalFine.setCellValueFactory(cellData -> {
                  User user = cellData.getValue();
                  double totalFineAmount = calculateTotalFine(user.getLogin());
                  return new SimpleIntegerProperty((int) totalFineAmount).asObject();
            });
      }

      @FXML
      private double calculateTotalFine(String username) {
            double totalFineAmount = 0;

            for (Book book : bookList) {
                  if (book.getUsername().equals(username)) {
                        totalFineAmount += calculateFine(book);
                  }
            }

            return totalFineAmount;
      }

      @FXML
      private void goback() throws IOException {
            App.setRoot("secondary");
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

      @FXML
      private void searchUsers(String query) {
            if (query == null || query.isEmpty()) {
                  userTableView.setItems(userList);
                  return;
            }

            ObservableList<User> filteredList = FXCollections.observableArrayList();
            for (User user : userList) {
                  if (userMatchesQuery(user, query)) {
                        filteredList.add(user);
                  }
            }
            userTableView.setItems(filteredList);
      }

      @FXML
      private boolean userMatchesQuery(User user, String query) {
            String lowerCaseQuery = query.toLowerCase();
            return String.valueOf(user.idProperty().toString()).toLowerCase().contains(lowerCaseQuery) ||
                        user.getLogin().toLowerCase().contains(lowerCaseQuery) ||
                        user.getRole().toLowerCase().contains(lowerCaseQuery);
      }

      @FXML
      private void changePassword() {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                  String newPassword = NewPassword.getText();
                  String confirmPassword = ConfrimNewPassword.getText();
                  if (newPassword.equals(confirmPassword)) {
                        selectedUser.setPassword(Auth.ChangetoHashed(newPassword));
                        NewPassword.clear();
                        ConfrimNewPassword.clear();
                  } else {
                        // Handle password mismatch
                  }
            } else {
                  // Handle no user selected
            }
      }

      @FXML
      private void changeUserGroup() {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                  String newUserGroup = NewUserGroup.getText();
                  selectedUser.setRole(newUserGroup);
                  NewUserGroup.clear();
            } else {
                  // Handle no user selected
            }
      }

      @FXML
      private void removeUser() {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                  userList.remove(selectedUser);
            } else {
                  // Handle no user selected
            }
      }

      @FXML
      private void addUser() {
            String newUserName = NewUserName.getText();
            String newUserPassword = NewUserPassword.getText();
            String newUserGroup = roleComboBox.getValue();
            User newUser = new User(0, newUserName, Auth.ChangetoHashed(newUserPassword), newUserGroup);
            userList.add(newUser);
            NewUserName.clear();
            NewUserPassword.clear();
            NewUserGroup.clear();
      }

      @FXML
      private void searchBooks() {
            String idText = LookForidField.getText().toLowerCase();
            String nameText = LookFornameField.getText().toLowerCase();
            String roleText = LookForRoleField.getText().toLowerCase();

            ObservableList<User> filteredList = FXCollections.observableArrayList();

            for (User user : userList) {
                  boolean matches = true;

                  if (!idText.isEmpty() && !user.idProperty().toString().contains(idText)) {
                        matches = false;
                  }
                  if (!nameText.isEmpty() && !user.getLogin().toLowerCase().contains(nameText)) {
                        matches = false;
                  }
                  if (!roleText.isEmpty() &&
                              !user.getRole().toLowerCase().contains(roleText)) {
                        matches = false;
                  }

                  if (matches) {
                        filteredList.add(user);
                  }
            }

            userTableView.setItems(filteredList);
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

}
