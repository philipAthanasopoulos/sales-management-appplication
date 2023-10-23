package main.gui;

import main.domain.Entry;
import main.parser.Parser;
import main.parser.TXTParser;
import main.parser.XMLParser;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.DebugGraphics;
import javax.swing.DefaultListModel;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Cursor;

/**
 * The App class is the main class of the application. It is responsible for
 * creating the GUI and handling the user's input.
 * @important The GUI is currently made with WindowBuilder extension for Eclipse.
 * Because of this, the code has autogenerated parts and some names are not
 * very descriptive. Make sure to change them when make changes to the GUI.
 */
public class AppGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String applicationName = "Sales Commissions Application";
	private JPanel panel_1;
	private JTextField textField;
	private List<Entry> entries; 
	private JList list;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppGUI frame = new AppGUI();
					frame.setTitle(frame.applicationName);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	


	/**
	 * Create the frame.
	 */
	public AppGUI() {
		entries = new ArrayList<Entry>();
		final DefaultListModel<String> associateListModel = new DefaultListModel<>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1269, 746);
		contentPane = new JPanel();
		contentPane.setFocusable(false);
		contentPane.setFocusTraversalKeysEnabled(false);
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(763, 208, 456, 471);
		contentPane.add(scrollPane);
		
		final JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		textPane.setCaretPosition(0);
		
		JLabel lblNewLabel = new JLabel("Associates");
		lblNewLabel.setIconTextGap(10);
		lblNewLabel.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-account-24.png")));
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		lblNewLabel.setBounds(448, 172, 148, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Receipts");
		lblNewLabel_1.setIconTextGap(10);
		lblNewLabel_1.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-receipt-24.png")));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(926, 172, 163, 25);
		contentPane.add(lblNewLabel_1);	
		
		JPanel panel = new JPanel();
		panel_1 = new GradientPanel();
		panel_1.setBounds(0, 0, 322, 707);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setBounds(0, 169, 322, 267);
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton importFileButton = new JButton("Import file");
		importFileButton.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		importFileButton.setFocusable(false);
		importFileButton.setFocusPainted(false);
		importFileButton.setFocusTraversalKeysEnabled(false);
		importFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		importFileButton.setIconTextGap(10);
		importFileButton.setHorizontalTextPosition(SwingConstants.LEFT);
		importFileButton.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-add-file-24.png")));
		importFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String fileType = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
					Parser parser;
					if (fileType.equals("txt")) parser = new TXTParser();
					else if (fileType.equals("xml")) parser = new XMLParser();
					else throw new IllegalArgumentException("Unsupported file type");
					try {
						Entry entry = parser.parseFileEntry(selectedFile);
						entries.add(entry);
						associateListModel.addElement(entry.getAssociate().getName());
					} catch (Exception unsupportedFileTypeException) {
						JOptionPane.showMessageDialog(null, "Invalid file type", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		importFileButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		importFileButton.setBorder(null);
		importFileButton.setForeground(new Color(255, 255, 255));
		importFileButton.setBackground(new Color(0, 128, 128));
		importFileButton.setOpaque(false);
		panel_2.add(importFileButton);
		
		JButton addReceiptButton = new JButton("Add receipt");
		addReceiptButton.setFocusable(false);
		addReceiptButton.setFocusTraversalKeysEnabled(false);
		addReceiptButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addReceiptButton.setIconTextGap(10);
		addReceiptButton.setHorizontalTextPosition(SwingConstants.LEFT);
		addReceiptButton.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-add-receipt-24.png")));
		addReceiptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Entry selectedEntry = entries.get(list.getSelectedIndex());
				ReceiptForm receiptForm = new ReceiptForm(selectedEntry);
				receiptForm.setVisible(true);
				//position to center
				receiptForm.setLocationRelativeTo(null);
			}
		});
		addReceiptButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		addReceiptButton.setBorder(null);
		addReceiptButton.setForeground(new Color(255, 255, 255));
		addReceiptButton.setBackground(new Color(0, 128, 128));
		addReceiptButton.setOpaque(false);
		panel_2.add(addReceiptButton);
		
		JButton exportFileButton = new JButton("Export as");
		exportFileButton.setFocusable(false);
		exportFileButton.setFocusTraversalKeysEnabled(false);
		exportFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exportFileButton.setIconTextGap(10);
		exportFileButton.setHorizontalTextPosition(SwingConstants.LEFT);
		exportFileButton.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-export-24.png")));
		exportFileButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		exportFileButton.setBorder(null);
		exportFileButton.setForeground(new Color(255, 255, 255));
		exportFileButton.setBackground(new Color(0, 128, 128));
		exportFileButton.setOpaque(false);
		panel_2.add(exportFileButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.setFocusTraversalKeysEnabled(false);
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(0, 128, 128));
		btnNewButton_1.setOpaque(false);
		panel_2.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("Sales Management");
		lblNewLabel_2.setIconTextGap(25);
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\Philip\\Downloads\\icons8-company-48.png"));
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 22));
		lblNewLabel_2.setBounds(10, 11, 274, 42);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBounds(322, 0, 931, 50);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_4.setBorder(null);
		btnNewButton_4.setFocusPainted(false);
		btnNewButton_4.setFocusTraversalKeysEnabled(false);
		btnNewButton_4.setFocusable(false);
		btnNewButton_4.setRolloverEnabled(false);
		btnNewButton_4.setRequestFocusEnabled(false);
		btnNewButton_4.setBackground(new Color(255, 255, 255));
		btnNewButton_4.setOpaque(false);
		btnNewButton_4.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-search-24.png")));
		btnNewButton_4.setBounds(0, 0, 50, 50);
		panel_3.add(btnNewButton_4);
		
		textField = new JTextField();
		textField.setFocusCycleRoot(true);
		textField.setFocusTraversalPolicyProvider(true);
		textField.setBackground(new Color(225, 225, 225));
		textField.setBorder(null);
		textField.setAutoscrolls(false);
		textField.setToolTipText("");
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setBounds(50, 0, 881, 50);
		panel_3.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(346, 208, 369, 471);
		contentPane.add(scrollPane_1);
		
		list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 18));
		list.setModel(associateListModel);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				if (evt.getValueIsAdjusting()) return;
				int index = list.getSelectedIndex();
				if (index == -1) return;
				Entry entry = entries.get(index);
				textPane.setText(entry.getFileAsString());
			}
		});

		scrollPane_1.setViewportView(list);
		
		
	}
}