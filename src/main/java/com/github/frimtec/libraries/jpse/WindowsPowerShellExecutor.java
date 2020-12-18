package com.github.frimtec.libraries.jpse;

import java.nio.file.Path;

class WindowsPowerShellExecutor extends AbstractPowerShellExecutor {

    private static final String POWER_SHELL_CMD = "powershell.exe";
    private static final String PARAM_QUOTE = "\"";

    WindowsPowerShellExecutor(Path tempPath) {
        super(tempPath, POWER_SHELL_CMD, PARAM_QUOTE);
    }

    WindowsPowerShellExecutor(Path tempPath, String executionCommand) {
        super(tempPath, executionCommand, PARAM_QUOTE);
    }

}
