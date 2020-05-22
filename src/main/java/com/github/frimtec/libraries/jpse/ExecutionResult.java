package com.github.frimtec.libraries.jpse;

/**
 * Result of PowerShell execution.
 * @see PowerShellExecutor
 */
public interface ExecutionResult {
    /**
     * Returns whether the execution has a non error return code of 0 or not.
     * @return true: if the return code was 0; false: otherwise
     */
    boolean isSuccess();

    /**
     * Returns the executions return code.
     * @return return code
     */
    int getReturnCode();

    /**
     * Returns the executions standart output.
     * @return standart output
     */
    String getStandartOutput();

    /**
     * Returns the executions error output.
     * @return error output
     */
    String getErrorOutput();
}
