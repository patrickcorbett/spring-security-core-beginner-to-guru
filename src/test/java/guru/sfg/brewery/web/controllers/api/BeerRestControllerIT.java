package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT {

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


}
