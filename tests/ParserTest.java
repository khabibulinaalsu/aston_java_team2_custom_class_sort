public class ParserTest {
    public static void main(String[] args) {
        CarParser parser = new CarParser();

        testValidParse(parser);
        testWrongFieldCount(parser);
        testNonNumericField(parser);
        testBlankInput(parser);
        testNullInput(parser);
        testInvalidValuesGoThroughBuilder(parser);

        TestAssertions.printSummary("ParserTest");
    }

    static void testValidParse(CarParser parser) {
        Car c = parser.parse("BMW, 200, 2010");
        TestAssertions.assertEquals("BMW", c.getBrand(), "brand");
        TestAssertions.assertEquals(200, c.getEnginePower(), "power");
        TestAssertions.assertEquals(2010, c.getYear(), "year");
    }

    static void testWrongFieldCount(CarParser parser) {
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse("BMW,200"), "2 поля -> исключение");
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse("BMW,200,2010,extra"), "4 поля -> исключение");
    }

    static void testNonNumericField(CarParser parser) {
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse("BMW,abc,2010"), "power не число");
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse("BMW,200,xxxx"), "year не число");
    }

    static void testBlankInput(CarParser parser) {
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse(""), "пустая строка");
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse("   "), "whitespace");
    }

    static void testNullInput(CarParser parser) {
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse(null), "null");
    }

    static void testInvalidValuesGoThroughBuilder(CarParser parser) {
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse("BMW,100,1800"), "год <1885 -> билдер бросает IAE");
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse("BMW,0,2000"), "мощность 0 -> билдер бросает IAE");
    }
}