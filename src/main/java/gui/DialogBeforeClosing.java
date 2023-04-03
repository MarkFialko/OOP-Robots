package gui;

import localization.LocaleApplication;
import localization.Names;

import javax.swing.*;
import java.awt.*;

public class DialogBeforeClosing {
    public static int showWarningMessage(Component parentComponent) {
        String[] buttonLabels = new String[]{
                Names.YES.getTitle(),
                Names.NO.getTitle()
        };
        String defaultOption = buttonLabels[0];
        Icon icon = null;

        return JOptionPane.showOptionDialog(parentComponent,
                Names.CLOSE_DIALOG.getTitle(),
                Names.WARNING.getTitle(),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);
    }
}
