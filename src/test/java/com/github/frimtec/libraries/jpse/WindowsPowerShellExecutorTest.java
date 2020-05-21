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
        String output = executor.execute("Write-Host Hello PowerShell!").getResult();

        // assert
        assertThat(output).isEqualTo(osWindows ? "Hello PowerShell!" : "-EncodedCommand VwByAGkAdABlAC0ASABvAHMAdAAgAEgAZQBsAGwAbwAgAFAAbwB3AGUAcgBTAGgAZQBsAGwAIQA=");
    }

    @Test
    void testExecuteForScriptFromFile() throws IOException {
        // arrange
        Map<String, String> arguments = Collections.singletonMap("name", "PowerShell");

        // act
        String output = executor.execute(Paths.get(".\\src\\test\\resources\\test.ps1"), arguments).getResult();

        // assert
        assertThat(output).isEqualTo(osWindows ? "Hello PowerShell!" : "-File .\\src\\test\\resources\\test.ps1 -name \"PowerShell\"");
    }

    @Test
    void testExecuteForScriptFromClasspath() throws IOException {
        // arrange
        Map<String, String> arguments = Collections.singletonMap("name", "PowerShell");

        // act
        String output = executor.execute(WindowsPowerShellExecutorTest.class.getResourceAsStream("/test.ps1"), arguments).getResult();

        // assert
        if (osWindows) {
            assertThat(output).isEqualTo("Hello PowerShell!");
        } else {
            assertThat(output).matches("^-File /tmp/java-power-shell-(\\d)*.ps1 -name \"PowerShell\"$");
        }
    }
}
