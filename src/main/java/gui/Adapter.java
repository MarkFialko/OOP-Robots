package gui;

import common.*;
import localization.Locale;
import localization.LocaleApplication;
import localization.Names;
import statesLoader.InternalFrameState;
import statesLoader.LocalizationState;
import statesLoader.StateFilePath;
import statesLoader.WindowState;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.util.Optional;

public class Adapter implements WindowListener, InternalFrameListener {

    private static volatile int st_loadState = JOptionPane.CANCEL_OPTION;
    private static final Object st_loadStateLock = new Object();
    Adapter()
    {

    }

    private static int showClosingDialog() {
        return JOptionPane.showOptionDialog(null,
                Names.CLOSE_DIALOG.getTitle(),
                Names.WARNING.getTitle(),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{Names.YES.getTitle(), Names.NO.getTitle()},
                Names.YES.getTitle());
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public final void windowClosing(WindowEvent e) {
        switch (showClosingDialog()) {
            case JOptionPane.YES_OPTION: {
                Window window = e.getWindow();
                if (window != null) {
                    window.dispose();
                }
                break;
            }
            case JOptionPane.NO_OPTION:
            case JOptionPane.CLOSED_OPTION:
                break;
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {

    }

    @Override
    public final void internalFrameClosing(InternalFrameEvent e) {
        int answer = showClosingDialog();
        switch (answer) {
            case JOptionPane.YES_OPTION: {
                JInternalFrame frame = e.getInternalFrame();
                if (frame != null) {
                    frame.dispose();
                }
                break;
            }
            case JOptionPane.NO_OPTION:
            case JOptionPane.CLOSED_OPTION:
                break;
        }
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {

    }
}
