package pl.edu.pk.cosmo.cookbookbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.SaveExampleRequest;
import pl.edu.pk.cosmo.cookbookbackend.converter.ExampleConverter;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.ExampleDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.interfaces.ExampleService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Patryk Borchowiec
 */
@WebMvcTest(controllers = ExampleController.class)
class ExampleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExampleService exampleService;

    @MockBean
    private ExampleConverter exampleConverter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    class save {
        @Test
        void nameIsBlank_shouldReturn400() throws Exception {
            // given
            final SaveExampleRequest request = new SaveExampleRequest();
            request.setName("");

            // when

            // then
            mockMvc.perform(post("/example")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void exampleServiceThrowsAlreadyExistsException_shouldReturn409() throws Exception {
            // given
            final String exampleName = "example_name";
            final SaveExampleRequest request = new SaveExampleRequest();
            request.setName(exampleName);
            final ExampleDTO exampleDTO = new ExampleDTO();
            exampleDTO.setName(exampleName);

            // when
            when(exampleConverter.toDTO(any(SaveExampleRequest.class))).thenReturn(exampleDTO);
            doThrow(AlreadyExistsException.class).when(exampleService).save(exampleDTO);

            // then
            mockMvc.perform(post("/example")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isConflict());
        }

        @Test
        void properData_shouldReturn200() throws Exception {
            // given
            final String exampleName = "example_name";
            final SaveExampleRequest request = new SaveExampleRequest();
            request.setName(exampleName);
            final ExampleDTO exampleDTO = new ExampleDTO();
            exampleDTO.setName(exampleName);

            // when
            when(exampleConverter.toDTO(any(SaveExampleRequest.class))).thenReturn(exampleDTO);
            doNothing().when(exampleService).save(exampleDTO);

            // then
            mockMvc.perform(post("/example")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }
    }
}