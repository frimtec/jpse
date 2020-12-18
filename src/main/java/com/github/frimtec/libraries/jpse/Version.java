package com.github.frimtec.libraries.jpse;

/**
 * Power shell version.
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
     * Major version.
     *
     * @return major
     */
    public int getMajor() {
        return this.major;
    }

    /**
     * Minor version.
     *
     * @return major
     */
    public int getMinor() {
        return this.minor;
    }

    /**
     * Version string in the format "{@code <major>.<minor>}".
     *
     * @return version string
     */
    @Override
    public String toString() {
        return String.format("%d.%d", this.major, this.minor);
    }
}
