package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Author extends AbstractModel{
    private String name;

    public Author(String id, String name) {
        super(id);
        this.name = name;
    }
}
