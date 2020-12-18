package com.github.frimtec.libraries.jpse;

import java.nio.file.Path;

class LinuxPowerShellExecutor extends AbstractPowerShellExecutor {

    private static final String POWER_SHELL_CMD = "pwsh";

    LinuxPowerShellExecutor(Path tempPath) {
        super(tempPath, POWER_SHELL_CMD);
    }
}
