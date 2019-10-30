package rpis71.badykov.wdad.learn.xmltask;

public class Book {

    private String autor;
    private String name;
    private int printyear;
    private GENRE genre;

    public Book(String autor, String name, int printyear, GENRE genre) {
        this.autor = autor;
        this.name = name;
        this.printyear = printyear;
        this.genre = genre;
    }

    Book() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
