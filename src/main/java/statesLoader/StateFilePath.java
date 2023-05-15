package statesLoader;

import java.nio.file.Path;

public enum StateFilePath {
    GAME_FRAME(Path.of(BasePath.path.toString(), "gameframe")),
    LOG_FRAME(Path.of(BasePath.path.toString(), "logframe")),
    LOCALIZATION(Path.of(BasePath.path.toString(), "localization")),
    MAIN_WINDOW(Path.of(BasePath.path.toString(), "mainwindow"));

    private static class BasePath {
        private static final Path path = Path.of("src", "main", "resources", "states");
    }

    public final Path path;

    StateFilePath(Path path) {
        this.path = path;
    }
}
