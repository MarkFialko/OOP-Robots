package statesLoader;

public interface PreservingState {
    StateFilePath getPath();
    State saveState();
    void loadState(State state);
}
