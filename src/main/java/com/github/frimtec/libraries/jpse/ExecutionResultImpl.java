package com.github.frimtec.libraries.jpse;

final class ExecutionResultImpl implements ExecutionResult {

    private final int returnCode;
    private final String result;
    private final String error;

    ExecutionResultImpl(int returnCode, String result, String error) {
        this.returnCode = returnCode;
        this.result = result;
        this.error = error;
    }

    @Override
    public boolean isSuccess() {
        return returnCode == 0;
    }

    @Override
    public String getStandartOutput() {
        return this.result;
    }

    @Override
    public int getReturnCode() {
        return this.returnCode;
    }

    @Override
    public String getErrorOutput() {
        return this.error;
    }

}
