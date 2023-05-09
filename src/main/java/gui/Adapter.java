package gui;

import localization.Names;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Adapter implements WindowListener, InternalFrameListener {

    private static int showClosingDialog() {
        String[] buttonLabels = new String[]{
                Names.YES.getTitle(),
                Names.NO.getTitle()
        };
        return JOptionPane.showOptionDialog(null,
                Names.CLOSE_DIALOG.getTitle(),
                Names.WARNING.getTitle(),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                buttonLabels,
                buttonLabels[0]);
    }

    @Override
    public final void windowOpened(WindowEvent e) {

    }

    @Override
    public final void windowClosing(WindowEvent e) {
        int answer = showClosingDialog();
        switch (answer) {
            case JOptionPane.YES_OPTION: {
                Window window = e.getWindow();
                if (window != null)
                    window.dispose();
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
                JInternalFrame internalFrame = e.getInternalFrame();
                if (internalFrame != null)
                    internalFrame.dispose();
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
