package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book implements SuperEntity{
    private String isbn;
    private String title;
    private String author;
    private String edition;
}
