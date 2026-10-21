public class ParserTest {
    public static void main(String[] args) {
        CarParser parser = new CarParser();

        testValidParse(parser);
        testWrongFieldCount(parser);
        testNonNumericField(parser);
        testBlankInput(parser);
        testNullInput(parser);
        testInvalidValuesGoThroughBuilder(parser);
        testWrongPrefix(parser); // Добавил тест на неверный префикс

        TestAssertions.printSummary("ParserTest");
    }

    static void testValidParse(CarParser parser) {
        // Обратите внимание на формат: "Car: brand, power, year"
        Car c = parser.parse("Car: BMW, 200, 2010");
        TestAssertions.assertEquals("BMW", c.getBrand(), "brand");
        TestAssertions.assertEquals(200, c.getEnginePower(), "power");
        TestAssertions.assertEquals(2010, c.getYear(), "year");
    }

    static void testWrongFieldCount(CarParser parser) {
        TestAssertions.assertThrows(ValidationException.class, // <-- Ждем ValidationException
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

    static void testWrongPrefix(CarParser parser) {
        TestAssertions.assertThrows(ValidationException.class,
                () -> parser.parse("BMW, 200, 2010"), "без префикса 'Car: ' -> ошибка");
    }
}