package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class WindowsPowerShellExecutorTest {

    private final boolean osWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private final PowerShellExecutor executor = this.osWindows ? new WindowsPowerShellExecutor(null) : new WindowsPowerShellExecutor(null, "echo");

    @Test
    void executeForSuccess() throws IOException {
        // act
        ExecutionResult executionResult = this.executor.execute("Write-Host Hello PowerShell!");

        // assert
        assertThat(executionResult.isSuccess()).isTrue();
        assertThat(executionResult.getStandartOutput()).isEqualTo(this.osWindows ? "Hello PowerShell!" : "-EncodedCommand VwByAGkAdABlAC0ASABvAHMAdAAgAEgAZQBsAGwAbwAgAFAAbwB3AGUAcgBTAGgAZQBsAGwAIQA=");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void executeForFailure() throws IOException {
        // act
        ExecutionResult executionResult = this.executor.execute("Write-Error Problem");

        // assert
        assertThat(executionResult.isSuccess()).isFalse();
        assertThat(executionResult.getReturnCode()).isEqualTo(1);
        assertThat(executionResult.getStandartOutput()).isEmpty();
        assertThat(executionResult.getErrorOutput()).contains("Problem");
    }

    @Test
    void executeForInterruptThrowsException() throws IOException {
        // arrange
        Thread mainThread = Thread.currentThread();
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                fail("Test was interrupted");
            }
            mainThread.interrupt();
        }).start();

        //noinspection MethodDoesntCallSuperMethod
        PowerShellExecutor interruptedExecutor = this.osWindows ? this.executor : new WindowsPowerShellExecutor(null, "") {
            @Override
            protected ProcessBuilder createProcessBuilder(List<String> commandLine) {
                return new ProcessBuilder("sleep", "2");
            }
        };

        // act & assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> interruptedExecutor.execute("Start-Sleep -Seconds 2"));
        assertThat(exception.getMessage()).contains("Wait for process termination interrupted");
    }

    @Test
    void executeForBadPowerShellExeThrowsException() throws IOException {
        // arrange
        PowerShellExecutor errorExecutor = new WindowsPowerShellExecutor(null, "unknown_command.exe");

        // act & assert
        UncheckedIOException exception = assertThrows(UncheckedIOException.class, () -> errorExecutor.execute("Write-Host any"));
        assertThat(exception.getMessage()).contains("Powershell cannot be executed");
    }

    @Test
    void testExecuteForScriptFromFile() throws IOException {
        // arrange
        Map<String, String> arguments = Collections.singletonMap("name", "PowerShell");

        // act
        ExecutionResult executionResult = this.executor.execute(Paths.get(".\\src\\test\\resources\\test.ps1"), arguments);

        // assert
        assertThat(executionResult.isSuccess()).isTrue();
        assertThat(executionResult.getStandartOutput()).isEqualTo(this.osWindows ? "Hello PowerShell!" : "-File .\\src\\test\\resources\\test.ps1 -name \"PowerShell\"");
    }

    @Test
    void testExecuteForScriptFromClasspath() throws IOException {
        // arrange
        Map<String, String> arguments = Collections.singletonMap("name", "PowerShell");

        // act
        ExecutionResult executionResult = this.executor.execute(WindowsPowerShellExecutorTest.class.getResourceAsStream("/test.ps1"), arguments);

        // assert
        assertThat(executionResult.isSuccess()).isTrue();
        if (this.osWindows) {
            assertThat(executionResult.getStandartOutput()).isEqualTo("Hello PowerShell!");
        } else {
            assertThat(executionResult.getStandartOutput()).matches("^-File /tmp/java-power-shell-(\\d)*.ps1 -name \"PowerShell\"$");
        }
    }

    @Test
    void createForNonExistingTempFolderExpectsFolderCreated(@TempDir Path tempBaseFolder) throws IOException {
        // arrange
        Path tempPath = tempBaseFolder.resolve("jpse");

        // act
        PowerShellExecutor executor = new WindowsPowerShellExecutor(tempPath);

        // assert
        assertThat(Files.exists(tempPath)).isTrue();
    }
}
