package ee.homework.adder.service;

import ee.homework.adder.dto.OperationDto;
import ee.homework.adder.entity.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdderService {

    //Data holder
    private static List<Operation> operations = new ArrayList<>();

    public AdderService(List<Operation> initialOperations) {
        operations = new ArrayList<>(initialOperations);
    }
    //thread-safety
    //Ensures that only one thread can enter block of code
    private final Object operationsLock = new Object();

    /**
     * Creates new Operation object in storage
     * @param input OperationDto which creates new Operation object
     * @return dto of created Operation object
     */
    public OperationDto addNewOperation(OperationDto input) {
        synchronized (operationsLock) {
            log.info("Adding new Operation");
            Operation operation = new Operation();
            operation.setAddable1(input.getAddable1());
            operation.setAddable2(input.getAddable2());
            Integer sum = input.getAddable1() + input.getAddable2();
            operation.setSum(sum);
            operations.add(operation);
            OperationDto result = new OperationDto(operation);
            log.info("Added new Operation object: {}",  result);

            return result;
        }


    }

    /**
     * returns Operation objects as OperationDto List that has one of addables or sum equal to input.findable(if provided)
     * and sorted ascending or descending based on ascending boolean value
     * @param ascending sorting direction, required
     *        findable number in range [0, 100], optional
     * @return OperationDto List of filtered and sorted Operation objects
     */
    public List<OperationDto> findOperations(boolean ascending, Integer findable) {
        synchronized (operationsLock) {
            log.info("Finding Operators with query params: {}", ascending, findable);
            List<OperationDto> result;

            if (findable != null && findable >= 0 && findable <= 100) {
                //Filtering results based on input Integer
                result = operations.stream()
                        .filter(o -> o.getAddable1().equals(findable) ||
                                o.getAddable2().equals(findable) ||
                                o.getSum().equals(findable))
                        .map(OperationDto::new)
                        .collect(Collectors.toList());
            } else {
                //Creating return List when searchable Integer is not provided
                result = operations.stream()
                        .map(OperationDto::new)
                        .collect(Collectors.toList());
            }

            //Sorting result
            log.info("Sorting results: {}", result);
            if (ascending) {
                //Ascending
                Collections.sort(result);
            } else {
                //Descending
                Collections.sort(result, Collections.reverseOrder());
            }

            log.info("Returning results: {}", result);
            return result;
        }
    }



    public List<OperationDto> getOperations() {
        synchronized (operationsLock) {
            return operations.stream().map(OperationDto::new).collect(Collectors.toList());
        }
    }
}
