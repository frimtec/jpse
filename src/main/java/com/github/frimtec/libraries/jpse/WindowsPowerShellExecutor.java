package com.github.frimtec.libraries.jpse;

import java.nio.file.Path;

class WindowsPowerShellExecutor extends AbstractPowerShellExecutor {

    private static final String POWER_SHELL_CMD = "powershell.exe";

    WindowsPowerShellExecutor(Path tempPath) {
        super(tempPath, POWER_SHELL_CMD);
    }

    WindowsPowerShellExecutor(Path tempPath, String executionCommand) {
        super(tempPath, executionCommand);
    }

}
