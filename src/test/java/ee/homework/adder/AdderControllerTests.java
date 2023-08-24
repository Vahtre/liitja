package ee.homework.adder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
public class AdderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test case to check the response when the values of addables are greater than 100.
     */
    @Test
    public void testAddOperationInvalidAddableValue() throws Exception {
        Integer addable1 = 150;
        Integer addable2 = 50;
        String expectedErrorMessage = "Addable1 is greater than 100.";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/" + addable2))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));
    }

    /**
     * Test case to check the response when the value of addable2 is greater than 100.
     */
    @Test
    public void testAddOperationInvalidAddableValue1() throws Exception {
        Integer addable1 = 15;
        Integer addable2 = 500;
        String expectedErrorMessage = "Addable2 is greater than 100.";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/"+ addable2))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));
    }

    /**
     * Test case to check the response when the value of addable1 is lower than 0.
     */
    @Test
    public void testAddOperationInvalidAddableValue2() throws Exception {
        Integer addable1 = -15;
        Integer addable2 = 0;
        String expectedErrorMessage = "Addable1 is lower than 0.";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/"+ addable2))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));
    }

    /**
     * Test case to check the response when the value of addable2 is lower than 0.
     */
    @Test
    public void testAddOperationInvalidAddableValue3() throws Exception {
        Integer addable1 = 5;
        Integer addable2 = -1;
        String expectedErrorMessage = "Addable2 is lower than 0.";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/"+ addable2))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));
    }

    /**
     * Test case to check the response when the value of addable1 is not a valid integer.
     */
    @Test
    public void testAddOperationInvalidAddableValue4() throws Exception {
        String addable1 = "addable1";
        Integer addable2 = 5;
        String expectedErrorMessage = "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; For input string: \"addable1\"";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/"+ addable2))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));

    }

    /**
     * Test case to check the response when the value of addable2 is not a valid integer.
     */
    @Test
    public void testAddOperationInvalidAddableValue5() throws Exception {
        Integer addable1 = 5;
        String addable2 = "addable2";
        String expectedErrorMessage = "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; For input string: \"addable2\"";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/"+ addable2))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));
    }

    /**
     * Test case to check the response when the second addable value is missing.
     */
    @Test
    public void testAddOperationInvalidAddableValue6() throws Exception {
        Integer addable1 = 5;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    /**
     * Test case to check the response when both addable values are missing.
     */
    @Test
    public void testAddOperationInvalidAddableValue7() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/addable2"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    /**
     * Test case to check the response when both addable values are lower than 0.
     */
    @Test
    public void testAddOperationInvalidAddableValue8() throws Exception {
        Integer addable1 = -3;
        Integer addable2 = -1;
        String expectedErrorMessage1 = "Addable1 is lower than 0.";
        String expectedErrorMessage2 = "Addable2 is lower than 0.";

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/" + addable2))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(response, containsString(expectedErrorMessage1));
        assertThat(response, containsString(expectedErrorMessage2));

    }

    /**
     * Test case to check the response when both addable values are greater than 100.
     */
    @Test
    public void testAddOperationInvalidAddableValue9() throws Exception {
        Integer addable1 = 101;
        Integer addable2 = 102;
        String expectedErrorMessage1 = "Addable1 is greater than 100.";
        String expectedErrorMessage2 = "Addable2 is greater than 100.";

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/" + addable2))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(response, containsString(expectedErrorMessage1));
        assertThat(response, containsString(expectedErrorMessage2));
    }

    /**
     * Test case to check the response when both addable values are within valid range [0, 100].
     */
    @Test
    public void testAddOperationEdgeCase() throws Exception {
        Integer addable1 = 0;
        Integer addable2 = 99;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/"+ addable2))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test case to check the response when both addable values are within valid range [0, 100].
     */
    @Test
    public void testAddOperationEdgeCase2() throws Exception {
        Integer addable1 = 2;
        Integer addable2 = 99;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/add/" + addable1 + "/"+ addable2))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test case to check the response when an invalid ascending type is provided.
     */
    @Test
    public void testFindOperationInvalidAscendingType() throws Exception {
        String ascending = "abc";
        String expectedErrorMessage = "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Boolean'; Invalid boolean value [abc]";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/find")
                        .param("ascending", ascending)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));
    }

    /**
     * Test case to check the response when a valid ascending type true is provided.
     */
    @Test
    public void testFindOperationValidAscendingType1() throws Exception {
        boolean ascending = true;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/find")
                        .param("ascending", String.valueOf(ascending))
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test case to check the response when a valid ascending type false is provided.
     */
    @Test
    public void testFindOperationValidAscendingType2() throws Exception {
        boolean ascending = false;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/find")
                        .param("ascending", String.valueOf(ascending))
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test case to check the response when an invalid findable value, lower than 0, is provided.
     */
    @Test
    public void testFindOperationInValidIntegerType2() throws Exception {
        String expectedErrorMessage = "Findable is lower than 0.";
        boolean ascending = false;
        Integer findable = -15;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/find")
                        .param("ascending", String.valueOf(ascending))
                        .param("findable", String.valueOf(findable))
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));
    }

    /**
     * Test case to check the response when an invalid findable value, greater than 100, is provided.
     */
    @Test
    public void testFindOperationInValidIntegerType3() throws Exception {
        String expectedErrorMessage = "Findable is greater than 100.";
        boolean ascending = false;
        Integer findable = 101;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adder/find")
                        .param("ascending", String.valueOf(ascending))
                        .param("findable", String.valueOf(findable))
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));
    }

}