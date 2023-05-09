package common;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class WindowState implements Serializable {
    private Dimension m_dimension;
    private Dimension m_maximumSize;
    private boolean m_isMaximumSizeSet;
    private int m_locationX;
    private int m_locationY;

    private WindowState() {}
    public WindowState(Window window) {
        m_dimension = window.getSize();
        m_isMaximumSizeSet = window.isMaximumSizeSet();
        if (m_isMaximumSizeSet)
            m_maximumSize = window.getMaximumSize();
        m_locationX = window.getX();
        m_locationY = window.getY();
    }

    public void applyState(Window window) {
        if (m_isMaximumSizeSet)
            window.setMaximumSize(m_maximumSize);
        window.setSize(m_dimension);
        window.setLocation(m_locationX, m_locationY);
    }
}
