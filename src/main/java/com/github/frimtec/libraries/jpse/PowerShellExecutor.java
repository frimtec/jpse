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
     * @param script file path to the script to execute
     * @param arguments map of named arguments for the script
     * @return execution result
     */
    ExecutionResult execute(Path script, Map<String, String> arguments);

    /**
     * Executes a PowerShell script provided as an input stream.
     *
     * @param script input stream of the script to execute
     * @param arguments map of named arguments for the script
     * @return execution result
     */
    ExecutionResult execute(InputStream script, Map<String, String> arguments);

    static PowerShellExecutor instance() {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().startsWith("windows") ? WindowsPowerShellExecutor.instance() : new UnsupportedOsPowerShellExecutor(osName);
    }
}
