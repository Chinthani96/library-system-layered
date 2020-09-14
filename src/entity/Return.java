package entity;

import lombok.*;

import java.sql.Date;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@ToString
public class Return implements SuperEntity{
    private String borrow_id;
    private Date date;
}
