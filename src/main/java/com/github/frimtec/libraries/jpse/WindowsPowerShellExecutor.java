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

    private static final int INITIAL_STREAM_BUFFER_SIZE = 1000;
    private static final String JPSE_GLOBBER_THREAD_NAME = "JPSE-Gobbler";

    private final String executionCommand;
    private final Path tempPath;

    WindowsPowerShellExecutor(Path tempPath) {
        this(tempPath, POWER_SHELL_CMD);
    }

    WindowsPowerShellExecutor(Path tempPath, String executionCommand) {
        this.tempPath = tempPath;
        this.executionCommand = executionCommand;
        if (tempPath != null && !Files.exists(tempPath)) {
            try {
                Files.createDirectories(tempPath);
            } catch (IOException e) {
                throw new UncheckedIOException("Cannot create given temp folder: " + tempPath.toAbsolutePath(), e);
            }
        }
    }

    @Override
    public ExecutionResult execute(String command) {
        String encodedCommand = BASE64_ENCODER.encodeToString(command.getBytes(UTF_16LE));
        return execute(createProcessBuilder(Arrays.asList(this.executionCommand, "-EncodedCommand", encodedCommand)));
    }

    @Override
    public ExecutionResult execute(Path script, Map<String, String> arguments) {
        List<String> commandLine = new ArrayList<>(Arrays.asList(this.executionCommand, "-File", script.toString()));
        commandLine.addAll(arguments.entrySet()
                .stream()
                .flatMap(entry -> Stream.of("-" + entry.getKey(), "\"" + entry.getValue() + "\""))
                .collect(Collectors.toList()));
        return execute(createProcessBuilder(commandLine));
    }

    @Override
    public ExecutionResult execute(InputStream script, Map<String, String> arguments) {
        try {
            String prefix = "java-power-shell-";
            String suffix = ".ps1";
            Path tempFile = this.tempPath != null ? Files.createTempFile(this.tempPath, prefix, suffix) : Files.createTempFile(prefix, suffix);
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
        StringWriter outputStringWriter = new StringWriter(INITIAL_STREAM_BUFFER_SIZE);
        StringWriter errorStringWriter = new StringWriter(INITIAL_STREAM_BUFFER_SIZE);
        PrintWriter outputBuffer = new PrintWriter(outputStringWriter);
        PrintWriter errorBuffer = new PrintWriter(errorStringWriter);
        try {
            Process process = processBuilder.start();
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), outputBuffer::println);
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), errorBuffer::println);
            List<Thread> threads = Arrays.asList(
                    new Thread(outputGobbler, JPSE_GLOBBER_THREAD_NAME),
                    new Thread(errorGobbler, JPSE_GLOBBER_THREAD_NAME)
            );
            threads.forEach(thread -> {
                thread.setDaemon(true);
                thread.start();
            });
            for (StreamGobbler gobbler : Arrays.asList(outputGobbler, errorGobbler)) {
                gobbler.waitTillFinished();
            }
            return new ExecutionResultImpl(process.waitFor(), getOutputFromWriter(outputStringWriter), getOutputFromWriter(errorStringWriter));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Wait for process termination interrupted", e);
        } catch (IOException e) {
            throw new UncheckedIOException("Powershell cannot be executed", e);
        }
    }

    private String getOutputFromWriter(StringWriter outputStringWriter) {
        return outputStringWriter.getBuffer().toString().trim();
    }

    private String readStream(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
