package com.github.frimtec.libraries.jpse;

/**
 * Represents a PowerShell version with major and minor components.
 * <p>
 * This class encapsulates the version information of a PowerShell environment
 * consisting of major and minor version numbers. It provides methods to access
 * these components individually and to get a formatted version string.
 *
 * @see PowerShellExecutor#version()
 * @since 1.3.0
 */
public final class Version {
    private final int major;
    private final int minor;

    Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    /**
     * Returns the major version number.
     * <p>
     * The major version is the first component in the version string (e.g., in "5.1", "5" is the major version).
     *
     * @return the major version number
     */
    public int getMajor() {
        return this.major;
    }

    /**
     * Returns the minor version number.
     * <p>
     * The minor version is the second component in the version string (e.g., in "5.1", "1" is the minor version).
     *
     * @return the minor version number
     */
    public int getMinor() {
        return this.minor;
    }

    /**
     * Returns a string representation of this version in the format "major.minor".
     * <p>
     * This method formats the version as a string with the major and minor components
     * separated by a period (e.g., "5.1" for major=5, minor=1).
     *
     * @return the formatted version string in the format "major.minor"
     */
    @Override
    public String toString() {
        return String.format("%d.%d", this.major, this.minor);
    }
}
