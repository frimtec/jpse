package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class AbstractPowerShellExecutorTest {

    private final PowerShellExecutor executor = PowerShellExecutor.instance();

    @Test
    void executeForSuccess() {
        // act
        ExecutionResult executionResult = this.executor.execute("Write-Host Hello PowerShell!");

        // assert
        assertThat(executionResult.isSuccess()).isTrue();
        assertThat(executionResult.getStandardOutput()).isEqualTo("Hello PowerShell!");
    }

    @Test
    void executeForFailure() {
        // act
        ExecutionResult executionResult = this.executor.execute("Write-Error Problem");

        // assert
        assertThat(executionResult.isSuccess()).isFalse();
        assertThat(executionResult.getReturnCode()).isEqualTo(1);
        assertThat(executionResult.getStandardOutput()).isEmpty();
        assertThat(executionResult.getErrorOutput()).contains("Problem");
    }

    @Test
    void executeForInterruptThrowsException() {
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

        // act & assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> this.executor.execute("Start-Sleep -Seconds 2"));
        assertThat(exception.getMessage()).contains("Wait for process termination interrupted");
    }

    @Test
    void executeForBadPowerShellExeThrowsException() {
        // arrange
        PowerShellExecutor errorExecutor = new WindowsPowerShellExecutor(null, "unknown_command.exe");

        // act & assert
        UncheckedIOException exception = assertThrows(UncheckedIOException.class, () -> errorExecutor.execute("Write-Host any"));
        assertThat(exception.getMessage()).contains("Powershell cannot be executed");
    }

    @Test
    void testExecuteForScriptFromFile() {
        // arrange
        Map<String, String> arguments = Collections.singletonMap("name", "PowerShell");

        // act
        ExecutionResult executionResult = this.executor.execute(Paths.get(".\\src\\test\\resources\\test.ps1"), arguments);

        // assert
        assertThat(executionResult.isSuccess()).isTrue();
        assertThat(executionResult.getStandardOutput()).isEqualTo("Hello PowerShell!");
    }

    @Test
    void testExecuteForScriptFromClasspath() {
        // arrange
        Map<String, String> arguments = Collections.singletonMap("name", "Power - 'Shell'");

        // act
        ExecutionResult executionResult = this.executor.execute(AbstractPowerShellExecutorTest.class.getResourceAsStream("/test.ps1"), arguments);

        // assert
        assertThat(executionResult.isSuccess()).isTrue();
        assertThat(executionResult.getStandardOutput()).isEqualTo("Hello Power - 'Shell'!");
    }

    @Test
    void createForNonExistingTempFolderExpectsFolderCreated(@TempDir Path tempBaseFolder) {
        // arrange
        Path tempPath = tempBaseFolder.resolve("jpse");

        // act
        new WindowsPowerShellExecutor(tempPath);

        // assert
        assertThat(Files.exists(tempPath)).isTrue();
    }

    @Test
    void version() {
        // act
        Version version = this.executor.version().orElseThrow(() -> {
            throw new AssertionError("result expected");
        });

        // assert
        assertThat(version.toString()).matches("(\\d+)\\.(\\d+)");
    }

    @Test
    void versionWithError() {
        // arrange
        PowerShellExecutor executor = new WindowsPowerShellExecutor(null, "badCommand");

        // act
        Optional<Version> version = executor.version();

        // assert
        assertThat(version).isEmpty();
    }
}
