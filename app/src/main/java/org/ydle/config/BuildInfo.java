package org.ydle.config;

/**
 * Created by Jean-Baptiste on 28/01/14.
 */
public class BuildInfo {

    public enum BuildMode {
        Development, Test, Release
    };

    // Build mode
    public final static BuildMode buildMode = BuildMode.Development;

}
