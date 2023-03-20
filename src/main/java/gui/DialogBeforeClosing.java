package gui;

import javax.swing.*;
import java.awt.*;

public class DialogBeforeClosing {
    public static int showWarningMessage(Component parentComponent) {
        String[] buttonLabels = new String[]{"Yes", "No"};
        String defaultOption = buttonLabels[0];
        Icon icon = null;

        return JOptionPane.showOptionDialog(parentComponent,
                "Are you sure you want to close?",
                "Warning",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);
    }
}
