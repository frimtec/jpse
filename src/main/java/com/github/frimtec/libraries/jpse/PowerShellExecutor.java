package com.github.frimtec.libraries.jpse;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

/**
 * Executor for PowerShell commands and scripts.
 * <p>
 * Only OS Windows is supported.
 */
public interface PowerShellExecutor {

    /**
     * Executes a single PowerShell command.
     *
     * @param command command
     * @return execution result
     */
    ExecutionResult execute(String command);

    /**
     * Executes a PowerShell script provided as a file path.
     *
     * @param script    file path to the script to execute
     * @param arguments map of named arguments for the script
     * @return execution result
     */
    ExecutionResult execute(Path script, Map<String, String> arguments);

    /**
     * Executes a PowerShell script provided as an input stream.
     *
     * @param script    input stream of the script to execute
     * @param arguments map of named arguments for the script
     * @return execution result
     */
    ExecutionResult execute(InputStream script, Map<String, String> arguments);

    /**
     * Returns the version of the available power shell environment.
     *
     * @return version of the available power shell environment or empty if powershell is not available
     * @since 1.3.0
     */
    Optional<Version> version();

    /**
     * Creates a power shell executor using the default temp path to create and execute temporary scripts.
     *
     * @return power shell executor
     */
    static PowerShellExecutor instance() {
        return instance(null);
    }

    /**
     * Creates a power shell executor using the given temp path to create and execute temporary scripts.
     *
     * @param tempPath path to the temp directory where JPSE can store temporary scripts to be executed
     * @return power shell executor
     */
    static PowerShellExecutor instance(Path tempPath) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("win")) {
            return new WindowsPowerShellExecutor(tempPath);
        } else if (osName.contains("nix") || osName.contains("nux")) {
            return new LinuxPowerShellExecutor(tempPath);
        } else {
            return new UnsupportedOsPowerShellExecutor(osName);
        }
    }
}
