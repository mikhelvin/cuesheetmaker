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
	private JTextField titleTf;
	private JTextField inTF1;
	private JTextField inTF2;
	private JTextField inTF4;
	private JTextField inTF3;
	private JTextField outTF1;
	private JTextField outTF2;
	private JTextField outTF3;
	private JTextField outTF4;

	private JCheckBox lockCheckbox;

	private JLabel cuenum, durationLbl, inLbl, outLbl;

	private boolean lock;

	int in1, in2, in3, in4, out1, out2, out3, out4, duration_mins, duration_total, hoursDif, minsDif, secsDif;
	private JLabel durLbl;
	private JButton clearBtn;

	/**
	 * Create the panel.
	 */
	public CuePanel() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		initializers(1);
	}

	public CuePanel(int currentCue) {
		initializers(currentCue + 1);
	}

	public void initializers(int currentCue) {
		setBounds(new Rectangle(0, 0, 560, 50));
		setLayout(null);

		cuenum = new JLabel("1m" + Integer.toString(currentCue));
		cuenum.setBounds(0, 0, 41, 14);
		add(cuenum);

		// safe init ints to 0
		in1 = 0;
		in2 = 0;
		in3 = 0;
		in4 = 0;
		out1 = 0;
		out2 = 0;
		out3 = 0;
		out4 = 0;
		lock = false;

		titleTf = new JTextField();
		titleTf.setColumns(10);
		titleTf.setBounds(51, 0, 184, 20);
		add(titleTf);

		durationLbl = new JLabel("[UNLOCKED]");
		durationLbl.setHorizontalAlignment(SwingConstants.LEFT);
		durationLbl.setBounds(251, 26, 76, 14);
		add(durationLbl);

		inTF1 = new JTextField();
		inTF1.setColumns(10);
		inTF1.setBounds(358, 0, 25, 20);
		add(inTF1);

		inTF2 = new JTextField();
		inTF2.setColumns(10);
		inTF2.setBounds(384, 0, 25, 20);
		add(inTF2);

		inTF4 = new JTextField();
		inTF4.setColumns(10);
		inTF4.setBounds(437, 0, 25, 20);
		add(inTF4);

		inTF3 = new JTextField();
		inTF3.setColumns(10);
		inTF3.setBounds(411, 0, 25, 20);
		add(inTF3);

		outTF1 = new JTextField();
		outTF1.setColumns(10);
		outTF1.setBounds(358, 24, 25, 20);
		add(outTF1);

		outTF2 = new JTextField();
		outTF2.setColumns(10);
		outTF2.setBounds(384, 24, 25, 20);
		add(outTF2);

		outTF3 = new JTextField();
		outTF3.setColumns(10);
		outTF3.setBounds(411, 24, 25, 20);
		add(outTF3);

		outTF4 = new JTextField();
		outTF4.setColumns(10);
		outTF4.setBounds(437, 24, 25, 20);
		add(outTF4);

		outLbl = new JLabel("OUT");
		outLbl.setHorizontalAlignment(SwingConstants.CENTER);
		outLbl.setBounds(331, 26, 25, 14);
		add(outLbl);

		inLbl = new JLabel("IN");
		inLbl.setHorizontalAlignment(SwingConstants.CENTER);
		inLbl.setBounds(330, 4, 25, 14);
		add(inLbl);

		lockCheckbox = new JCheckBox("LOCK");
		lockCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cueLocked();
			}
		});
		lockCheckbox.setBounds(485, 23, 57, 23);
		add(lockCheckbox);

		durLbl = new JLabel("DURATION");
		durLbl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		durLbl.setBounds(254, 4, 76, 14);
		add(durLbl);
		
		clearBtn = new JButton("x");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		clearBtn.setBounds(485, 0, 41, 19);
		clearBtn.setFocusable(false);
		add(clearBtn);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{cuenum, titleTf, durationLbl, inTF1, inTF2, inTF3, inTF4, outTF1, outTF2, outTF3, outTF4, outLbl, lockCheckbox, inLbl, durLbl}));
	}

	public void cueLocked() {
		if (!lock) {
			// TODO: FIX DURATION CALCULATION
			//durationLbl.setText(inTF1.getText() + ":" + inTF2.getText() + ":" + inTF3.getText() + ":" + inTF4.getText());
			calculateDuration();
		} else {
			durationLbl.setText("[UNLOCKED]");
			durationLbl.setForeground(Color.BLACK);
		}

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
		duration_total -= 60 * duration_mins;
		
		
		if (duration_total <= 9) {
			durationLbl.setText(Integer.toString(duration_mins) + ":0" + Integer.toString(duration_total));
		} else {
			durationLbl.setText(Integer.toString(duration_mins) + ":" + Integer.toString(duration_total));
		}
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
}
