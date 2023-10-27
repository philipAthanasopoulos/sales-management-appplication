package main.gui;

import main.domain.Associate;

import main.parser.*;

import main.reporter.*;

import java.util.List;

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
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
	private JPanel sidePanel;
	private JTextField searchTextField;
	private List<Associate> associates; 
	private JList<String> associatesList; // change name
	private JFileChooser fileChooser;
	private JLabel associateFileLabel;

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
		final DefaultListModel<String> listModel = new DefaultListModel<String>();
		associates = new ArrayList<Associate>();
		fileChooser = new JFileChooser();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(fileChooser);
		} catch (Exception fileChooserException) {
			fileChooserException.printStackTrace();
		}


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setFocusable(false);
		contentPane.setFocusTraversalKeysEnabled(false);
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(0, 0, getWidth(), getHeight());

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JScrollPane associateFileScrollPane = new JScrollPane();
		associateFileScrollPane.setBounds(763, 208, 456, 471);
		contentPane.add(associateFileScrollPane);
		
		final JTextPane associateFileTextpane = new JTextPane();
		associateFileTextpane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		associateFileTextpane.setEditable(false);
		associateFileScrollPane.setViewportView(associateFileTextpane);
		associateFileTextpane.setCaretPosition(0);
		
		JLabel associateListLabel = new JLabel("Associates");
		associateListLabel.setIconTextGap(10);
		associateListLabel.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-account-24.png")));
		associateListLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		associateListLabel.setBounds(448, 172, 148, 25);
		contentPane.add(associateListLabel);
		
		associateFileLabel = new JLabel("No file to show");
		associateFileLabel.setIconTextGap(10);
		associateFileLabel.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-file-24.png")));
		associateFileLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		associateFileLabel.setBounds(899, 172, 320, 25);
		contentPane.add(associateFileLabel);	
		
		sidePanel = new GradientPanel();
		sidePanel.setBounds(0, 0, 322, getHeight());
		contentPane.add(sidePanel);
		sidePanel.setLayout(null);
		
		JPanel actionsButtonsPanel = new JPanel();
		actionsButtonsPanel.setOpaque(false);
		actionsButtonsPanel.setBounds(0, 169, 322, 267);
		sidePanel.add(actionsButtonsPanel);
		actionsButtonsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
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
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						File selectedFile = fileChooser.getSelectedFile();
						String fileType = Files.probeContentType(selectedFile.toPath());
						System.out.println(fileType);
						ParserFactory parserFactory = new ParserFactory();
						Parser parser = parserFactory.getParser(fileType);
						Associate newAssociate = parser.parseAssociateFromFile(selectedFile);
						associates.add(newAssociate);
						listModel.addElement(newAssociate.getName());
					} catch (NumberFormatException wrongNumberFormat) {
						JOptionPane.showMessageDialog(null, "Some numerical values may be words", "Error", JOptionPane.ERROR_MESSAGE);
					} catch (IOException ioException) {
						JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(null, "File not supported", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		importFileButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		importFileButton.setBorder(null);
		importFileButton.setForeground(new Color(255, 255, 255));
		importFileButton.setBackground(new Color(0, 128, 128));
		importFileButton.setOpaque(false);
		actionsButtonsPanel.add(importFileButton);
		
		JButton addReceiptButton = new JButton("Add receipt");
		addReceiptButton.setFocusable(false);
		addReceiptButton.setFocusTraversalKeysEnabled(false);
		addReceiptButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addReceiptButton.setIconTextGap(10);
		addReceiptButton.setHorizontalTextPosition(SwingConstants.LEFT);
		addReceiptButton.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-add-receipt-24.png")));
		addReceiptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Associate selectedAssociate = associates.get(associatesList.getSelectedIndex());
				ReceiptForm receiptForm = new ReceiptForm(selectedAssociate);
				receiptForm.setVisible(true);
				receiptForm.setLocationRelativeTo(null);
			}
		});
		addReceiptButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		addReceiptButton.setBorder(null);
		addReceiptButton.setForeground(new Color(255, 255, 255));
		addReceiptButton.setBackground(new Color(0, 128, 128));
		addReceiptButton.setOpaque(false);
		actionsButtonsPanel.add(addReceiptButton);
		
		JButton exportFileButton = new JButton("Export as");
		exportFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: make file type pick dynamic so that we dont have to add a new button for each file type
				Associate selectedAssociate = associates.get(associatesList.getSelectedIndex());
				String[] options = {"TXT", "XML"};
				int choice = JOptionPane.showOptionDialog(null, "Choose file type", "Export as", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				ReporterFactory reporterFactory = new ReporterFactory();
				Reporter reporter = reporterFactory.getReporter(options[choice] , selectedAssociate);
				reporter.saveFile();
			}
		});
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
		actionsButtonsPanel.add(exportFileButton);
		
		JLabel appTitleLabel = new JLabel("Sales Management");
		appTitleLabel.setIconTextGap(25);
		appTitleLabel.setIcon(new ImageIcon("C:\\Users\\Philip\\Downloads\\icons8-company-48.png"));
		appTitleLabel.setForeground(new Color(255, 255, 255));
		appTitleLabel.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 22));
		appTitleLabel.setBounds(10, 11, 274, 42);
		sidePanel.add(appTitleLabel);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(new Color(255, 255, 255));
		searchPanel.setBounds(322, 0, 931, 50);
		contentPane.add(searchPanel);
		searchPanel.setLayout(null);
		
		JButton searchButton = new JButton("");
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBorder(null);
		searchButton.setFocusPainted(false);
		searchButton.setFocusTraversalKeysEnabled(false);
		searchButton.setFocusable(false);
		searchButton.setRolloverEnabled(false);
		searchButton.setRequestFocusEnabled(false);
		searchButton.setBackground(new Color(255, 255, 255));
		searchButton.setOpaque(false);
		searchButton.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-search-24.png")));
		searchButton.setBounds(0, 0, 50, 50);
		searchPanel.add(searchButton);
		
		searchTextField = new JTextField();
		searchTextField.setFocusCycleRoot(true);
		searchTextField.setFocusTraversalPolicyProvider(true);
		searchTextField.setBackground(new Color(225, 225, 225));
		searchTextField.setBorder(null);
		searchTextField.setAutoscrolls(false);
		searchTextField.setToolTipText("");
		searchTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		searchTextField.setBounds(50, 0, 881, 50);
		searchPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		JScrollPane associatesScrollPane = new JScrollPane();
		associatesScrollPane.setBounds(346, 208, 369, 471);
		contentPane.add(associatesScrollPane);
		
		associatesList = new JList();
		associatesList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		associatesList.setModel(listModel);
		associatesList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				if (evt.getValueIsAdjusting()) return;
				Associate selectedAssociate = associates.get(associatesList.getSelectedIndex());
				associateFileTextpane.setText(selectedAssociate.getFileString());
				associateFileLabel.setText(selectedAssociate.getPersonalFile().getName());
				if(selectedAssociate.getFileType().equals("text/plain")){
					associateFileLabel.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-text-file-24.png")));
				} else if(selectedAssociate.getFileType().equals("application/xml")){
					associateFileLabel.setIcon(new ImageIcon(AppGUI.class.getResource("/resources/icons8-xml-file-24.png")));
				}

			}
		});
		associatesScrollPane.setViewportView(associatesList);
	}
}
