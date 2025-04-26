package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeerUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")
                        .param("apiKey", "spring")
                        .param("apiSecret", "guru"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteBeerUrlBadCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")
                        .param("apiKey", "spring")
                        .param("apiSecret", "guruXXX"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void deleteBeerBadCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "guruXXX"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "guru"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteBeerHttpBasic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")
                .with(httpBasic("spring", "guru")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void findBeers() throws Exception {
        // GET Operations should not require authentication, see the SecurityConfig
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        // GET Operations should not require authentication, see the SecurityConfig
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception {
        // GET Operations should not require authentication, see the SecurityConfig
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/0631234200036")).andExpect(MockMvcResultMatchers.status().isOk());
    }


}
