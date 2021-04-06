import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Editor extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor frame = new Editor();
					frame.setVisible(true);
					frame.setTitle("Editor");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public Editor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 370);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textAreaMain = new JTextArea();
		scrollPane.setViewportView(textAreaMain);
		
		//Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JLabel pathLabel = new JLabel("null");  //To temp file path
		menuBar.add(pathLabel);
		pathLabel.setVisible(false);
		
		var fileNew = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewMethod(textAreaMain, pathLabel);
			}
		};

		var fileOpen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenMethod(textAreaMain, pathLabel);
			}
		};
		
		var fileSave = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveMethod(textAreaMain, pathLabel);
			}
		};
		
		var fileSaveAs =  new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveAsMethod(textAreaMain, pathLabel);
			}
		};
		
		JMenu MenuFile = new JMenu("File");
		menuBar.add(MenuFile);
		
		JMenuItem MenuFile_New = new JMenuItem("New");
		MenuFile_New.addActionListener(fileNew);
		MenuFile.add(MenuFile_New);
		
		JMenuItem MenuFile_Open = new JMenuItem("Open File");
		MenuFile_Open.addActionListener(fileOpen);
		MenuFile.add(MenuFile_Open);
		
		JMenuItem MenuFile_Save = new JMenuItem("Save");
		MenuFile_Save.addActionListener(fileSave);
		MenuFile.add(MenuFile_Save);
		
		JMenuItem MenuFile_SaveAs = new JMenuItem("Save As...");
		MenuFile_SaveAs.addActionListener(fileSaveAs);
		MenuFile.add(MenuFile_SaveAs);
		
		JMenu MenuEdit = new JMenu("Edit");
		menuBar.add(MenuEdit);
		
		//ToolBar, button
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton ButtonNew = new JButton("New");
		ButtonNew.addActionListener(fileNew);
		toolBar.add(ButtonNew);
		
		JButton ButtonOpen = new JButton("Open");
		ButtonOpen.addActionListener(fileOpen);
		toolBar.add(ButtonOpen);
		
		JButton ButtonSave = new JButton("Save");
		ButtonSave.addActionListener(fileSave);
		toolBar.add(ButtonSave);
		
		JButton ButtonSaveAs = new JButton("Save As");
		ButtonSaveAs.addActionListener(fileSaveAs);
		toolBar.add(ButtonSaveAs);
	}
	
	private void NewMethod(JTextArea textAreaMain, JLabel pathLabel) {
		if (pathLabel.getText() == "null") {
			if (!textAreaMain.getText().equals("")) {
				int result = JOptionPane.showConfirmDialog(null, "You haven't save yet.\nDo you want to save it?", "Message", JOptionPane.YES_NO_CANCEL_OPTION);
				if (result == JOptionPane.YES_OPTION)
					SaveAsMethod(textAreaMain, pathLabel);
				else if (result == JOptionPane.NO_OPTION) {
					//nothing
				} else
					return;
			}
		} else {
			int result = JOptionPane.showConfirmDialog(null, "You haven't save yet.\nDo you want to save it?", "Message", JOptionPane.YES_NO_CANCEL_OPTION);
			if (result == JOptionPane.YES_OPTION)
				SaveMethod(textAreaMain, pathLabel);
			else if (result == JOptionPane.NO_OPTION) {
				//nothing
			} else
				return;
		}
		pathLabel.setText("null");
		textAreaMain.setText("");
	}
	
	private void OpenMethod(JTextArea textAreaMain, JLabel pathLabel) {
		try {
			if (pathLabel.getText() != "null") {
				File opening = new File(pathLabel.getText());
				if (!Files.readString(opening.toPath()).equals(textAreaMain.getText())) {
					int result = JOptionPane.showConfirmDialog(null, "You haven't save yet.\nDo you want to save it?", "Message", JOptionPane.YES_NO_CANCEL_OPTION);
					if (result == JOptionPane.YES_OPTION)
						SaveMethod(textAreaMain, pathLabel);
					else if (result == JOptionPane.NO_OPTION) {
						//nothing
					} else
						return;
				}
			} else {
				if (!textAreaMain.getText().equals("")) {
					int result = JOptionPane.showConfirmDialog(null, "You haven't save yet.\nDo you want to save it?", "Message", JOptionPane.YES_NO_CANCEL_OPTION);
					if (result == JOptionPane.YES_OPTION)
						SaveAsMethod(textAreaMain, pathLabel);
					else if (result == JOptionPane.NO_OPTION) {
						//nothing
					} else
						return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final String openFolder = "D:\\";
		//fileChooser
		JFileChooser fileChooser = new JFileChooser(openFolder);
		fileChooser.setDialogTitle("Open");
		//set extension
		var extensionFilter = new FileNameExtensionFilter("*.txt*, *.java*", "txt", "java");
		fileChooser.setFileFilter(extensionFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		//open file
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {	//APPROVE_OPTION means return value is "yes" or "OK".
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile.exists()) {
				JOptionPane.showMessageDialog(null, selectedFile.getName() + "\nOpen success", "Message", JOptionPane.CLOSED_OPTION);
				pathLabel.setText(selectedFile.getPath());
				//put file contain in textAreaMain (only work above JAVA 11)
				try {
					textAreaMain.setText(Files.readString(selectedFile.toPath()));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, selectedFile.getName() + "\nCan't find this file.\nPlease enter the correct file name.", "Message", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void SaveMethod(JTextArea textAreaMain, JLabel pathLabel) {
		if (pathLabel.getText() == "null") {
			SaveAsMethod(textAreaMain, pathLabel);
		} else {
			FileWriter fileWriter;
			try {
				var selectedFile = new File(pathLabel.getText());
				fileWriter = new FileWriter(selectedFile);
				fileWriter.write(textAreaMain.getText());
				fileWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void SaveAsMethod(JTextArea textAreaMain, JLabel pathLabel) {
		final String openFolder = "D:\\";
		//fileChooser
		JFileChooser fileChooser = new JFileChooser(openFolder);
		fileChooser.setDialogTitle("Save As");
		//set extension
		var extensionFilter = new FileNameExtensionFilter("*.txt*, *.java*", "txt", "java");
		fileChooser.setFileFilter(extensionFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {	//APPROVE_OPTION means return value is "yes" or "OK".
			File selectedFile = fileChooser.getSelectedFile();
			//if extension not exist
			if (!selectedFile.getAbsolutePath().endsWith(".txt"))
				selectedFile = new File(selectedFile.getAbsoluteFile() + ".txt");
			//file exist or not
			if (selectedFile.exists()) {
				//If exist, call option
				int result = JOptionPane.showConfirmDialog(null, selectedFile.getName() + "\nThis file is already exist.\nDo you want to cover it?", "Message", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {  //option yes
					try {
						FileWriter fileWriter = new FileWriter(selectedFile);
						fileWriter.write(textAreaMain.getText());
						fileWriter.close();
						pathLabel.setText(selectedFile.getPath());
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				} else if (result == JOptionPane.NO_OPTION) {  //option no
					
				}
			} else {
				//If not exist
				try {
					FileWriter fileWriter = new FileWriter(selectedFile);
					fileWriter.write(textAreaMain.getText());
					fileWriter.close();
					pathLabel.setText(selectedFile.getPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
