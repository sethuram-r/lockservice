package smartshare.lockservice.controller;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import smartshare.lockservice.service.reads.ILockService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/lock-service",produces = "application/json")
public class LockController {

    private ILockService iLockService;

    @Autowired
    LockController( @Qualifier(value = "redisLockReadService") ILockService iLockService){
        this.iLockService = iLockService;
    }


    @GetMapping(value = "/status/object/{objectName}")
    public Boolean getLockStatusForCurrentObject(@PathVariable String objectName) {
        log.info( "Inside getLockStatusForCurrentFile" );
        iLockService.getLockStatusOfObject( objectName );
        return Boolean.FALSE;
    }

    @GetMapping(value = "/status/objects}")
    public List<Boolean> getLockStatusForCurrentObjects(@RequestParam List<String> objectNames) {
        log.info( "Inside getLockStatusForCurrentFolder" );
        return iLockService.getLockStatusOfObjects( objectNames );
    }
}
