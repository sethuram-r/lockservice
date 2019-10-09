package smartshare.lockservice.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "lock")
public class File {

    @Id
    @Indexed
    private String fileName;
    private Boolean lockStatus;

    File(String fileName){
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public Boolean getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Boolean lockStatus) {
        this.lockStatus = lockStatus;
    }
}
