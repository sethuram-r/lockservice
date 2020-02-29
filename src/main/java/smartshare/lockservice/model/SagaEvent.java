package smartshare.lockservice.model;

import lombok.Data;

import java.util.List;

public @Data
class SagaEvent {

    private String eventId;
    private List<S3Object> objects;
    private String status;

}