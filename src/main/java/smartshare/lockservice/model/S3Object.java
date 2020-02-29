package smartshare.lockservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash(value = "lock", timeToLive = 60)
@AllArgsConstructor
public @Data
class S3Object {

    @Id
    private String id;
    @Indexed
    private String objectName;


}
