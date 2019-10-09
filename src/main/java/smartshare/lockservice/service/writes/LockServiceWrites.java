package smartshare.lockservice.service.writes;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import smartshare.lockservice.constant.KafkaKeys;
import smartshare.lockservice.model.File;
import smartshare.lockservice.model.Folder;
import smartshare.lockservice.repository.FileRepository;
import smartshare.lockservice.repository.FolderRepository;

import java.io.IOException;

import static smartshare.lockservice.constant.KafkaKeys.FILE;
import static smartshare.lockservice.constant.KafkaKeys.FOLDER;

@Slf4j
@Service
public class LockServiceWrites {

    private FileRepository fileRepository;

    private FolderRepository folderRepository;

    private ObjectMapper jsonConverter;


    @Autowired
    LockServiceWrites(FileRepository fileRepository, FolderRepository folderRepository,ObjectMapper jsonConverter ){
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
        this.jsonConverter = jsonConverter;
    }


    @KafkaListener(topics = "lock")
    public void consume(String fileOrFolderObjectInJsonAsStringFormat, ConsumerRecord record) throws IOException {
        System.out.println("fileOrFolderObjectInJsonAsStringFormat------------->"+fileOrFolderObjectInJsonAsStringFormat);
        System.out.println("record--------->"+record);
        if (record.key() == ( FILE )){
            File result = jsonConverter.readValue( fileOrFolderObjectInJsonAsStringFormat, File.class );
            System.out.println("result----file----->"+result);
            File result2 = fileRepository.save( result );
            System.out.println("result----file----->"+result2);

        }
        if (record.key() == ( FOLDER )){
            Folder result = jsonConverter.readValue(fileOrFolderObjectInJsonAsStringFormat, Folder.class);
            System.out.println("result----folder----->"+result);
            Folder result2 = folderRepository.save( result );
            System.out.println("result----folder----->"+result2);
        }
    }

}
