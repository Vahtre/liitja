package ee.homework.adder.dto;

import ee.homework.adder.entity.Operation;
import lombok.Data;

@Data
public class OperationDto implements Comparable<OperationDto>{
    private Integer addable1;
    private Integer addable2;
    private Integer sum;

    public OperationDto(Operation entity){
        this.addable1 = entity.getAddable1();
        this.addable2 = entity.getAddable2();
        this.sum = entity.getSum();
    }

    public OperationDto(Integer i1, Integer i2, Integer sum){
        this.addable1 = i1;
        this.addable2 = i2;
        this.sum = sum;
    }

    public OperationDto(Integer i1, Integer i2){
        this.addable1 = i1;
        this.addable2 = i2;
    }

    @Override
    public int compareTo(OperationDto other) {
        return Integer.compare(this.sum, other.sum);
    }

}
