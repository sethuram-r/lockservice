package smartshare.lockservice.controller;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smartshare.lockservice.service.reads.ILockService;

@Slf4j
@RestController
@RequestMapping(value = "/lock-service",produces = "application/json")
public class LockController {

    private ILockService iLockService;

    @Autowired
    LockController( @Qualifier(value = "redisLockReadService") ILockService iLockService){
        this.iLockService = iLockService;
    }


    @GetMapping(value = "/status/file/{fileName}")
    public Boolean getLockStatusForCurrentFile(@PathVariable String fileName){
        log.info( "Inside getLockStatusForCurrentFile" );
        iLockService.getLockStatusForCurrentFile( fileName );
        return Boolean.FALSE;
    }

    @GetMapping(value = "/status/folder/{folderName}")
    public Boolean getLockStatusForCurrentFolder(@PathVariable String folderName){
        log.info( "Inside getLockStatusForCurrentFolder" );
        iLockService.getLockStatusForCurrentFolder( folderName );
        return Boolean.FALSE;
    }
}
