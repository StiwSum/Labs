package rpis71.badykov.wdad.learn.xmltask;

import java.time.LocalDate;
import java.util.List;

public class Reader {
   private List<Book> books;
   private String name;
   private LocalDate takeDate;

    public Reader(List<Book> books, String name, LocalDate takeDate) {
        this.books = books;
        this.name = name;
        this.takeDate = takeDate;
    }
   
   
 }
