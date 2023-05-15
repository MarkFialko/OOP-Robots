package statesLoader;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.Optional;

public class InternalFrameState implements State {
    private Dimension m_dimension;
    private boolean m_isMaximum;
    private boolean m_isIcon;
    private int m_locationX;
    private int m_locationY;


    private InternalFrameState() {
    }

    public InternalFrameState(JInternalFrame frame) {
        m_dimension = frame.getSize();
        m_isMaximum = frame.isMaximum();
        m_isIcon = frame.isIcon();
        m_locationX = frame.getX();
        m_locationY = frame.getY();
    }

    public void applyState(JInternalFrame frame) {
        frame.setSize(m_dimension);
        frame.setLocation(m_locationX, m_locationY);
        try {
            frame.setIcon(m_isIcon);
            frame.setMaximum(m_isMaximum);
        } catch (PropertyVetoException e) {
            // attempt to set the property is vetoed
        }

    }

//    public static Optional<InternalFrameState> readFromFile(String fileName) {
//        try {
//            InternalFrameState state = (InternalFrameState) FileManager.readFromFile(fileName);
//            return Optional.of(state);
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.printf("failReading %s%n", fileName);
//        }
//        return Optional.empty();
//    }

}
