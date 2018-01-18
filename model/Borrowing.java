package zadDBlib.model;

public class Borrowing {
    private int idBook;
    private int idReader;

    public int getIdBook() {
        return idBook;
    }
    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }
    public int getIdReader() {
        return idReader;
    }
    public void setIdReader(int idReader) {
        this.idReader = idReader;
    }

    public Borrowing() {}
    public Borrowing(int idBook, int idReader) {
        this.idBook = idBook;
        this.idReader = idReader;

    }
}