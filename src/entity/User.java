package entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements SuperEntity{
    private User_PK user_pk;

    public User(String name, String password) {
        this.user_pk = new User_PK(name,password);
    }
}
