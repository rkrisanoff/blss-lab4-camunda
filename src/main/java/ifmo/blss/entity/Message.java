package ifmo.blss.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
public class Message {
    @NotNull(message = "subject is null")
    @Size(min = 5, max = 50, message = "subject wrong size")
    private final String subject;
    @NotNull(message = "emailTo is null")
    @Email(message = "emailTo not valid email")
    private final String emailTo;
    @NotNull(message = "text is null")
    @Size(min = 20, max = 512, message = "text wrong size")
    private final String text;
}
