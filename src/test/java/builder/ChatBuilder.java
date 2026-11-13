package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.domain.chat.Chat;

import java.util.HexFormat;

@UtilityClass
public class ChatBuilder {
    public static final Long DEFAULT_SPACE_ID = 10L;
    public static final byte[] DEFAULT_ENCRYPTED_DEK = HexFormat.of().parseHex(
            "65A8A5934285D23F5D5216952EF5DD369489927E210756E50D957BCBD80AB9F34B93AC2DC03EDBFEEADC34B5853EA2F1"
    );
    public static final byte[] DEFAULT_DEK_IV = HexFormat.of().parseHex("707EC183CEACBA3FD07638B7");
    public static final String DEFAULT_KEK_VERSION = "v1";

    public static Chat buildDefault() {
        Chat chat = new Chat();
        chat.setSpaceId(DEFAULT_SPACE_ID);
        chat.setEncryptedDek(DEFAULT_ENCRYPTED_DEK);
        chat.setDekIv(DEFAULT_DEK_IV);
        chat.setKekVersion(DEFAULT_KEK_VERSION);
        return chat;
    }

    public static Chat buildWith(Long spaceId) {
        Chat chat = buildDefault();
        chat.setSpaceId(spaceId);
        return chat;
    }
}
