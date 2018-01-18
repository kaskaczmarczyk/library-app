package zadDBlib.libraryToCommunicate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import zadDBlib.model.Reader;
import zadDBlib.model.Book;

public class Library {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:library.db";

    private Connection conn;
    private Statement stat;

    public Library() {
        try {
            Class.forName(Library.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }

        createTables();
    }

    public boolean createTables()  {
        String createReaders = "CREATE TABLE IF NOT EXISTS readers (id_reader INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(255), surname varchar(255), pesel int)";
        String createBooks = "CREATE TABLE IF NOT EXISTS books (id_book INTEGER PRIMARY KEY AUTOINCREMENT, title varchar(255), author varchar(255))";
        String createBorrowing = "CREATE TABLE IF NOT EXISTS borrowings (id_borrow INTEGER PRIMARY KEY AUTOINCREMENT, id_reader int, id_book int)";
        try {
            stat.execute(createReaders);
            stat.execute(createBooks);
            stat.execute(createBorrowing);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertReader(String name, String surname, String pesel) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into readers values (NULL, ?, ?, ?);");
            prepStmt.setString(1, name);
            prepStmt.setString(2, surname);
            prepStmt.setString(3, pesel);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu czytelnika");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertBook(String title, String author) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into books values (NULL, ?, ?);");
            prepStmt.setString(1, title);
            prepStmt.setString(2, author);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wypozyczaniu");
            return false;
        }
        return true;
    }

    public boolean insertBorrow(int idReader, int idBook) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into borrowings values (NULL, ?, ?);");
            prepStmt.setInt(1, idReader);
            prepStmt.setInt(2, idBook);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wypozyczaniu");
            return false;
        }
        return true;
    }

    public List<Reader> selectReaders() {
        List<Reader> readers = new LinkedList<Reader>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM readers");
            int id;
            String name, surname, pesel;
            while(result.next()) {
                id = result.getInt("id_reader");
                name = result.getString("name");
                surname = result.getString("surname");
                pesel = result.getString("pesel");
                readers.add(new Reader(id, name, surname, pesel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return readers;
    }

    public List<Book> selectBooks() {
        List<Book> books = new LinkedList<Book>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM books");
            int id;
            String title, author;
            while(result.next()) {
                id = result.getInt("id_book");
                title = result.getString("title");
                author = result.getString("author");
                books.add(new Book(id, title, author));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return books;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
}
