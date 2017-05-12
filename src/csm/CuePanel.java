package csm;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.JButton;
import org.eclipse.wb.swing.FocusTraversalOnArray;

public class CuePanel extends JPanel {

	// COMPONENTS
	private JCheckBox lockCheckbox;
	private JLabel cuenum, durationLbl, inLbl, outLbl, durLbl;
	private JButton clearBtn;
	private JTextField titleTf;
	private JTextField inTF1, inTF2, inTF3, inTF4, outTF1, outTF2, outTF3, outTF4;

	// JAVA OBJECTS & PRIMITIVES
	private boolean lock;
	private int cue, duration_mins, duration_secs, duration_total;

	/**
	 * Create the panel.
	 */
	public CuePanel() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		cue = 1;
		initializers(cue);
	}

	public CuePanel(int currentCue) {
		cue = currentCue;
		initializers(cue + 1);
	}

	public void initializers(int currentCue) {
		setBounds(new Rectangle(0, 0, 700, 50));
		setLayout(null);

		cuenum = new JLabel("1m" + Integer.toString(currentCue));
		cuenum.setBounds(5, 4, 35, 14);
		add(cuenum);

		lock = false;

		titleTf = new JTextField();
		titleTf.setColumns(10);
		titleTf.setBounds(51, 0, 203, 20);
		add(titleTf);

		durationLbl = new JLabel("[UNLOCKED]");
		durationLbl.setHorizontalAlignment(SwingConstants.CENTER);
		durationLbl.setBounds(264, 26, 92, 14);
		add(durationLbl);

		inTF1 = new JTextField();
		inTF1.setColumns(10);
		inTF1.setBounds(425, 0, 30, 20);
		add(inTF1);

		inTF2 = new JTextField();
		inTF2.setColumns(10);
		inTF2.setBounds(457, 0, 30, 20);
		add(inTF2);

		inTF4 = new JTextField();
		inTF4.setColumns(10);
		inTF4.setBounds(521, 0, 30, 20);
		add(inTF4);

		inTF3 = new JTextField();
		inTF3.setColumns(10);
		inTF3.setBounds(489, 0, 30, 20);
		add(inTF3);

		outTF1 = new JTextField();
		outTF1.setColumns(10);
		outTF1.setBounds(425, 24, 30, 20);
		add(outTF1);

		outTF2 = new JTextField();
		outTF2.setColumns(10);
		outTF2.setBounds(457, 24, 30, 20);
		add(outTF2);

		outTF3 = new JTextField();
		outTF3.setColumns(10);
		outTF3.setBounds(489, 24, 30, 20);
		add(outTF3);

		outTF4 = new JTextField();
		outTF4.setColumns(10);
		outTF4.setBounds(521, 24, 30, 20);
		add(outTF4);

		outLbl = new JLabel("OUT");
		outLbl.setHorizontalAlignment(SwingConstants.CENTER);
		outLbl.setBounds(366, 26, 49, 14);
		add(outLbl);

		inLbl = new JLabel("IN");
		inLbl.setHorizontalAlignment(SwingConstants.CENTER);
		inLbl.setBounds(365, 4, 50, 14);
		add(inLbl);

		lockCheckbox = new JCheckBox("LOCK");
		lockCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cueLocked();
			}
		});
		lockCheckbox.setBounds(577, 23, 100, 23);
		add(lockCheckbox);

		durLbl = new JLabel("DURATION");
		durLbl.setHorizontalAlignment(SwingConstants.CENTER);
		durLbl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		durLbl.setBounds(264, 4, 92, 14);
		add(durLbl);

		clearBtn = new JButton("x");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		clearBtn.setBounds(577, 0, 100, 19);
		clearBtn.setFocusable(false);
		add(clearBtn);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { cuenum, durationLbl, titleTf, inTF1, inTF2,
				inTF3, inTF4, outTF1, outTF2, outTF3, outTF4, outLbl, lockCheckbox, inLbl, durLbl }));
	}

	public void cueLocked() {
		if (!lock) {
			calculateDuration();
		} else {
			durationLbl.setText("[UNLOCKED]");
			durationLbl.setForeground(Color.BLACK);
		}

		titleTf.setEditable(lock);
		
		inTF1.setEditable(lock);
		inTF2.setEditable(lock);
		inTF3.setEditable(lock);
		inTF4.setEditable(lock);

		outTF1.setEditable(lock);
		outTF2.setEditable(lock);
		outTF3.setEditable(lock);
		outTF4.setEditable(lock);

		lock = !lock;

		lockCheckbox.setSelected(lock);
	}
	
	public void setLock(Boolean bool) {
		lock = !bool;
		cueLocked();
	}

	public void calculateDuration() {

		int outTotal, inTotal;
		int outH, outM, outS;
		int inH, inM, inS;

		try {
			outH = Integer.parseInt(outTF1.getText());
		} catch (NumberFormatException e) {
			outH = 0;
		}
		try {
			outM = Integer.parseInt(outTF2.getText());
		} catch (NumberFormatException e) {
			outM = 0;
		}
		try {
			outS = Integer.parseInt(outTF3.getText());
		} catch (NumberFormatException e) {
			outS = 0;
		}
		outTotal = outH * 3600 + outM * 60 + outS;

		try {
			inH = Integer.parseInt(inTF1.getText());
		} catch (NumberFormatException e) {
			inH = 0;
		}
		try {
			inM = Integer.parseInt(inTF2.getText());
		} catch (NumberFormatException e) {
			inM = 0;
		}
		try {
			inS = Integer.parseInt(inTF3.getText());
		} catch (NumberFormatException e) {
			inS = 0;
		}

		inTotal = inH * 3600 + inM * 60 + inS;

		if (outTotal < inTotal) {
			durationLbl.setText("Error!");
			durationLbl.setForeground(Color.red);
			return;
		}

		durationLbl.setForeground(Color.BLACK);

		duration_total = outTotal - inTotal;
		
		duration_mins = duration_total / 60;
		duration_secs = duration_total - 60 * duration_mins;

		durationLbl.setText(formatDuration(duration_total));
		
	}
	
	public String formatDuration(int dur) {
		String s = null;
		if (duration_secs <= 9) {
			s = Integer.toString(duration_mins) + ":0" + Integer.toString(duration_secs);
		} else {
			s = Integer.toString(duration_mins) + ":" + Integer.toString(duration_secs);
		}
		return s;
	}

	public void reset() {
		titleTf.setText("");

		inTF1.setText("");
		inTF2.setText("");
		inTF3.setText("");
		inTF4.setText("");

		outTF1.setText("");
		outTF2.setText("");
		outTF3.setText("");
		outTF4.setText("");

		titleTf.setEditable(true);
		
		inTF1.setEditable(true);
		inTF2.setEditable(true);
		inTF3.setEditable(true);
		inTF4.setEditable(true);

		outTF1.setEditable(true);
		outTF2.setEditable(true);
		outTF3.setEditable(true);
		outTF4.setEditable(true);

		durationLbl.setText("[UNLOCKED]");
		durationLbl.setForeground(Color.BLACK);

		lock = false;
		lockCheckbox.setSelected(false);
	}

	// START GETTERS AND SETTERS
	
	public JTextField getTitleTf() {
		return titleTf;
	}

	public void setTitleTf(JTextField titleTf) {
		this.titleTf = titleTf;
	}

	public JTextField getInTF1() {
		return inTF1;
	}

	public void setInTF1(JTextField inTF1) {
		this.inTF1 = inTF1;
	}

	public JTextField getInTF2() {
		return inTF2;
	}

	public void setInTF2(JTextField inTF2) {
		this.inTF2 = inTF2;
	}

	public JTextField getInTF3() {
		return inTF3;
	}

	public void setInTF3(JTextField inTF3) {
		this.inTF3 = inTF3;
	}

	public JTextField getInTF4() {
		return inTF4;
	}

	public void setInTF4(JTextField inTF4) {
		this.inTF4 = inTF4;
	}

	public JTextField getOutTF1() {
		return outTF1;
	}

	public void setOutTF1(JTextField outTF1) {
		this.outTF1 = outTF1;
	}

	public JTextField getOutTF2() {
		return outTF2;
	}

	public void setOutTF2(JTextField outTF2) {
		this.outTF2 = outTF2;
	}

	public JTextField getOutTF3() {
		return outTF3;
	}

	public void setOutTF3(JTextField outTF3) {
		this.outTF3 = outTF3;
	}

	public JTextField getOutTF4() {
		return outTF4;
	}

	public void setOutTF4(JTextField outTF4) {
		this.outTF4 = outTF4;
	}

	public int getCue() {
		return cue;
	}

	public void setCue(int cue) {
		this.cue = cue;
	}

	public int getDuration_total() {
		return duration_total;
	}

	public void setDuration_total(int duration_total) {
		this.duration_total = duration_total;
	}
	
	// END GETTERS AND SETTERS

}
