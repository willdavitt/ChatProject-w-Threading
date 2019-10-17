import java.awt.Dimension;

import javax.swing.*;
public class GUI {
	private JFrame frame;
	private JPanel southPanel;
	private JPanel panel;
	private JPanel masterPanel;
	private JTextPane text;
	private JTextArea entry;
	 //I started to make a gui and this is as far as I got with implementing it.
	public GUI() {
		 frame = new JFrame("Server");
		 southPanel = new JPanel();
		 panel = new JPanel();
		 masterPanel = new JPanel();
		 
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		
		masterPanel.setLayout(new BoxLayout(masterPanel,BoxLayout.PAGE_AXIS));
		text = new JTextPane();
		text.setText("this is new");
		JScrollPane scroll = new JScrollPane(text);
		scroll.setBorder(BorderFactory.createTitledBorder("Console"));
		scroll.setPreferredSize(new Dimension(0,400));
		text.setPreferredSize(new Dimension(500,25));
		text.setEditable(false);
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		masterPanel.add(scroll);
		southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.LINE_AXIS));
		entry = new JTextArea();
		entry.setRows(1);
		southPanel.add(entry);
		southPanel.add(new JButton("Exit"));
		southPanel.setPreferredSize(new Dimension(0,25));
		
		masterPanel.add(southPanel);
		frame.add(masterPanel);
		frame.setPreferredSize(new Dimension(500,500));
		frame.setSize(500,500);
		frame.setVisible(true);
	}
	public void displayMessage(String message) {
		text.setText(text.getText()+ "\n" + message);
		
	}
}
