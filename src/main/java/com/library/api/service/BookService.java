package com.library.api.service;

import com.library.api.exception.notFound.BookNotFoundException;
import com.library.api.exception.business.NotUniqueISBNCode;
import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.dto.BookDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Сервис для управления книгами в библиотечной системе.
 * <p>
 * Предоставляет полный набор CRUD-операций для работы с книгами,
 * а также расширенные возможности:
 * <ul>
 *   <li>Поиск и фильтрация книг по названию, жанру, автору и доступности</li>
 *   <li>Получение списка авторов конкретной книги</li>
 *   <li>Пагинация результатов для эффективной работы с большими объёмами данных</li>
 * </ul>
 * <p>
 * Все операции валидируют входные данные согласно бизнес-правилам:
 * <ul>
 *   <li>ISBN должен быть уникальным (20 символов)</li>
 *   <li>Название — от 3 до 255 символов</li>
 *   <li>Описание — от 3 до 1000 символов</li>
 *   <li>Авторы должны существовать в системе</li>
 * </ul>
 *
 * @author MrDobryak88
 * @see BookDTO
 * @see AuthorDTO
 * @see com.library.api.repository.BookRepository
 */
public interface BookService {

    /**
     * Возвращает список авторов указанной книги.
     * <p>
     * Метод извлекает всех авторов, связанных с книгой через таблицу {@code book_author}.
     * Если у книги нет авторов, возвращается пустой список.
     *
     * @param bookId идентификатор книги (должен быть > 0)
     * @return список DTO авторов книги; пустой список, если авторов нет
     * @throws BookNotFoundException если книга с указанным ID не найдена
     */
    List<AuthorDTO> getAuthorsById(Long bookId);

    /**
     * Возвращает пагинированный список всех книг с загруженными авторами.
     * <p>
     *
     * @param pageable параметры пагинации и сортировки
     *                 (номер страницы, размер страницы, сортировка)
     * @return страница с книгами и их авторами
     */
    Page<BookDTO> getAll(Pageable pageable);

    /**
     * Возвращает полную информацию о книге по её идентификатору.
     * <p>
     * Включает все поля книги и список связанных авторов.
     *
     * @param id идентификатор книги (должен быть > 0)
     * @return DTO книги с полной информацией
     * @throws BookNotFoundException если книга с указанным ID не найдена
     */
    BookDTO getBookById(Long id);

    /**
     * Выполняет поиск книг с применением фильтров.
     * <p>
     * Поддерживает фильтрацию по нескольким критериям одновременно.
     * Параметры, переданные как {@code null}, игнорируются при поиске.
     * <p>
     * Критерии поиска:
     * <ul>
     *   <li><b>title</b> — частичное совпадение по названию (case-insensitive)</li>
     *   <li><b>genre</b> — точное совпадение жанра (case-insensitive)</li>
     *   <li><b>authorName</b> — частичное совпадение по имени или фамилии автора</li>
     *   <li><b>available</b> — фильтр по доступности ({@code true} — только книги с {@code availableCopies > 0})</li>
     * </ul>
     *
     * @param title      часть названия книги (необязательный)
     * @param genre      точное название жанра (необязательный)
     * @param authorName часть имени или фамилии автора (необязательный)
     * @param available  фильтр доступности: {@code true} — только доступные,
     *                   {@code false} — только недоступные,
     *                   {@code null} — без фильтра
     * @param pageable   параметры пагинации и сортировки
     * @return страница с книгами, соответствующими заданным фильтрам
     */
    Page<BookDTO> searchBooks(String title, String genre, String authorName, Boolean available, Pageable pageable);

    /**
     * Создаёт новую книгу в системе.
     * <p>
     * При создании выполняются следующие проверки:
     * <ul>
     *   <li>ISBN должен быть уникальным (ровно 20 символов)</li>
     *   <li>Все указанные авторы должны существовать в системе</li>
     *   <li>Поле {@code availableCopies} автоматически устанавливается равным {@code totalCopies}</li>
     *   <li>Поле {@code createdAt} устанавливается как текущее время</li>
     * </ul>
     *
     * @param bookDto DTO с данными новой книги (название, ISBN, описание, жанр, авторы и т.д.)
     * @return созданная книга с присвоенным идентификатором и полной информацией
     * @throws NotUniqueISBNCode           если книга с таким ISBN уже существует
     * @throws BookNotFoundException       если один или несколько указанных авторов не найдены
     * @throws DataIntegrityViolationException при нарушении ограничений базы данных
     */
    BookDTO create(BookDTO bookDto);

    /**
     * Удаляет книгу по её идентификатору.
     * <p>
     * <b>Внимание:</b> при удалении книги автоматически удаляются все связанные записи
     * о выдачах этой книги из таблицы {@code book_author}.
     * <p>
     * Удаление книги с активными выдачами может быть ограничено бизнес-логикой.
     *
     * @param id идентификатор удаляемой книги (должен быть > 0)
     * @return DTO удалённой книги (для подтверждения операции)
     * @throws BookNotFoundException если книга с указанным ID не найдена
     */
    BookDTO remove(Long id);

    /**
     * Обновляет данные существующей книги.
     * <p>
     * Обновляются все поля, переданные в DTO:
     * <ul>
     *   <li>Название, ISBN, описание, жанр, год публикации</li>
     *   <li>Список авторов (полная замена существующего списка)</li>
     * </ul>
     * <p>
     * Если ISBN изменяется, проверяется его уникальность.
     *
     * @param id  идентификатор книги, которую нужно обновить (должен быть > 0)
     * @param dto DTO с новыми данными книги
     * @return обновлённая книга с актуальными данными
     * @throws BookNotFoundException         если книга с указанным ID не найдена
     * @throws NotUniqueISBNCode             если новый ISBN уже используется другой книгой
     * @throws BookNotFoundException         если один или несколько указанных авторов не найдены
     * @throws DataIntegrityViolationException при нарушении ограничений базы данных
     */
    BookDTO update(Long id, BookDTO dto);
}