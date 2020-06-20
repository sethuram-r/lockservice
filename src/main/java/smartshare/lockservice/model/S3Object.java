package smartshare.lockservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash(value = "lock", timeToLive = 60)
@AllArgsConstructor
@NoArgsConstructor
public @Data
class S3Object {

    @Id
    private String id;
    @Indexed
    private String objectName;

    @JsonCreator
    public S3Object(@JsonProperty("objectName") String objectName) {
        this.objectName = objectName;
    }


}
