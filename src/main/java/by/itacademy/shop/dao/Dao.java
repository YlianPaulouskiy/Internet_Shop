package by.itacademy.shop.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    /**
     * Ищет сущность по заданному идентификатору
     *
     * @param id индивидуальный идентификатор
     * @return Optional<сущности>, если сущность есть в базе
     * Optional.empty, если сущности в базе нет
     */
    Optional<E> findById(K id);

    /**
     * Список всех сущностей которые есть в базе данных
     *
     * @return List<сущности>
     */
    List<E> findAll();

    /**
     * Сохраняет сущность в базе данных
     *
     * @param entity сущность, которую необходимо сохранить
     * @return Optional<сущности>, если сущность сохранилась
     * Optional.empty, если  нет
     */
    Optional<E> save(E entity);

    /**
     * Обновляет сущность в базе данных
     *
     * @param entity сущность, которую необходимо обновить
     * @return true - если обновилась
     * false - если нет
     */
    boolean update(E entity);

    /**
     * Удаляет  сущность из базы данных по id
     *
     * @param id индивидуальный идентификатор
     * @return true - если удалилась
     * false - если нет
     */
    boolean delete(K id);

}
