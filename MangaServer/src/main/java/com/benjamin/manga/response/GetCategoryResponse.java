package com.benjamin.manga.response;

import com.benjamin.manga.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoryResponse extends AbstractResponse{
    private List<Category> categories;
}
