package pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SpecialPriceTest {

    @ParameterizedTest
    @MethodSource("validSpecialPrice")
    public void validSpecialPriceConstructs(SpecialPrice specialPrice, int amountNeeded, int price) {
        assertEquals(amountNeeded, specialPrice.getAmountNeeded());
        assertEquals(price, specialPrice.getPrice());
    }

    @ParameterizedTest
    @MethodSource("invalidSpecialPrice")
    public void invalidSpecialPriceDoesNotConstruct(int amountNeeded, int price) {
        assertThrows(IllegalArgumentException.class, () -> new SpecialPrice(amountNeeded, price));
    }

    private static Stream<Arguments> validSpecialPrice() {
        return Stream.of(
                // SpecialPrice, Expected AmountNeeded, Expected Price
                Arguments.of(new SpecialPrice(2, 1), 2, 1),
                Arguments.of(new SpecialPrice(100, 5), 100, 5),
                Arguments.of(new SpecialPrice(Integer.MAX_VALUE, Integer.MAX_VALUE), Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
    }

    private static Stream<Arguments> invalidSpecialPrice() {
        return Stream.of(
                // AmountNeeded, Price
                Arguments.of(0, 0),
                Arguments.of(0, 1),
                Arguments.of(1, 0),
                Arguments.of(1, 1),
                Arguments.of(-1, -1),
                Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE)
        );
    }
}