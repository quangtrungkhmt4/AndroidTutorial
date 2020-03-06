package com.benjamin.manga.service.base;

import com.benjamin.manga.model.AbstractModel;

import java.util.Collection;

public interface Service<T extends AbstractModel> {
    T insertEntity(T entity);
    T updateEntity(T entity);
    void deteleEntity(String id);
    Collection<T> getAllEntities(int skip, int take);
}
