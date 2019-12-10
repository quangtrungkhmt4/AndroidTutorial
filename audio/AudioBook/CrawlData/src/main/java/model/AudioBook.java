package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AudioBook extends AbstractModel{
    private String name;
    private List<Part> parts;
    private String idAuthor;
    private String image;
    private String duration;
    private Integer numberOfPart;
    private Long views;
    private String idAccent;
    private String idCategory;
    private Long createdAt;
    private String relatedAudio;
}
