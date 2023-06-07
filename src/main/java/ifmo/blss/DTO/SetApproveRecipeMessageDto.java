package ifmo.blss.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//import javax.validation.constraints.Email;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
import kotlinx.serialization.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Serializable
@NoArgsConstructor
@AllArgsConstructor
public class SetApproveRecipeMessageDto {
    @NotNull(message = "subject is null")
    @Size(min = 5, max = 50, message = "subject wrong size")
    private String subject;
    @NotNull(message = "emailTo is null")
    @Email(message = "emailTo not valid email")
    private String emailTo;
    @NotNull(message = "text is null")
    @Size(min = 20, max = 512, message = "text wrong size")
    private String text;
}
