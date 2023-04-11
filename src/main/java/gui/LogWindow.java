package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyChangeEvent;

import javax.swing.*;

import common.ComparingHelpers;
import localization.Names;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends InternalFrame
{
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) 
    {
        super(Names.WORK_PROTOCOL, true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
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
    public void propertyChange(PropertyChangeEvent evt) {
        if (ComparingHelpers.areEqual(m_logSource, evt.getSource())) {
            if (ComparingHelpers.areEqual(LogWindowSource.PROPERTY_LOG, evt.getPropertyName())) {
                onLogChanged();
            }
        } else {
            super.propertyChange(evt);
        }
    }

    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
