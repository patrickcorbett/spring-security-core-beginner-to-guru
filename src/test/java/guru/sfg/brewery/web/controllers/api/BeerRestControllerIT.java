package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

/*
 * @WebMvcTest by default excludes all JPA resources, by default Spring creates a InMemoryAuthentication provider which
 * was previously configured in our tests.
 *
 * With the move to the H2 Database for Authentication the test context creates a InMemoryAuthentication which is not
 * able to authenticate the test users.
 *
 * @SpringBootTest brings up the complete spring context and the tests continue to work.
 */
// @WebMvcTest
@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    /* Deactivate custom filter Tests*/
//    @Test
//    void deleteBeerUrl() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")
//                        .param("apiKey", "spring")
//                        .param("apiSecret", "guru"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    void deleteBeerUrlBadCredentials() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")
//                        .param("apiKey", "spring")
//                        .param("apiSecret", "guruXXX"))
//                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
//    }
//
//    @Test
//    void deleteBeerBadCredentials() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")
//                        .header("Api-Key", "spring")
//                        .header("Api-Secret", "guruXXX"))
//                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
//    }
//
//    @Test
//    void deleteBeer() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/ab73d1de-cbce-4ff2-9406-eabe2cf878a9")
//                        .header("Api-Key", "spring")
//                        .header("Api-Secret", "guru"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

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
