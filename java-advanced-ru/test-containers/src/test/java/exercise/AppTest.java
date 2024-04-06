package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.MediaType;

import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("database")
            .withUsername("sa")
            .withPassword("sa")
            .withInitScript("init.sql");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        // Устанавливаем URL базы данных
        registry.add("spring.datasource.url", database::getJdbcUrl);
        // Имя пользователя и пароль для подключения
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        // Эти значения приложение будет использовать при подключении к базе данных
    }

    @Test
    void testCreatePerson() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/people")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\": \"Jackson\", \"lastName\": \"Bind\"}")
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/people"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Jackson", "Bind");
    }

    @Test
    void testIndex() throws Exception {
        MockHttpServletResponse responseIndex = mockMvc
                .perform(get("/people"))
                .andReturn()
                .getResponse();

        assertThat(responseIndex.getStatus()).isEqualTo(200);
        assertThat(responseIndex.getContentAsString()).isEqualTo("[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\"},{\"id\":2,\"firstName\":\"Jack\",\"lastName\":\"Doe\"},{\"id\":3,\"firstName\":\"Jassica\",\"lastName\":\"Simpson\"},{\"id\":4,\"firstName\":\"Robert\",\"lastName\":\"Lock\"}]");
    }

    @Test
    void testShow() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/people/1"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\"}");
    }

    @Test
    void testRemove() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(delete("/people/1"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);

        MockHttpServletResponse response1 = mockMvc
                .perform(get("/people/1"))
                .andReturn()
                .getResponse();

        assertThat(response1.getContentAsString()).isEqualTo("");
    }

    @Test
    void testUpdate() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(patch("/people/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"firstName\": \"Alia\", \"lastName\": \"Dong\"}")
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);

        MockHttpServletResponse response1 = mockMvc
                .perform(get("/people/1"))
                .andReturn()
                .getResponse();

        assertThat(response1.getContentAsString()).isEqualTo("{\"id\":1,\"firstName\":\"Alia\",\"lastName\":\"Dong\"}");
    }
}
