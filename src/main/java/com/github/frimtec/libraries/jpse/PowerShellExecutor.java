package com.github.frimtec.libraries.jpse;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

/**
 * Executor for PowerShell commands and scripts.
 * <p>
 * Windows, Linux and macOS are supported.
 */
public interface PowerShellExecutor {

    /**
     * Executes a single PowerShell command.
     * <p>
     * This method takes a PowerShell command as a string and executes it in the PowerShell environment.
     * The command is encoded and passed to PowerShell using the -EncodedCommand parameter, which allows
     * for complex commands with special characters to be executed correctly.
     *
     * @param command the PowerShell command to execute as a string
     * @return the result of the execution containing return code, standard output, and error output
     * @throws java.io.UncheckedIOException if PowerShell cannot be executed or if there's an I/O error
     */
    ExecutionResult execute(String command);

    /**
     * Executes a PowerShell script provided as a file path.
     * <p>
     * This method takes a path to a PowerShell script file (.ps1) and executes it in the PowerShell environment.
     * The script is executed using the -File parameter, and any provided arguments are passed to the script
     * as named parameters (e.g., -ParamName Value).
     * 
     * @param script    file path to the PowerShell script (.ps1) to execute
     * @param arguments map of named arguments for the script, where keys are parameter names (without the leading dash)
     *                  and values are parameter values
     * @return the result of the execution containing return code, standard output, and error output
     * @throws java.io.UncheckedIOException if PowerShell cannot be executed or if there's an I/O error
     */
    ExecutionResult execute(Path script, Map<String, String> arguments);

    /**
     * Executes a PowerShell script provided as an input stream.
     * <p>
     * This method takes an input stream containing PowerShell script content and executes it in the PowerShell environment.
     * The script content is first written to a temporary file, which is then executed using the -File parameter.
     * Any provided arguments are passed to the script as named parameters (e.g., -ParamName Value).
     * The temporary file is automatically deleted after execution.
     * 
     * @param script    input stream containing the PowerShell script content to execute
     * @param arguments map of named arguments for the script, where keys are parameter names (without the leading dash)
     *                  and values are parameter values
     * @return the result of the execution containing return code, standard output, and error output
     * @throws java.io.UncheckedIOException if PowerShell cannot be executed, if there's an I/O error, or if the script cannot be handled
     */
    ExecutionResult execute(InputStream script, Map<String, String> arguments);

    /**
     * Returns the version of the available PowerShell environment.
     * <p>
     * This method queries the PowerShell environment to determine its version (major and minor).
     * It executes a PowerShell command that retrieves the version information from the $PSVersionTable variable.
     * If PowerShell is not available or an error occurs during execution, an empty Optional is returned.
     *
     * @return an Optional containing the Version object with major and minor version numbers of the PowerShell environment,
     *         or an empty Optional if PowerShell is not available or an error occurs
     * @since 1.3.0
     */
    Optional<Version> version();

    /**
     * Creates a PowerShell executor using the default temp path to create and execute temporary scripts.
     * <p>
     * This factory method creates an appropriate PowerShell executor implementation based on the current operating system.
     * It uses the system's default temporary directory for storing temporary script files.
     * <p>
     * The method automatically selects:
     * - WindowsPowerShellExecutor for Windows systems
     * - LinuxPowerShellExecutor for Linux and macOS systems
     * - UnsupportedOsPowerShellExecutor for unsupported operating systems
     *
     * @return a PowerShell executor implementation appropriate for the current operating system
     */
    static PowerShellExecutor instance() {
        return instance(null);
    }

    /**
     * Creates a PowerShell executor using the given temp path to create and execute temporary scripts.
     * <p>
     * This factory method creates an appropriate PowerShell executor implementation based on the current operating system,
     * using the specified directory for storing temporary script files.
     * <p>
     * The method automatically selects:
     * - WindowsPowerShellExecutor for Windows systems
     * - LinuxPowerShellExecutor for Linux and macOS systems
     * - UnsupportedOsPowerShellExecutor for unsupported operating systems
     * <p>
     * If the specified temp directory does not exist, it will be created. If creation fails,
     * an UncheckedIOException will be thrown.
     *
     * @param tempPath path to the temp directory where JPSE can store temporary scripts to be executed,
     *                 or null to use the system's default temporary directory
     * @return a PowerShell executor implementation appropriate for the current operating system
     * @throws java.io.UncheckedIOException if the specified temp directory does not exist and cannot be created
     */
    static PowerShellExecutor instance(Path tempPath) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("win")) {
            return new WindowsPowerShellExecutor(tempPath);
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            return new LinuxPowerShellExecutor(tempPath);
        } else {
            return new UnsupportedOsPowerShellExecutor(osName);
        }
    }
}
