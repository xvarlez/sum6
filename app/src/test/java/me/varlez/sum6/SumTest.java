package me.varlez.sum6;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


public class SumTest {

    @Test
    public void compute() throws Exception {
        Sum sum = new Sum();
        assertThat(sum.compute(1, 2)).isEqualTo(3);
    }

}