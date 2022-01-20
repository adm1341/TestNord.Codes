package exercise;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import exercise.model.User;
import exercise.model.UserRole;
import exercise.repository.URLRepository;
import exercise.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DBRider
@DataSet("users.yml")

public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    URLRepository urlRepository;

    @Test
    void testAdminCanDeleteShortURL() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(
                        delete("/short/eMrnDFb")
                                .header("Authorization", "Basic YWRtaW5AZ21haWwuY29tOmFkbWluX3Bhc3M=")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        boolean isURLPresent = urlRepository.findByShortUrl("eMrnDFb").isPresent();
        assertThat(isURLPresent).isFalse();

    }

    @Test
    void testUnauthenticatedDeleteShortURL() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(
                        delete("/short/7zf2mt7")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }

    @Test
    void testAdminCanShowInfo() throws Exception {
        MockHttpServletResponse response1 = mockMvc
                .perform(
                        get("/short/7zf2mt7/info")
                                .header("Authorization", "Basic YWRtaW5AZ21haWwuY29tOmFkbWluX3Bhc3M=")
                )
                .andReturn()
                .getResponse();

        assertThat(response1.getStatus()).isEqualTo(200);
        assertThat(response1.getContentAsString()).contains("docs.spring.io");
    }

    @Test
    void testUnauthenticatedShowInfo() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(
                        get("/short/7zf2mt7/info")
                )
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }





    @Test
    void testUnauthenticatedRootPage() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void testCreateUser() throws Exception {
        String content = "{\"username\": \"Petr_12\", \"email\": \"petrilo@yandex.ru\", \"password\": \"mypass\"}";

        MockHttpServletResponse responsePost = mockMvc
                .perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andReturn()
                .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        // Проверяем, что пользователь добавился в базу данных
        User actualUser = userRepository.findByEmail("petrilo@yandex.ru").get();
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getUsername()).isEqualTo("Petr_12");

        // Проверяем, что пароль хранится в базе в зашифрованном виде
        assertThat(actualUser.getPassword()).isNotEqualTo("mypass");

        // Проверяем, что новый пользователь создаётся с ролью USER
        assertThat(actualUser.getRole()).isEqualTo(UserRole.USER);

        // Проверяем, что новый пользователь успешно проходит аутентификацию
        MockHttpServletResponse response = mockMvc
                .perform(
                        get("/users")
                                .header("Authorization", "Basic cGV0cmlsb0B5YW5kZXgucnU6bXlwYXNz")
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    // Проверяем полномочия пользователя

    // Проверяем, что пользователь ролью USER не может удалять пользователей
    @Test
    void testUserCannotDeleteUser() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(
                        delete("/users/2")
                                .header("Authorization", "Basic YWxleGlzQGdtYWlsLmNvbToxMjM0NQ==")
                )
                .andReturn()
                .getResponse();

        // Проверяем, что статус ответа 403 Forbidden
        // Код ответа 403 Forbidden указывает, что сервер понял запрос, но отказывается его авторизовать.
        assertThat(response.getStatus()).isEqualTo(403);
    }

    // Проверяем полномочия администратора (роль ADMIN)

    // Администратор может просматривать данные пользователей
    @Test
    void testAdminCanShowUsers() throws Exception {
        MockHttpServletResponse response1 = mockMvc
                .perform(
                        get("/users")
                                .header("Authorization", "Basic YWRtaW5AZ21haWwuY29tOmFkbWluX3Bhc3M=")
                )
                .andReturn()
                .getResponse();

        assertThat(response1.getStatus()).isEqualTo(200);
        assertThat(response1.getContentAsString()).contains("Alexis", "alexis@gmail.com");
        assertThat(response1.getContentAsString()).contains("Admin", "admin@gmail.com");

        MockHttpServletResponse response2 = mockMvc
                .perform(
                        get("/users/1")
                                .header("Authorization", "Basic YWRtaW5AZ21haWwuY29tOmFkbWluX3Bhc3M=")
                )
                .andReturn()
                .getResponse();

        assertThat(response2.getStatus()).isEqualTo(200);
        assertThat(response2.getContentAsString()).contains("Alexis", "alexis@gmail.com");
    }


}
