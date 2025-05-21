package br.spring.cloud.examplemicroservice.service;

import br.spring.cloud.examplemicroservice.entity.Book;
import br.spring.cloud.examplemicroservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() { return bookRepository.findAll(); }
    public Optional<Book> getBookById(Long id) { return bookRepository.findById(id); }
    public Book saveBook(Book book) { return bookRepository.save(book); }
    public void deleteBook(Long id) { bookRepository.deleteById(id); }
    public Book updateBook(Long id, Book details) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(details.getTitle());
        book.setAuthor(details.getAuthor());
        book.setIsbn(details.getIsbn());
        return bookRepository.save(book);
    }
}
