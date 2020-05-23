package com.github.frimtec.libraries.jpse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_16LE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

class WindowsPowerShellExecutor implements PowerShellExecutor {

    private static final String POWER_SHELL_CMD = "powershell.exe";
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();

    private static final WindowsPowerShellExecutor INSTANCE = new WindowsPowerShellExecutor();

    private final String executionCommand;

    static WindowsPowerShellExecutor instance() {
        return INSTANCE;
    }

    WindowsPowerShellExecutor() {
        this(POWER_SHELL_CMD);
    }

    WindowsPowerShellExecutor(String executionCommand) {
        this.executionCommand = executionCommand;
    }

    @Override
    public ExecutionResult execute(String command) {
        String encodedCommand = BASE64_ENCODER.encodeToString(command.getBytes(UTF_16LE));
        return execute(createProcessBuilder(Arrays.asList(executionCommand, "-EncodedCommand", encodedCommand)));
    }

    @Override
    public ExecutionResult execute(Path script, Map<String, String> arguments) {
        List<String> commandLine = new ArrayList<>(Arrays.asList(executionCommand, "-File", script.toString()));
        commandLine.addAll(arguments.entrySet()
                .stream()
                .flatMap(entry -> Stream.of("-" + entry.getKey(), "\"" + entry.getValue() + "\""))
                .collect(Collectors.toList()));
        return execute(createProcessBuilder(commandLine));
    }

    @Override
    public ExecutionResult execute(InputStream script, Map<String, String> arguments) {
        try {
            Path tempFile = Files.createTempFile("java-power-shell-", ".ps1");
            try {
                Files.write(tempFile, readStream(script).getBytes(UTF_8), TRUNCATE_EXISTING);
                return execute(tempFile, arguments);
            } finally {
                Files.deleteIfExists(tempFile);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot handle script", e);
        }
    }

    protected ProcessBuilder createProcessBuilder(List<String> commandLine) {
        return new ProcessBuilder(commandLine);
    }

    private ExecutionResult execute(ProcessBuilder processBuilder) {
        try {
            Process process = processBuilder.start();
            return new ExecutionResultImpl(process.waitFor(), readStream(process.getInputStream()), readStream(process.getErrorStream()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Wait for process termination interrupted", e);
        } catch (IOException e) {
            throw new UncheckedIOException("Powershell cannot be executed", e);
        }
    }

    private String readStream(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
