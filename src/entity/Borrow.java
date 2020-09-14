package entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Borrow implements SuperEntity {
    private String borrow_id;
    private String m_id;
    private String isbn;
    private Date date;
}
