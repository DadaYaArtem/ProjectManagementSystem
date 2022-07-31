package tables.skill;

import lombok.Data;

@Data
public class Skill {
    private long id;
    private Branch branch;
    private Skillset skill;

    public enum Branch {
        Java,
        CSHARP,
        JS
    }


    public enum Skillset {
        Junior,
        Middle,
        Senior
    }
}