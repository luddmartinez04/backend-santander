package santander.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "backend-santander",
                version = "1.0",
                description = "CRUD to Santander challenge"
        )
)
public class OpenApiConfig {
}
