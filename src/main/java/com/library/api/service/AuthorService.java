package com.library.api.service;

import com.library.api.exception.notFound.AuthorNotFoundException;
import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.dto.BookDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Сервис для работы с авторами библиотеки.
 * Предоставляет CRUD-операции, поиск и получение связанных книг.
 *
 * @see AuthorDTO
 * @see BookDTO
 */
public interface AuthorService {
    /**
     * Создаёт нового автора.
     *
     * @param dto данные для создания (имя, фамилия, дата рождения); не {@code null}
     * @return сохранённый автор с присвоенным {@code id}
     * @throws DataIntegrityViolationException при нарушении ограничений БД
     */
    AuthorDTO create(AuthorDTO dto);

    /**
     * Получает автора по его ID.
     *
     * @param id идентификатор автора; должен быть {@code > 0}
     * @return DTO найденного автора
     * @throws AuthorNotFoundException если автор с указанным {@code id} не найден
     */
    AuthorDTO getById(Long id);

    /**
     * Возвращает пагинированный список всех авторов.
     *
     * @param pageable параметры пагинации и сортировки; не {@code null}
     * @return страница с DTO авторов (может быть пустой)
     */
    Page<AuthorDTO> getAll(Pageable pageable);


    /**
     * Обновляет данные существующего автора.
     * <p>
     * Обновляются поля: {@code firstName}, {@code lastName}, {@code birthDate}.
     * Поле {@code id} берётся из пути запроса, а не из DTO.
     *
     * @param id  идентификатор автора для обновления
     * @param dto новые данные автора
     * @return обновлённый автор в формате DTO
     * @throws AuthorNotFoundException если автор с указанным {@code id} не найден
     */
    AuthorDTO update(Long id, AuthorDTO dto);

    /**
     * Удаляет автора по его ID.
     * <p>
     *  Удаление невозможно если у автора есть связанные книги
     * </p>
     * @param id идентификатор автора
     * @return DTO удалённого автора
     * @throws AuthorNotFoundException если автор не найден
     * @throws IllegalStateException если у автора есть книги
     */
    AuthorDTO remove(Long id);

    Page<AuthorDTO> searchByName(String query, Pageable pageable);

    List<BookDTO> getBooksByAuthorId(Long authorId);
}
