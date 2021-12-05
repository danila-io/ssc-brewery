package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/6de204c4-57e1-4793-b2ee-48ba84c0ff9a"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/123456789101"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/6de204c4-57e1-4793-b2ee-48ba84c0ff9a")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "spring"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerBadCredentials() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/6de204c4-57e1-4793-b2ee-48ba84c0ff9a")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "spring123"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerWithHttpBasic() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/6de204c4-57e1-4793-b2ee-48ba84c0ff9a")
                        .with(httpBasic("spring", "spring")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/6de204c4-57e1-4793-b2ee-48ba84c0ff9a"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerUrl() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .param("apiKey","spring").param("apiSecret", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerBadCredsUrl() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .param("apiKey","spring").header("apiSecret", "guruXXXX"))
                .andExpect(status().isUnauthorized());
    }
}
