package ee.homework.adder;

import ee.homework.adder.dto.OperationDto;
import ee.homework.adder.service.AdderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdderApplicationTests {

    @Autowired
    private AdderService adderService;

    /**
     * Test case to add a new operation using the adder service.
     * It checks whether the added operation is as expected and whether the operation list is correctly updated.
     */
    @Test
    public void testAddNewOperation() {
        OperationDto input = new OperationDto(10, 20, 30);
        OperationDto result = adderService.addNewOperation(input);

        assertNotNull(result);
        assertNotNull(result.getAddable1());
        assertNotNull(result.getAddable2());
        assertNotNull(result.getSum());

        List<OperationDto> operations = adderService.getOperations();
        assertEquals(1, operations.size());
        assertEquals(Integer.valueOf(input.getAddable1() + input.getAddable2()), operations.get(0).getSum());
    }

    /**
     * Test case to save multiple operations using the adder service and verify the results.
     */
    @Test
    public void testOperationSave() {
        OperationDto input1 = new OperationDto(10, 20, 30);
        OperationDto result1 = adderService.addNewOperation(input1);

        OperationDto input2 = new OperationDto(30, 20, 50);
        OperationDto result2 = adderService.addNewOperation(input2);

        assertNotNull(result1);
        assertNotNull(result1.getAddable1());
        assertEquals(Integer.valueOf(10), Integer.valueOf(result1.getAddable1()));
        assertNotNull(result1.getAddable2());
        assertEquals(Integer.valueOf(20), Integer.valueOf(result1.getAddable2()));

        assertNotNull(result2);
        assertNotNull(result2.getAddable1());
        assertEquals(Integer.valueOf(30), Integer.valueOf(result2.getAddable1()));
        assertNotNull(result2.getAddable2());
        assertEquals(Integer.valueOf(20), Integer.valueOf(result2.getAddable2()));

        List<OperationDto> operations = adderService.getOperations();
        assertEquals(2, operations.size());
        assertEquals(Integer.valueOf(input1.getAddable1() + input1.getAddable2()), operations.get(0).getSum());
        assertEquals(Integer.valueOf(input2.getAddable1() + input2.getAddable2()), operations.get(1).getSum());
    }

    /**
     * Test case to find operations using the adder service by specifying a valid findable value.
     */
    @Test
    public void testFindOperationsWithFindable() {
        adderService.addNewOperation(new OperationDto(10, 20, 30));
        adderService.addNewOperation(new OperationDto(30, 40, 70));

        List<OperationDto> results = adderService.findOperations(true, 70);

        assertEquals(1, results.size());
        Integer found = results.get(0).getSum();
        assertEquals(Integer.valueOf(70), found);
    }

    /**
     * Test case to find operations using the adder service without specifying a findable value.
     */
    @Test
    public void testFindOperationsWithoutFindable() {
        adderService.addNewOperation(new OperationDto(10, 20, 30));
        adderService.addNewOperation(new OperationDto(30, 40, 70));

        List<OperationDto> results = adderService.findOperations(true, null);

        assertEquals(2, results.size());
    }

}
