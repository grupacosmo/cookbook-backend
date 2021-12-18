package pl.edu.pk.cosmo.cookbookbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.pk.cosmo.cookbookbackend.controller.request.SaveAccountRequest;
import pl.edu.pk.cosmo.cookbookbackend.converter.AccountConverter;
import pl.edu.pk.cosmo.cookbookbackend.service.dto.AccountDTO;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.AlreadyExistsException;
import pl.edu.pk.cosmo.cookbookbackend.service.exception.NoAccountException;
import pl.edu.pk.cosmo.cookbookbackend.service.interfaces.AccountService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountConverter accountConverter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    class save {

        @Test
        void nameIsBlank_shouldReturn400() throws Exception {
            // given
            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName("");
            request.setEmail("b");
            request.setPassword("c");

            // when

            // then
            mockMvc.perform(post("/api/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void emailIsBlank_shouldReturn400() throws Exception {
            // given
            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName("a");
            request.setEmail("");
            request.setPassword("c");

            // when

            // then
            mockMvc.perform(post("/api/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isBadRequest());
        }

        @Test
        void passwordIsBlank_shouldReturn400() throws Exception {
            // given
            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName("a");
            request.setEmail("b");
            request.setPassword("");

            // when

            // then
            mockMvc.perform(post("/api/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void fieldsAreBlank_shouldReturn400() throws Exception {
            // given
            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName("");
            request.setEmail("");
            request.setPassword("");

            // when

            // then
            mockMvc.perform(post("/api/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void nameAndEmailAreBlank_shouldReturn400() throws Exception {
            // given
            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName("");
            request.setEmail("");
            request.setPassword("");

            // when

            // then
            mockMvc.perform(post("/api/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void nameAndPasswordAreBlank_shouldReturn400() throws Exception {
            // given
            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName("");
            request.setEmail("b");
            request.setPassword("");

            // when

            // then
            mockMvc.perform(post("/api/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void emailAndPasswordAreBlank_shouldReturn400() throws Exception {
            // given
            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName("a");
            request.setEmail("");
            request.setPassword("");

            // when

            // then
            mockMvc.perform(post("/api/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void properData_shouldReturn200() throws Exception {
            // given
            final String name="a";
            final String email="b";
            final String pass="pass";

            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName(name);
            request.setEmail(email);
            request.setPassword(pass);

            final AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(name);
            accountDTO.setEmail(email);
            accountDTO.setPassword(pass);

            // when
            when(accountConverter.toDTO(Mockito.any(SaveAccountRequest.class))).thenReturn(accountDTO);
            doNothing().when(accountService).save(accountDTO);

            // then
            mockMvc.perform(post("/api/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isOk());


        }

        @Test
        void alreadyExistsWithGivenEmail_shouldTrowAlreadyExistsException_shouldReturn409() throws Exception {
            // given
            final String name = "a";
            final String email = "b";
            final String pass = "pass";

            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName(name);
            request.setEmail(email);
            request.setPassword(pass);

            final AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(name);
            accountDTO.setEmail(email);
            accountDTO.setPassword(pass);

            // when
            when(accountConverter.toDTO(Mockito.any(SaveAccountRequest.class))).thenReturn(accountDTO);
            doThrow(AlreadyExistsException.class).when(accountService).save(accountDTO);

            // then
            mockMvc.perform(post("/api/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isConflict());

        }

    }

    @Nested
    class getAccountById {
        @Test
        void noAccountWithGivenId_shouldReturn404() throws Exception {
            // given
            Long id = 1L;

            // when
            doThrow(NoAccountException.class).when(accountService).getAccountById(id);

            // then
            mockMvc.perform(post("/api/account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("id", String.valueOf(id)))
                            .andExpect(status().isNotFound());
        }

        @Test
        void invalidIdParam_shouldReturn400() throws Exception {

            // given
            Long id = null;

            // when

            // then
            mockMvc.perform(post("/api/account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("id", String.valueOf(id)))
                            .andExpect(status().isBadRequest());
        }

        @Test
        void properId_shouldReturn200() throws Exception {
            // given
            final Long id = 1L;
            final String name="a";
            final String email="b";
            final String pass="pass";

            final SaveAccountRequest request = new SaveAccountRequest();
            request.setName(name);
            request.setEmail(email);
            request.setPassword(pass);

            final AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(name);
            accountDTO.setEmail(email);
            accountDTO.setPassword(pass);

            // when
            when(accountConverter.toDTO(Mockito.any(SaveAccountRequest.class))).thenReturn(accountDTO);
            doNothing().when(accountService).save(accountDTO);

            // then
            mockMvc.perform(post("/api/account")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("id", String.valueOf(id)))
                    .andExpect(status().isOk());
        }

    }
}
