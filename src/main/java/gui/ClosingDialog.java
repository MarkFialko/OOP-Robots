package gui;

import localization.Names;

import javax.swing.*;

public class ClosingDialog {
    private static final String[] st_buttonLabels;
    private static final String st_defaultOption;
    private static final Icon st_icon;

    static {
        st_buttonLabels = new String[] {
                Names.YES.getTitle(),
                Names.NO.getTitle()
        };
        st_defaultOption = st_buttonLabels[0];
        st_icon = null;
    }

    public static void confirm(ClosableComponent closable) {
        int answer = showOptionDialog();
        switch (answer) {
            case JOptionPane.YES_OPTION:
                closable.onClose();
                break;
            case JOptionPane.NO_OPTION:
            case JOptionPane.CLOSED_OPTION:
                break;
        }
    }

    private static int showOptionDialog() {
        return JOptionPane.showOptionDialog(null,
                Names.CLOSE_DIALOG.getTitle(),
                Names.WARNING.getTitle(),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                st_icon,
                st_buttonLabels,
                st_defaultOption);
    }
}
