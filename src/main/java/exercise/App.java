package exercise;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                // Название
                title = "Shorts API",
                // Версия
                version = "1.0",
                // Описание
                description = "Тестовое задание на создание сервиса коротких ссылок"
        )
)
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }
}
