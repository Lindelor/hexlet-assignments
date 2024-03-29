package exercise.controller;

import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    private Task generateTask() {
        return Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .create();
    }

    @Test
    public void testShow() throws Exception {
        var task = generateTask();
        taskRepository.save(task);
        var result = mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isNotNull();
        assertThatJson(body.contains("\"id\":1"));
        assertThatJson(body.contains("\"title\":"));
        assertThatJson(body.contains("\"description\":"));
        assertThatJson(body.contains("\"updatedAt\":"));
        assertThatJson(body.contains("\"createdAt\":"));
    }

    @Test
    public void testShowNegative() throws Exception {
        var task = generateTask();
        taskRepository.save(task);
        var result = mockMvc.perform(get("/tasks/9999"))
                .andExpect(status().isNotFound())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).isEqualTo("Task with id 9999 not found");
    }

    @Test
    public void testCreate() throws Exception {
        var task = generateTask();
        var result = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body.contains("\"title\":"));
        assertThatJson(body.contains("\"description\":"));
        assertThatJson(body.contains("\"updatedAt\":"));
        assertThatJson(body.contains("\"createdAt\":"));

        assertThatJson(body.contains(task.getTitle()));
        assertThatJson(body.contains("" + task.getId()));

    }

    @Test
    public void testRemove() throws Exception {
        var task = generateTask();
        var result = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body.contains("\"title\":"));
        assertThatJson(body.contains("\"description\":"));
        assertThatJson(body.contains("\"updatedAt\":"));
        assertThatJson(body.contains("\"createdAt\":"));

        var taskId = task.getId();

        assertThatJson(body.contains(task.getTitle()));
        assertThatJson(body.contains("" + taskId));

        var remove = mockMvc.perform(delete("/tasks/" + 4))
                .andExpect(status().isOk());

        var resultNotFound = mockMvc.perform(get("/tasks/" + 4))
                .andExpect(status().isNotFound())
                .andReturn();

        var bodyNotFound = resultNotFound.getResponse().getContentAsString();
        assertThat(bodyNotFound).isEqualTo(String.format("Task with id %d not found", 4));
    }

    @Test
    public void testUpdate() throws Exception {
        var task = generateTask();
        var result = mockMvc.perform(put("/tasks/" + 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body.contains("\"title\":"));
        assertThatJson(body.contains("\"description\":"));
        assertThatJson(body.contains("\"updatedAt\":"));
        assertThatJson(body.contains("\"createdAt\":"));

        assertThatJson(body.contains(task.getTitle()));
        assertThatJson(body.contains("" + task.getId()));
    }
}
