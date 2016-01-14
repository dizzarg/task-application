package org.kadyrov.task.dao.api;

import org.kadyrov.task.dao.api.domain.Task;
import org.kadyrov.task.dao.api.exception.DAOException;

import java.util.List;

/**
 * Defines pattern "Data Access Object" as a mechanism for encapsulating storage,
 * retrieval, and search behavior which emulates a collection of objects.
 *
 * @param <T>  type of entity
 * @param <PK> identifier of current entity
 */
public interface Dao<T, PK> {

    List<Task> findAll(int maxResult, int count) throws DAOException;

    /**
     * Find all elements.
     *
     * @return List entries or empty list id entries not found
     * @throws DAOException if we have database problem
     */
    List<T> findAll() throws DAOException;

    /**
     * Create new entity or update if primary key fields is empty
     *
     * @param t entity
     * @return entity
     * @throws DAOException if we have database problem
     */
    T save(T t) throws DAOException;

    /**
     * Remove entity by primary key
     *
     * @param id primary key value, it must not be null
     * @throws DAOException if we have database problem
     */
    void remove(PK id) throws DAOException;

    /**
     * Get one entity by primary key.
     * If entity not found it throws {@link org.kadyrov.task.dao.api.exception.ZeroRowsAffectedException} exception
     *
     * @param id primary key value, it must not be null
     * @return entity
     * @throws DAOException
     */
    T findById(PK id) throws DAOException;
}
