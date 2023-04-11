package common;

import javax.swing.text.html.Option;
import java.awt.*;
import java.io.*;
import java.util.Optional;

public class FrameState implements Serializable {
    private Dimension m_dimension;
    private boolean m_isMaximum;
    private boolean m_isIcon;

    private FrameState() {
    }

    public FrameState(Dimension dimension, boolean isMaximum, boolean isIcon) {
        m_dimension = dimension;
        m_isMaximum = isMaximum;
        m_isIcon = isIcon;
    }

    public Dimension getDimension() {
        return m_dimension;
    }

    public boolean getIsMaximum() {
        return m_isMaximum;
    }

    public boolean getIsIcon() {
        return m_isIcon;
    }

    public static void writeIntoFile(FrameState state, String fileName) {
        try {
            FileManager.writeIntoFile(state, fileName);
        } catch (IOException exc) {
            failWriting(fileName);
        }
    }

    public static Optional<FrameState> readFromFile(String fileName) {
        try {
            FrameState state = (FrameState) FileManager.readFromFile(fileName);
            return Optional.of(state);
        } catch (IOException | ClassNotFoundException e) {
            failReading(fileName);
        }
        return Optional.empty();
    }

    protected static void failWriting(String fileName) {
        //TODO
        System.err.printf("failWriting %s%n", fileName);
    }

    protected static void failReading(String fileName) {
        //TODO
        System.err.printf("failReading %s%n", fileName);
    }
}
