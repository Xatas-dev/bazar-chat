package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.domain.reaction.Reaction;
import org.bazar.chat.domain.reaction.ReactionType;

@UtilityClass
public class ReactionBuilder {
    public static final String DEFAULT_CODE = "LIKE";
    public static final String DEFAULT_VALUE = "👍";
    public static final ReactionType type = ReactionType.UNICODE;

    public static Reaction buildDefault() {
        Reaction reaction = new Reaction();
        reaction.setCode(DEFAULT_CODE);
        reaction.setValue(DEFAULT_VALUE);
        reaction.setType(type);
        return reaction;
    }

    public static Reaction buildWith(String code, String value, ReactionType type) {
        Reaction reaction = new Reaction();
        reaction.setCode(code);
        reaction.setValue(value);
        reaction.setType(type);
        return reaction;
    }
}
