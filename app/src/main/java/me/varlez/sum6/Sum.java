package me.varlez.sum6;

public class Sum {

    public int compute(Object[] values) {
        int sum = 0;

        for (Object value : values) {
            if (value instanceof Integer) {
                sum += (Integer) value;
            }
        }

        return sum;
    }
}
