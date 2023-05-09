package gui;

import common.*;
import localization.Locale;
import localization.LocaleApplication;
import localization.Names;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

public class Adapter implements WindowListener, InternalFrameListener {

    private static volatile int st_loadState = JOptionPane.CANCEL_OPTION;
    private static final Object st_loadStateLock = new Object();
    private final StateFilePath m_path;
    Adapter(StateFilePath path)
    {
        m_path = path;
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

    private static int showLoadStateDialog() {
        if (st_loadState == JOptionPane.CANCEL_OPTION) {
            synchronized (st_loadStateLock){
                if (st_loadState == JOptionPane.CANCEL_OPTION) {
                    Locale currentLocale = null;
                    Optional<Serializable> optional = FileManager.readFromFileSafe(StateFilePath.LOCALIZATION.path);
                    if(optional.isPresent()) {
                        LocalizationState state = (LocalizationState) optional.get();
                        currentLocale = LocaleApplication.getInstance().getCurrentLocale();
                        state.applyState();
                    }
                    st_loadState = JOptionPane.showOptionDialog(null,
                            Names.STATE_DIALOG.getTitle(),
                            null,
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            new String[]{Names.YES.getTitle(), Names.NO.getTitle()},
                            Names.YES.getTitle());
                    if (st_loadState != JOptionPane.YES_OPTION && currentLocale != null) {
                        LocaleApplication.getInstance().setLocale(currentLocale);
                    }
                }
            }
        }
        return st_loadState;
    }


    @Override
    public final void windowOpened(WindowEvent e) {
        WindowState state = null;
        Optional<Serializable> optional = FileManager.readFromFileSafe(m_path.path);
        if (optional.isPresent())
        {
            state = (WindowState) optional.get();
        }
        if (state != null) {
            switch (showLoadStateDialog()) {
                case JOptionPane.YES_OPTION: {
                    Window window = e.getWindow();
                    state.applyState(window);
                    break;
                }
                case JOptionPane.NO_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    break;
            }
        }
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
        Window window = e.getWindow();
        if (window != null) {
            WindowState state = new WindowState(window);
            FileManager.writeIntoFileSafe(state, m_path.path);
        }
        FileManager.writeIntoFileSafe(new LocalizationState(LocaleApplication.getInstance()), StateFilePath.LOCALIZATION.path);
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
    public final void internalFrameOpened(InternalFrameEvent e) {
        Optional<Serializable> optional = FileManager.readFromFileSafe(m_path.path);
        if (optional.isPresent()) {
            InternalFrameState state = (InternalFrameState) optional.get();
            switch (showLoadStateDialog()) {
                case JOptionPane.YES_OPTION: {
                    JInternalFrame frame = e.getInternalFrame();
                    state.applyState(frame);
                    break;
                }
                case JOptionPane.NO_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    break;
            }
        }
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
        JInternalFrame frame = e.getInternalFrame();
        if (frame != null) {
            InternalFrameState state = new InternalFrameState(frame);
            FileManager.writeIntoFileSafe(state, m_path.path);
        }
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
