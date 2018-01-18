package zadDBlib;

import java.util.List;

import zadDBlib.model.Reader;
import zadDBlib.model.Book;
import zadDBlib.libraryToCommunicate.Library;

public class JdbcTest {

    public static void main(String[] args) {
        Library b = new Library();
        b.insertReader("Karol", "Maciaszek", "92873847182");
        b.insertReader("Piotr", "Wojtecki", "89273849128");
        b.insertReader("Abdul", "Dabdul", "");

        b.insertBook("Cień Wiatru", "Carlos Ruiz Zafon");
        b.insertBook("W pustyni i w puszczy", "Henryk Sienkiewicz");
        b.insertBook("Harry Potter", "Joanne Kathleen Rowling.");

        List<Reader> readers = b.selectReaders();
        List<Book> books = b.selectBooks();

        System.out.println("Lista czytelników: ");
        for(Reader c: readers)
            System.out.println(c);

        System.out.println("Lista książek:");
        for(Book k: books)
            System.out.println(k);

        b.closeConnection();
    }
}