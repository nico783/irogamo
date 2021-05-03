package myapp.dto;

import myapp.featureorigami.OrigamiResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class OrigamiResponseDtoTest {

    @Test
    void testGetDurationInDays() {
        //arrange
        OrigamiResponseDto origamiResponseDto = new OrigamiResponseDto();
        origamiResponseDto.setDurationInMin(7 * 60);

        // act & assert
        Assertions.assertEquals(BigDecimal.ONE.setScale(2, BigDecimal.ROUND_DOWN), origamiResponseDto.getDurationInDays());
    }

    @Test
    void testGetDurationInDays2() {
        //arrange
        OrigamiResponseDto origamiResponseDto = new OrigamiResponseDto();
        origamiResponseDto.setDurationInMin(60);

        // act & assert
        Assertions.assertEquals(BigDecimal.valueOf(1.0/7.0).setScale(2, BigDecimal.ROUND_DOWN), origamiResponseDto.getDurationInDays());
    }

}