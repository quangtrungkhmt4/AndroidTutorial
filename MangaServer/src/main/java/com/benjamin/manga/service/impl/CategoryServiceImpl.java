package com.benjamin.manga.service.impl;

import com.benjamin.manga.model.Category;
import com.benjamin.manga.repository.CategoryRepository;
import com.benjamin.manga.service.base.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category insertEntity(Category entity) {
        return null;
    }

    @Override
    public Category updateEntity(Category entity) {
        return null;
    }

    @Override
    public void deteleEntity(String id) {

    }

    @Override
    public Collection<Category> getAllEntities(int skip, int take) {
        return categoryRepository.findAll();
    }
}
