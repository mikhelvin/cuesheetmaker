package csm;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.Component;

public class CueSheetMakerWindow {

	// COMPONENTS
	private JFrame frame;
	private JButton addPanelBtn, removePanelBtn, lockAllBtn, exportBtn;
	private JFileChooser chooser;
	private JPanel eastPanel, northpane;
	private JScrollPane scrollPane;

	// COMPONENT SUPPORT
	private ArrayList<CuePanel> cuePanels;
	private Container innerPanel;
	private Dimension btnSize;

	// JAVA OBJECTS & PRIMITIVES
	private FileWriter fw;
	private ArrayList<String> exportedCues;
	private int panelCount, yBase, scroll_height, scroll_width, button_width, button_height;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CueSheetMakerWindow window = new CueSheetMakerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CueSheetMakerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1024, 737);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		varInit();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 840, 107, 0 };
		gridBagLayout.rowHeights = new int[] { 159, 698, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		northpane = new JPanel();
		GridBagConstraints gbc_northpane = new GridBagConstraints();
		gbc_northpane.anchor = GridBagConstraints.SOUTH;
		gbc_northpane.insets = new Insets(0, 0, 5, 5);
		gbc_northpane.gridx = 1;
		gbc_northpane.gridy = 0;
		frame.getContentPane().add(northpane, gbc_northpane);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		innerPanel = new Container();
		scrollPane.setViewportView(innerPanel);

		eastPanel = new JPanel();
		GridBagConstraints gbc_eastPanel = new GridBagConstraints();
		gbc_eastPanel.insets = new Insets(0, 0, 5, 0);
		gbc_eastPanel.fill = GridBagConstraints.BOTH;
		gbc_eastPanel.gridx = 2;
		gbc_eastPanel.gridy = 1;
		frame.getContentPane().add(eastPanel, gbc_eastPanel);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

		addPanelBtn = new JButton("ADD CUE");
		addPanelBtn.setAlignmentY(Component.TOP_ALIGNMENT);
		initButton(addPanelBtn);
		eastPanel.add(addPanelBtn);

		removePanelBtn = new JButton("REMOVE CUE");
		removePanelBtn.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		initButton(removePanelBtn);
		eastPanel.add(removePanelBtn);

		lockAllBtn = new JButton("LOCK ALL");
		lockAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lockAllCues();
			}
		});
		lockAllBtn.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		initButton(lockAllBtn);
		eastPanel.add(lockAllBtn);

		exportBtn = new JButton("EXPORT");
		exportBtn.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		initButton(exportBtn);
		exportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lockAllCues();

				chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				String savefile = null;

				try {
					savefile = f.getAbsolutePath();
				} catch (NullPointerException err) {
					savefile = null;
				}

				if (savefile != null) {
					try {
						fw = new FileWriter(new File(savefile + ".txt"));
						fw.write("Hello world!\tThere was a tab before this...");
						fw.write(System.lineSeparator());
						fw.write("Second write...");
						fw.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		exportBtn.setPreferredSize(new Dimension(button_width, button_height));
		eastPanel.add(exportBtn);
		removePanelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePanel();
			}
		});
		addPanelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPanel();
			}
		});

	} // END INITIALIZE

	private void varInit() {
		scroll_height = 50;
		scroll_width = 550;
		button_width = 125;
		button_height = 35;

		yBase = 10;

		panelCount = 0;

		btnSize = new Dimension(button_width, button_height);
		cuePanels = new ArrayList<CuePanel>();
		exportedCues = new ArrayList<String>();
	}

	private void initButton(JButton button) {

		button.setMinimumSize(btnSize);
		button.setPreferredSize(btnSize);
		button.setMaximumSize(btnSize);
	}

	public void lockAllCues() {
		int i = 0;
		if (panelCount > 0) {
			for (CuePanel cuePanel : cuePanels) {
				cuePanels.get(i).setLock(true);
				i++;
			}
			// JOptionPane.showMessageDialog(null, "Locked " + panelCount + "
			// cues!"); // DEBUG MESSAGE DIALOG
		}
	}

	public void addPanel() {
		int i = panelCount;

		cuePanels.add(i, new CuePanel(i));
		cuePanels.get(i).setBounds(10, yBase, 700, 50);
		innerPanel.add(cuePanels.get(i));
		yBase += 60;
		panelCount++;

		innerPanel.setPreferredSize(new Dimension(scroll_width, scroll_height + yBase));

		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());

		innerPanel.revalidate();
		innerPanel.repaint();
	}

	public void removePanel() {
		int target = panelCount - 1;
		if (target < 0) {
			JOptionPane.showMessageDialog(null, "No cues left!");
		} else {
			innerPanel.remove(cuePanels.get(target));
			cuePanels.remove(target);
			yBase -= 60;
			panelCount--;

			innerPanel.setPreferredSize(new Dimension(scroll_width, scroll_height + yBase));

			JScrollBar vertical = scrollPane.getVerticalScrollBar();
			vertical.setValue(vertical.getMaximum());

			innerPanel.revalidate();
			innerPanel.repaint();
		}
	}
	
	public void buildCue(CuePanel c) {
		//c.
	}
	
	
}
