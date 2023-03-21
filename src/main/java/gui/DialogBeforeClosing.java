package gui;

import localization.Localization;

import javax.swing.*;
import java.awt.*;

public class DialogBeforeClosing {
    public static int showWarningMessage(Component parentComponent) {
        String[] buttonLabels = new String[]{
                Localization.getString("yes"),
                Localization.getString("no")
        };
        String defaultOption = buttonLabels[0];
        Icon icon = null;

        return JOptionPane.showOptionDialog(parentComponent,
                Localization.getString("closeDialog"),
                Localization.getString("warning"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);
    }
}
