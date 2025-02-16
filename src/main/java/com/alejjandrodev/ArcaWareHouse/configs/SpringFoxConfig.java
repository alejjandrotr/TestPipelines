package com.alejjandrodev.ArcaWareHouse.configs;

import com.alejjandrodev.ArcaWareHouse.utils.ILoggerWriter;
import com.alejjandrodev.ArcaWareHouse.utils.LoggerJsonAdapter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFoxConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Supliers API")
                        .description("This apis are for Crud of supliers")
                        .version("1.0")
                );
    }


}
