package rzelonek.libsys.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rzelonek.libsys.model.Book;

public class BooksData {
    private static ObservableList<Book> dummyData = FXCollections.observableArrayList();

    // Initialize sample books
    static {
        initializeBooks();
    }

    // Initialize sample books
    public static void initializeBooks() {
        // Adding sample books
        dummyData.add(new Book(1, "To Kill a Mockingbird", "Harper Lee", false, null, null, ""));
        dummyData.add(new Book(2, "1984", "George Orwell", false, null, null, ""));
        dummyData.add(new Book(3, "The Great Gatsby", "F. Scott Fitzgerald", false, null, null, ""));
        dummyData.add(new Book(4, "Pride and Prejudice", "Jane Austen", false, null, null, ""));
        dummyData.add(new Book(5, "The Catcher in the Rye", "J.D. Salinger", false, null, null, ""));
        dummyData.add(new Book(6, "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", false, null, null, ""));
        dummyData.add(new Book(7, "The Lord of the Rings", "J.R.R. Tolkien", false, null, null, ""));
        dummyData.add(new Book(8, "Brave New World", "Aldous Huxley", false, null, null, ""));
        dummyData.add(new Book(9, "The Hunger Games", "Suzanne Collins", false, null, null, ""));
        dummyData.add(new Book(10, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", false, null, null, ""));
        dummyData.add(new Book(11, "To Kill a Mockingbird", "Harper Lee", false, null, null, ""));
        dummyData.add(new Book(12, "1984", "George Orwell", false, null, null, ""));
        dummyData.add(new Book(13, "The Great Gatsby", "F. Scott Fitzgerald", false, null, null, ""));
        dummyData.add(new Book(14, "Pride and Prejudice", "Jane Austen", false, null, null, ""));
        dummyData.add(new Book(15, "The Catcher in the Rye", "J.D. Salinger", false, null, null, ""));
        dummyData.add(new Book(16, "The Hobbit", "J.R.R. Tolkien", false, null, null, ""));
        dummyData.add(new Book(17, "Fahrenheit 451", "Ray Bradbury", false, null, null, ""));
        dummyData.add(new Book(18, "The Picture of Dorian Gray", "Oscar Wilde", false, null, null, ""));
        dummyData.add(new Book(19, "Frankenstein", "Mary Shelley", false, null, null, ""));
        dummyData.add(new Book(20, "Animal Farm", "George Orwell", false, null, null, ""));

    }

    public static ObservableList<Book> getAllBooks() {
        return dummyData;
    }

    // Add a new book
    public static void addBook(Book book) {
        dummyData.add(book);
    }

    // Remove a book
    public static void removeBook(Book book) {
        dummyData.remove(book);
    }

}