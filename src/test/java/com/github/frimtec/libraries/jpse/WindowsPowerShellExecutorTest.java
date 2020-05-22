package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WindowsPowerShellExecutorTest {

    private final boolean osWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private final PowerShellExecutor executor = osWindows ? new WindowsPowerShellExecutor() : new WindowsPowerShellExecutor("echo");

    @Test
    void execute() throws IOException {
        // act
        ExecutionResult executionResult = executor.execute("Write-Host Hello PowerShell!");

        // assert
        assertThat(executionResult.isSuccess()).isTrue();
        assertThat(executionResult.getStandartOutput()).isEqualTo(osWindows ? "Hello PowerShell!" : "-EncodedCommand VwByAGkAdABlAC0ASABvAHMAdAAgAEgAZQBsAGwAbwAgAFAAbwB3AGUAcgBTAGgAZQBsAGwAIQA=");
    }

    @Test
    void testExecuteForScriptFromFile() throws IOException {
        // arrange
        Map<String, String> arguments = Collections.singletonMap("name", "PowerShell");

        // act
        ExecutionResult executionResult = executor.execute(Paths.get(".\\src\\test\\resources\\test.ps1"), arguments);

        // assert
        assertThat(executionResult.isSuccess()).isTrue();
        assertThat(executionResult.getStandartOutput()).isEqualTo(osWindows ? "Hello PowerShell!" : "-File .\\src\\test\\resources\\test.ps1 -name \"PowerShell\"");
    }

    @Test
    void testExecuteForScriptFromClasspath() throws IOException {
        // arrange
        Map<String, String> arguments = Collections.singletonMap("name", "PowerShell");

        // act
        ExecutionResult executionResult = executor.execute(WindowsPowerShellExecutorTest.class.getResourceAsStream("/test.ps1"), arguments);

        // assert
        assertThat(executionResult.isSuccess()).isTrue();
        if (osWindows) {
            assertThat(executionResult.getStandartOutput()).isEqualTo("Hello PowerShell!");
        } else {
            assertThat(executionResult.getStandartOutput()).matches("^-File /tmp/java-power-shell-(\\d)*.ps1 -name \"PowerShell\"$");
        }
    }
}
