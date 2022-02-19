package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LinuxPowerShellExecutorTest {
    @Test
    void ctor() {
        // act
        PowerShellExecutor executor = new LinuxPowerShellExecutor(null);

        // assert
        assertThat(executor).isNotNull();
    }
}
