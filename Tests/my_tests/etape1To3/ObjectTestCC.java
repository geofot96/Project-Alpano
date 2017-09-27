package my_tests.etape1To3;

public interface ObjectTestCC {
    static boolean hashCodeIsCompatibleWithEquals(Object o1, Object o2) {
        return ! o1.equals(o2) || o1.hashCode() == o2.hashCode();
    }
}
