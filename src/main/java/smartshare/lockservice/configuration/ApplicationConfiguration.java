package smartshare.lockservice.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ObjectMapper customObjectMapperForKafka() {
        return new ObjectMapper();
    }
}
