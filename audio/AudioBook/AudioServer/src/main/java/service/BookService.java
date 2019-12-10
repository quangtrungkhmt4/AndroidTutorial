package service;

import model.impl.AudioBook;
import org.springframework.stereotype.Service;
import repository.mongodb.BookRepository;
import util.exception.ApplicationException;

import java.util.List;

@Service
public class BookService {

    public List<AudioBook> getBooks(int limit, int offset) throws ApplicationException {
        return BookRepository.getBooks(limit, offset);
    }
}
