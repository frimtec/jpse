package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WindowsPowerShellExecutorTest {
    @Test
    void ctor1() {
        // act
        PowerShellExecutor executor = new WindowsPowerShellExecutor(null);

        // assert
        assertThat(executor).isNotNull();
    }

    @Test
    void ctor2() {
        // act
        PowerShellExecutor executor = new WindowsPowerShellExecutor(null, "");

        // assert
        assertThat(executor).isNotNull();
    }

}
