package csm;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class CueSheetMakerWindow {

	private JFrame frame;
	private JScrollPane scrollPane;

	private ArrayList<JPanel> cuePanels = new ArrayList<JPanel>();

	private int panelCount, yBase;
	private JButton addPanelBtn;
	private JButton removePanelBtn;
	private Container innerPanel;

	private int scroll_height, scroll_width, button_width, button_height;
	private JPanel eastPanel;
	private JPanel northpane;
	private JButton exportBtn;

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
		frame.setBounds(100, 100, 850, 737);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		scroll_height = 50;
		scroll_width = 550;
		button_width = 125;
		button_height = 35;

		yBase = 10;

		panelCount = 0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 640, 107, 0 };
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

		addPanelBtn = new JButton("ADD CUE");
		addPanelBtn.setPreferredSize(new Dimension(button_width, button_height));
		eastPanel.add(addPanelBtn);

		removePanelBtn = new JButton("REMOVE CUE");
		removePanelBtn.setPreferredSize(new Dimension(button_width, button_height));
		eastPanel.add(removePanelBtn);

		exportBtn = new JButton("EXPORT");
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

	public void addPanel() {
		int i = panelCount;

		cuePanels.add(i, new CuePanel(i));
		cuePanels.get(i).setBounds(10, yBase, 560, 50);
		innerPanel.add(cuePanels.get(i));
		yBase += 60;
		panelCount++;

		innerPanel.setPreferredSize(new Dimension(scroll_width, scroll_height + yBase));

		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());

		innerPanel.getParent().revalidate();
		innerPanel.revalidate();
		innerPanel.repaint();
	}

	public void removePanel() {
		int target = panelCount - 1;
		if (target < 0) {
			JOptionPane.showMessageDialog(null, "No cues left!");
		} else {
			// frame.getContentPane().remove(cuePanels.get(target));
			innerPanel.remove(cuePanels.get(target));
			cuePanels.remove(target);
			yBase -= 60;
			panelCount--;

			innerPanel.setPreferredSize(new Dimension(scroll_width, scroll_height + yBase));

			JScrollBar vertical = scrollPane.getVerticalScrollBar();
			vertical.setValue(vertical.getMaximum());

			innerPanel.getParent().revalidate();
			innerPanel.revalidate();
			innerPanel.repaint();
		}
	}
}
