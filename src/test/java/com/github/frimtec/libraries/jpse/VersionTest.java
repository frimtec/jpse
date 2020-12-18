package com.github.frimtec.libraries.jpse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionTest {

    @Test
    void getMajor() {
        // arrange
        Version version = new Version(5, 1);

        // act
        int major = version.getMajor();

        // assert
        assertThat(major).isEqualTo(5);
    }

    @Test
    void getMinor() {
        // arrange
        Version version = new Version(5, 1);

        // act
        int minor = version.getMinor();

        // assert
        assertThat(minor).isEqualTo(1);
    }

    @Test
    void testToString() {
        // arrange
        Version version = new Version(5, 1);

        // act
        String versionString = version.toString();

        // assert
        assertThat(versionString).isEqualTo("5.1");
    }
}
