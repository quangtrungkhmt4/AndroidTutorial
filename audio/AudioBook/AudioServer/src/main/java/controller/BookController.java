package controller;

import constant.ErrorCode;
import model.impl.AudioBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.BookService;
import util.exception.ApplicationException;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books")
    public void getBooks(@RequestParam int offset){
        try {
            List<AudioBook> books = bookService.getBooks(6, offset);
        } catch (ApplicationException e) {
        }
    }
}
