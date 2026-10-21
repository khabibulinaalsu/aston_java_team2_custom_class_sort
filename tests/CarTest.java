
public class CarTest {
    public static void main(String[] args) {
        testBuilderSetsAllFields();

        testBuilderBrandValidation();
        testBuilderPowerValidation();
        testBuilderYearValidation(); 

        testEqualsReflexiveSymmetric();
        testEqualsRejectsNull();
        testEqualsRejectsDifferentClass();

        testHashCodeEqualForEqualCars();

        testToStringContainsAllFields();

        testComparatorByBrand();
        testComparatorByEnginePower();
        testComparatorByYear();

        TestAssertions.printSummary("CarTest");
    }

    // --- Builder ---
    static void testBuilderSetsAllFields() {
        Car c = Car.builder().brand("BMW").enginePower(200).year(2010).build();
        TestAssertions.assertEquals("BMW", c.getBrand(), "brand");
        TestAssertions.assertEquals(200, c.getEnginePower(), "enginePower");
        TestAssertions.assertEquals(2010, c.getYear(), "year");
    }

    static void testBuilderBrandValidation() {
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> Car.builder().enginePower(100).year(2000).build(),
                "null brand отклоняется");
    }

    static void testBuilderPowerValidation() {
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> Car.builder().brand("A").enginePower(0).year(2000).build(),
                "enginePower=0 отклоняется");
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> Car.builder().brand("A").enginePower(-1).year(2000).build(),
                "enginePower<0 отклоняется");
    }

    static void testBuilderYearValidation() {
        TestAssertions.assertThrows(IllegalArgumentException.class,
                () -> Car.builder().brand("A").enginePower(100).year(1884).build(),
                "year<1885 отклоняется");
    }

    // --- equals ---
    static void testEqualsReflexiveSymmetric() {
        Car a = Car.builder().brand("A").enginePower(100).year(2000).build();
        Car b = Car.builder().brand("A").enginePower(100).year(2000).build();
        Car c = Car.builder().brand("B").enginePower(100).year(2000).build();
        TestAssertions.assertTrue(a.equals(a), "рефлексивность");
        TestAssertions.assertTrue(a.equals(b) && b.equals(a), "симметричность");
        TestAssertions.assertTrue(!a.equals(c), "разные brand -> !equals");
    }

    static void testEqualsRejectsNull() {
        Car a = Car.builder().brand("A").enginePower(100).year(2000).build();
        TestAssertions.assertTrue(!a.equals(null), "equals(null) == false");
    }

    static void testEqualsRejectsDifferentClass() {
        Car a = Car.builder().brand("A").enginePower(100).year(2000).build();
        TestAssertions.assertTrue(!a.equals("A"), "equals(String) == false");
    }

    // --- hashCode ---
    static void testHashCodeEqualForEqualCars() {
        Car a = Car.builder().brand("A").enginePower(100).year(2000).build();
        Car b = Car.builder().brand("A").enginePower(100).year(2000).build();
        TestAssertions.assertEquals(a.hashCode(), b.hashCode(), "hashCode равных объектов");
    }

    // --- toString ---
    static void testToStringContainsAllFields() {
        Car c = Car.builder().brand("BMW").enginePower(200).year(2010).build();
        String s = c.toString();
        TestAssertions.assertTrue(s.contains("BMW"), "toString содержит brand");
        TestAssertions.assertTrue(s.contains("200"), "toString содержит power");
        TestAssertions.assertTrue(s.contains("2010"), "toString содержит year");
    }

    // --- Компараторы ---
    static void testComparatorByBrand() {
        Car a = Car.builder().brand("A").enginePower(1).year(2000).build();
        Car b = Car.builder().brand("B").enginePower(1).year(2000).build();
        TestAssertions.assertTrue(Car.BY_BRAND.compare(a, b) < 0, "BY_BRAND: A < B");
        TestAssertions.assertTrue(Car.BY_BRAND.compare(b, a) > 0, "BY_BRAND: B > A");
        TestAssertions.assertEquals(0, Car.BY_BRAND.compare(a, a), "BY_BRAND: A == A");
    }

    static void testComparatorByEnginePower() {
        Car a = Car.builder().brand("A").enginePower(100).year(2000).build();
        Car b = Car.builder().brand("A").enginePower(200).year(2000).build();
        TestAssertions.assertTrue(Car.BY_ENGINE_POWER.compare(a, b) < 0, "BY_ENGINE_POWER: 100 < 200");
        TestAssertions.assertTrue(Car.BY_ENGINE_POWER.compare(b, a) > 0, "BY_ENGINE_POWER: 200 > 100");
    }

    static void testComparatorByYear() {
        Car a = Car.builder().brand("A").enginePower(1).year(2000).build();
        Car b = Car.builder().brand("A").enginePower(1).year(2020).build();
        TestAssertions.assertTrue(Car.BY_YEAR.compare(a, b) < 0, "BY_YEAR: 2000 < 2020");
        TestAssertions.assertTrue(Car.BY_YEAR.compare(b, a) > 0, "BY_YEAR: 2020 > 2000");
    }
}