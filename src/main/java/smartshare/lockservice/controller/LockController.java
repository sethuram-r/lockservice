package smartshare.lockservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smartshare.lockservice.service.reads.LockService;


@Slf4j
@RestController
@RequestMapping(value = "/lock-service",produces = "application/json")
public class LockController {

    private LockService lockService;

    @Autowired
    LockController(@Qualifier(value = "redisLockReadService") LockService lockService) {
        this.lockService = lockService;
    }


    @GetMapping(value = "/status/object/{objectName}")
    public Boolean getLockStatusForCurrentObject(@PathVariable String objectName) {
        log.info( "Inside getLockStatusForCurrentFile" );
        lockService.getLockStatusOfObject( objectName );
        return Boolean.FALSE;
    }

    @GetMapping(value = "/status/objects/{objectName}")
    public Boolean getLockStatusForCurrentObjects(@PathVariable String objectName) {
        log.info( "Inside getLockStatusForCurrentFolder" );
        return lockService.getLockStatusOfObjects( objectName );
    }


}
