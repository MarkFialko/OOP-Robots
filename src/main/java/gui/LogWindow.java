package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import localization.Localization;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener
{
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) 
    {
        super(Localization.getString("workProtocol"), true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        addClosingConfirmation();
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    private void addClosingConfirmation() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                handleClosing();
            }
        });
    }

    private void handleClosing() {
        int answer = DialogBeforeClosing.showWarningMessage(this);
        switch (answer) {
            case JOptionPane.YES_OPTION:
                dispose();
                break;
            case JOptionPane.NO_OPTION:
                break;
        }
    }
}
