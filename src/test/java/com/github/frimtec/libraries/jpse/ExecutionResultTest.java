package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ExecutionResultTest {

    @Test
    void isSuccessForReturnCodeZeroIsTrue() {
        // arrange
        ExecutionResult executionResult = new ExecutionResultImpl(0, "OUTPUT", "ERROR");

        // act
        boolean success = executionResult.isSuccess();

        // assert
        assertThat(success).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 1, -1, -5})
    void isSuccessForReturnCodeNonZeroIsFalse(int returnCode) {
        // arrange
        ExecutionResult executionResult = new ExecutionResultImpl(returnCode, "OUTPUT", "ERROR");

        // act
        boolean success = executionResult.isSuccess();

        // assert
        assertThat(success).isFalse();
    }

    @Test
    void getReturnCode() {
        // arrange
        ExecutionResult executionResult = new ExecutionResultImpl(2, "OUTPUT", "ERROR");

        // act
        int returnCode = executionResult.getReturnCode();

        // assert
        assertThat(returnCode).isEqualTo(2);
    }

    @Test
    void getStandartOutput() {
        // arrange
        ExecutionResult executionResult = new ExecutionResultImpl(2, "OUTPUT", "ERROR");

        // act
        String output = executionResult.getStandartOutput();

        // assert
        assertThat(output).isEqualTo("OUTPUT");
    }

    @Test
    void getErrorOutput() {
        // arrange
        ExecutionResult executionResult = new ExecutionResultImpl(2, "OUTPUT", "ERROR");

        // act
        String output = executionResult.getErrorOutput();

        // assert
        assertThat(output).isEqualTo("ERROR");
    }
}
