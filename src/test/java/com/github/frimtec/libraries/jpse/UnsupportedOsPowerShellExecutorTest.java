package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnsupportedOsPowerShellExecutorTest {

    private final PowerShellExecutor executor = new UnsupportedOsPowerShellExecutor("Other");

    @Test
    void execute() {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> this.executor.execute("command"));
        assertThat(exception.getMessage()).isEqualTo("Not supported on OS Other");
    }

    @Test
    void testExecuteForScriptFromFile() {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> this.executor.execute(Paths.get("file.ps1"), Collections.emptyMap()));
        assertThat(exception.getMessage()).isEqualTo("Not supported on OS Other");
    }

    @Test
    void testExecuteForScriptFromClasspath() {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> this.executor.execute(UnsupportedOsPowerShellExecutorTest.class.getResourceAsStream("/test.ps1"), Collections.emptyMap()));
        assertThat(exception.getMessage()).isEqualTo("Not supported on OS Other");
    }

    @Test
    void version() {
        Optional<Version> version = this.executor.version();
        assertThat(version).isEmpty();
    }
}

