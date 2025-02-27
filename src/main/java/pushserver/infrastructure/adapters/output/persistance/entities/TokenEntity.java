package pushserver.infrastructure.adapters.output.persistance.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@Document(collection = "pushtokens")
public class TokenEntity {

    @Id
    private String id = UUID.randomUUID().toString();;

    @Field(name = "os_id")
    @Indexed(unique = true)
    private String os;

    @Field(name = "phone_model")
    private String phoneModel;

    @Field(name = "push_token")
    @Indexed(unique = true)
    private String pushToken;

}
