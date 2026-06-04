package com.library.api.service;

import com.library.api.exception.notFound.AuthorNotFoundException;
import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.dto.BookDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Сервис для управления авторами книг.
 * <p>
 * Предоставляет полный набор CRUD-операций, поиск и получение связанных книг.
 *
 * @see AuthorDTO
 * @see BookDTO
 */
public interface AuthorService {

    /**
     * Создаёт нового автора.
     *
     * @param dto данные автора (имя, фамилия, дата рождения и т.д.)
     * @return созданный автор с присвоенным идентификатором
     * @throws DataIntegrityViolationException при нарушении уникальности данных
     */
    AuthorDTO create(AuthorDTO dto);

    /**
     * Возвращает автора по его идентификатору.
     *
     * @param id идентификатор автора (должен быть > 0)
     * @return DTO автора
     * @throws AuthorNotFoundException если автор с указанным ID не найден
     */
    AuthorDTO getById(Long id);

    /**
     * Возвращает пагинированный список всех авторов.
     *
     * @param pageable параметры пагинации и сортировки
     * @return страница с авторами
     */
    Page<AuthorDTO> getAll(Pageable pageable);

    /**
     * Обновляет данные существующего автора.
     *
     * @param id  идентификатор автора, которого нужно обновить
     * @param dto новые данные автора
     * @return обновлённый автор
     * @throws AuthorNotFoundException если автор не найден
     */
    AuthorDTO update(Long id, AuthorDTO dto);

    /**
     * Удаляет автора по ID.
     * <p>
     * Удаление запрещено, если у автора есть связанные книги.
     *
     * @param id идентификатор автора
     * @return удалённый автор
     * @throws AuthorNotFoundException если автор не найден
     * @throws IllegalStateException   если у автора есть связанные книги
     */
    AuthorDTO remove(Long id);

    /**
     * Поиск авторов по части имени или фамилии.
     *
     * @param query    поисковый запрос (имя/фамилия)
     * @param pageable параметры пагинации и сортировки
     * @return страница с найденными авторами
     */
    Page<AuthorDTO> searchByName(String query, Pageable pageable);

    /**
     * Возвращает список всех книг указанного автора.
     *
     * @param id идентификатор автора
     * @param pageable
     * @return список книг автора
     */
    Page<BookDTO> getBooksByAuthorId(Long id, Pageable pageable);
}