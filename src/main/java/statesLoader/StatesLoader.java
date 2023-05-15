package statesLoader;

import common.FileManager;
import localization.Locale;
import localization.LocaleApplication;
import localization.Names;

import javax.swing.*;
import java.io.Serializable;
import java.util.Optional;

public class StatesLoader {
    private static final StatesLoader st_loader = new StatesLoader();
    private static final int DO_LOAD_STATES = 1;
    private static final int DO_NOT_LOAD_STATES = 0;
    private static final int NO_ANSWER = -1;

    private static volatile int st_answer = NO_ANSWER;
    private static final Object st_lock = new Object();

    private StatesLoader() {
    }

    public static StatesLoader getInstance() {
        return st_loader;
    }

    public void loadStateIfNecessary(PreservingState instance) {
        if (getAnswer() == DO_LOAD_STATES) {
                Optional<State> readState = readState(instance);
                readState.ifPresent(instance::loadState);
        }
    }

    public void saveState(PreservingState instance) {
        writeState(instance);
    }

    private static int getAnswer() {
        if (st_answer == NO_ANSWER) {
            synchronized (st_lock) {
                if (st_answer == NO_ANSWER) {
                    PreservingState preservingState = LocaleApplication.getInstance();
                    State currentState = preservingState.saveState();
                    Optional<State> readState = readState(preservingState);
                    readState.ifPresent(preservingState::loadState);
                    st_answer = showLoadStateDialog();
                    if (st_answer == DO_NOT_LOAD_STATES && readState.isPresent())
                        preservingState.loadState(currentState);
                }
            }
        }
        return st_answer;
    }

    private static int showLoadStateDialog() {
        int answer = JOptionPane.showOptionDialog(null,
                Names.STATE_DIALOG.getTitle(),
                null,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{Names.YES.getTitle(), Names.NO.getTitle()},
                Names.YES.getTitle());
        if (answer == JOptionPane.YES_OPTION) {
            return DO_LOAD_STATES;
        }
        return DO_NOT_LOAD_STATES;
    }

    private static Optional<State> readState(PreservingState instance) {
        Optional<Serializable> optional = FileManager.readFromFileSafe(instance.getPath().path);
        if (optional.isPresent()) {
            Serializable serializable = optional.get();
            if (serializable instanceof State) {
                return Optional.of((State) serializable);
            }
        }
        return Optional.empty();
    }

    private static void writeState(PreservingState instance) {
        FileManager.writeIntoFileSafe(instance.saveState(), instance.getPath().path);
    }
}
