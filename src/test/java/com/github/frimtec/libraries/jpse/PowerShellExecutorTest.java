package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PowerShellExecutorTest {

    @Test
    void instance() {
        PowerShellExecutor instance = PowerShellExecutor.instance();
        assertThat(instance).isNotNull();
    }
}
