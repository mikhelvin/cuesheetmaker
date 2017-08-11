package csm;

import java.awt.Cursor;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CueTotal extends JPanel {
	
	// COMPONENTS
	private JLabel durationTotal;
	
	
	public CueTotal() {
		initialize();
	}
	
	public void initialize() {
		durationTotal = new JLabel("THIS IS THE TOTAL DURATION");
		durationTotal.setHorizontalAlignment(SwingConstants.CENTER);
		add(durationTotal);
		
	}
	
}
