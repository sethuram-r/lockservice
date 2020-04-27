package smartshare.lockservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping(value = "/status/object")
    public Boolean getLockStatusForCurrentObject(@RequestParam String objectName) {
        log.info( "Inside getLockStatusForCurrentFile" );
//        return lockService.getLockStatusOfObject( objectName );
        return Boolean.FALSE;
    }

    @GetMapping(value = "/status/objects")
    public Boolean getLockStatusForCurrentObjects(@RequestParam String objectName) {
        log.info( "Inside getLockStatusForCurrentFolder" );
//        return lockService.getLockStatusOfObjects( objectName );
        return Boolean.FALSE;
    }


}
