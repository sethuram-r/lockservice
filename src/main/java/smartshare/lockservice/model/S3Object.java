package smartshare.lockservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash(value = "lock", timeToLive = 60)
public class S3Object {

    @Id
    @Indexed
    private String objectName;
    private Boolean lockStatus;

    S3Object(String fileName) {
        this.objectName = fileName;
    }

    public String getObjectName() {
        return objectName;
    }

    public Boolean getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Boolean lockStatus) {
        this.lockStatus = lockStatus;
    }
}
