package domain.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Reader {

    private int id;
    private String name;
    private LocalDate dateOfBirth;
    private Login login;

    public Reader(String nameInput, LocalDate birthdayInput, Login loginInput) {
        this.name = nameInput;
        this.dateOfBirth = birthdayInput;
        this.login = loginInput;
    }

    public Reader(int id, String nameInput, LocalDate birthdayInput) {
        this.id = id;
        this.name = nameInput;
        this.dateOfBirth = birthdayInput;
    }
}
