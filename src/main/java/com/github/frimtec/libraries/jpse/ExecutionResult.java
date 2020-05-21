package com.github.frimtec.libraries.jpse;

public interface ExecutionResult {
    boolean isSuccess();

    String getResult();

    int getReturnCode();

    String getError();
}
