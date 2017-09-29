package com.feicent.zhang.util.tool;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.feicent.zhang.util.http.HttpUtil;

public class SearchFrame extends JFrame implements ActionListener, DocumentListener {
	
	private static final long serialVersionUID = 295308654627384749L;
	private JLabel phonenumber;
	private JLabel attribution;
	private JLabel operator;
	private JLabel postal;
	private JLabel zone;
	private JTextField phoneField;
	private JTextField attrField;
	private JTextField opeField;
	private JTextField postField;
	private JTextField zoneField;
	private JButton search;

	public SearchFrame() {
		this.setLayout(new FlowLayout());
		phonenumber = new JLabel("手机号");
		attribution = new JLabel("归属地");
		operator = new JLabel("运营商");
		postal = new JLabel("邮    编");
		zone = new JLabel("区    号");
		phoneField = new JTextField(20);
		phoneField.getDocument().addDocumentListener(this);
		attrField = new JTextField(20);
		attrField.setEditable(false);
		opeField = new JTextField(20);
		opeField.setEditable(false);
		postField = new JTextField(20);
		postField.setEditable(false);
		zoneField = new JTextField(20);
		zoneField.setEditable(false);
		search = new JButton("查询");
		search.addActionListener(this);
		this.add(phonenumber);
		this.add(phoneField);
		this.add(attribution);
		this.add(attrField);
		this.add(operator);
		this.add(opeField);
		this.add(postal);
		this.add(postField);
		this.add(zone);
		this.add(zoneField);
		this.add(search);
		this.setTitle("手机归属地查询");
		this.setSize(300, 250);
		this.setLocation(
				(getToolkit().getScreenSize().width - this.getWidth()) / 2,
				(getToolkit().getScreenSize().height - this.getHeight()) / 2);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SearchFrame frame = new SearchFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == search) {
			String phonenumber = phoneField.getText();
			if (phonenumber.equals("") || phonenumber == null) {
				JOptionPane.showMessageDialog(this, "请输入手机号，至少前7位");
				return;
			} 
			
			String url = "http://haoma.imobile.com.cn/index.php?mob=" + phonenumber;
			String info= HttpUtil.readContentFromURL(url);
			Parser parser = new Parser(new Lexer(info));
			try {
				NodeFilter filter = new TagNameFilter("td");
				NodeList list = parser.extractAllNodesThatMatch(filter);
				if (list.elementAt(0) != null) {
					attrField.setText(list.elementAt(1).getChildren()
							.elementAt(0).getText());
					opeField.setText(list.elementAt(2).getChildren()
							.elementAt(0).getText());
					postField.setText(list.elementAt(3).getChildren()
							.elementAt(0).getText());
					zoneField.setText(list.elementAt(4).getChildren()
							.elementAt(0).getText());
				} else {
					JOptionPane.showMessageDialog(this, "没有查到相关信息");
				}
			} catch (ParserException e1) {

			}
		}
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		
	}
	
	@Override
	public void removeUpdate(DocumentEvent e) {
		if (phoneField.getText() == null || phoneField.getText().equals("")) {
			attrField.setText("");
			opeField.setText("");
			postField.setText("");
			zoneField.setText("");
		}
	}
}
