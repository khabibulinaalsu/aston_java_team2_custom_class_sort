package aston_java_team2_custom_class_sort.tests;

import java.util.Objects;

public final class TestAssertions {
    private static int passed = 0;
    private static int failed = 0;

    private TestAssertions() {}

    public static void assertEquals(Object expected, Object actual, String msg) {
        if (Objects.equals(expected, actual)) {
            passed++;
        } else {
            failed++;
            System.err.println("FAILED: " + msg + " | expected=" + expected + ", actual=" + actual);
        }
    }

    public static void assertTrue(boolean condition, String msg) {
        if (condition) { passed++; }
        else { failed++; System.err.println("FAILED: " + msg); }
    }

    public static void assertThrows(Class<? extends Throwable> expected,
                                    Runnable action, String msg) {
        try {
            action.run();
            failed++;
            System.err.println("FAILED: " + msg + " | expected " + expected.getSimpleName() + ", nothing thrown");
        } catch (Throwable t) {
            if (expected.isInstance(t)) { passed++; }
            else {
                failed++;
                System.err.println("FAILED: " + msg + " | expected " + expected.getSimpleName()
                        + ", got " + t.getClass().getSimpleName());
            }
        }
    }

    public static void printSummary(String suite) {
        System.out.println("[" + suite + "] passed=" + passed + ", failed=" + failed);
        if (failed > 0) throw new AssertionError(suite + ": " + failed + " test(s) failed");
    }
}