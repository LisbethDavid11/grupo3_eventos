package Clientes;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;

public class HeaderRenderer extends DefaultTableCellRenderer {
    public HeaderRenderer() {
        setOpaque(true);
        setBackground(Color.decode("#37474f"));
        setForeground(Color.WHITE);
        setFont(getFont().deriveFont(Font.BOLD));
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}

