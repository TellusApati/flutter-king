package hackathon.fluttershy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Citizen
{
    @Id
    private Integer citizen_id;
    private String name;
    private String text;
    private String tts;
    private int ai_religion;
    private int ai_population;
    private int ai_army;
    private int ai_economics;
    private int di_religion;
    private int di_population;
    private int di_army;
    private int di_economics;
    private String accept_token;
    private String deny_token;
    private String start_token;
}
