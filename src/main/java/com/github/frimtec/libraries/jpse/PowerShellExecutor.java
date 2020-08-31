package com.github.frimtec.libraries.jpse;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

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
     * Creates a power shell executor using the default temp path to create and execute temporary scripts.
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
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().startsWith("windows") ? new WindowsPowerShellExecutor(tempPath) : new UnsupportedOsPowerShellExecutor(osName);
    }
}
