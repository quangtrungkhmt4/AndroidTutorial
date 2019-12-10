package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import repository.AccentRepository;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoty extends AbstractModel{
    private String id;
    private String name;
    private String link;

    public Categoty(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
