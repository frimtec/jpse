package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LinuxPowerShellExecutorTest {
    @Test
    void ctor() {
        // act
        PowerShellExecutor executor = new LinuxPowerShellExecutor(null);

        // assert
        assertThat(executor).isNotNull();
    }
}
