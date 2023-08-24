package ee.homework.adder.controller;

import ee.homework.adder.dto.OperationDto;
import ee.homework.adder.service.AdderService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/adder")
@Validated
@Slf4j
public class AdderController {

    @Autowired
    private AdderService service;

    @GetMapping(value = "/add/{addable1}/{addable2}")
    @ResponseBody
    public ResponseEntity<OperationDto> addOperation(@PathVariable
                                               @NotNull(message = "Addable1 cannot be empty")
                                               @Max(value=100, message = "Addable1 is greater than 100.")
                                               @Min(value=0, message = "Addable1 is lower than 0.") Integer addable1,
                                               @PathVariable
                                               @NotNull(message = "Addable2 cannot be empty")
                                               @Max(value=100, message = "Addable2 is greater than 100.")
                                               @Min(value=0, message = "Addable2 is lower than 0.") Integer addable2){
        log.info("Started adding new operation");
        OperationDto input = new OperationDto(addable1, addable2);
        OperationDto result = this.service.addNewOperation(input);
        log.info("Returning result to user");

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public ResponseEntity<List<OperationDto>> findOperation(@RequestParam Boolean ascending,
                                                @RequestParam(required = false)
                                                @Max(value=100, message = "Findable is greater than 100.")
                                                @Min(value=0, message = "Findable is lower than 0.") Integer findable){
        log.info("Got request to find Operation(s)");
        List<OperationDto> result = this.service.findOperations(ascending, findable);
        log.info("Returning result to user");

        return ResponseEntity.ok(result);
    }
}
