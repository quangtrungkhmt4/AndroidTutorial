package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accent extends AbstractModel{
    private String name;

    public Accent(String id, String name) {
        super(id);
        this.name = name;
    }
}
