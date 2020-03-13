package com.benjamin.manga.controller.manga;

import com.benjamin.manga.controller.AbstractController;
import com.benjamin.manga.model.Category;
import com.benjamin.manga.request.GetCategoryRequest;
import com.benjamin.manga.response.GetCategoryResponse;
import com.benjamin.manga.response.ResponseEntity;
import com.benjamin.manga.service.base.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GetCategoryAPI extends AbstractController<GetCategoryRequest> {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String getCategories(@Valid GetCategoryRequest request){
        return handler(request);
    }

    @Override
    protected void validateRequest(GetCategoryRequest request) {

    }

    @Override
    protected String execute(GetCategoryRequest request) {
        List<Category> categories = (List<Category>) categoryService.getAllEntities(0, 100);
        GetCategoryResponse response = new GetCategoryResponse(categories);
        return new ResponseEntity(response).toString();
    }
}
