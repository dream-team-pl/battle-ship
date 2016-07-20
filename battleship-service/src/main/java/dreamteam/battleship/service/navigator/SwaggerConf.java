package dreamteam.battleship.service.navigator;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration for swagger2.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConf{
    @Bean
    public Docket customImplementation(){
        return new Docket(new DocumentationType("Dreamteam","1.0")).pathMapping("/service/");
    }
}
