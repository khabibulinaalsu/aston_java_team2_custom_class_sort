public class ParserTest {
    public static void main(String[] args) {
        CarParser parser = new CarParser();

        testValidParse(parser);
        testValidParseWithoutPrefix(parser);
        testWrongFieldCount(parser);
        testNonNumericField(parser);
        testBlankInput(parser);
        testNullInput(parser);
        testInvalidValuesGoThroughBuilder(parser);

        TestAssertions.printSummary("ParserTest");
    }

    static void testValidParse(CarParser parser) {
        Car c = parser.parse("Car: BMW, 200, 2010");
        TestAssertions.assertEquals("BMW", c.getBrand(), "brand");
        TestAssertions.assertEquals(200, c.getEnginePower(), "power");
        TestAssertions.assertEquals(2010, c.getYear(), "year");
    }

    static void testValidParseWithoutPrefix(CarParser parser) {
        Car c = parser.parse("Audi, 150, 2015");
        TestAssertions.assertEquals("Audi", c.getBrand(), "brand без префикса");
        TestAssertions.assertEquals(150, c.getEnginePower(), "power без префикса");
        TestAssertions.assertEquals(2015, c.getYear(), "year без префикса");
        
        Car c2 = parser.parse("Lada,80,1990");
        TestAssertions.assertEquals("Lada", c2.getBrand(), "brand без пробелов");
    }

    static void testWrongFieldCount(CarParser parser) {
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse("Car: BMW, 200"), "2 поля -> исключение");
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse("Car: BMW, 200, 2010, extra"), "4 поля -> исключение");
    }

    static void testNonNumericField(CarParser parser) {
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse("Car: BMW, abc, 2010"), "power не число");
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse("Car: BMW, 200, xxxx"), "year не число");
    }

    static void testBlankInput(CarParser parser) {
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse(""), "пустая строка");
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse("   "), "whitespace");
    }

    static void testNullInput(CarParser parser) {
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse(null), "null");
    }

    static void testInvalidValuesGoThroughBuilder(CarParser parser) {
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse("Car: BMW, 100, 1800"), "год <1885 -> билдер бросает IAE, парсер заворачивает в VE");
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse("Car: BMW, 0, 2000"), "мощность 0 -> билдер бросает IAE, парсер заворачивает в VE");
    }
}