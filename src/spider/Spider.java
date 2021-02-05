package spider;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
@SuppressWarnings("unused")
public class Spider extends Thread {
	private JFrame jf=new JFrame("ץȡ�������д�");
	private JPanel jp=new JPanel();
	private JLabel wangzhi=new JLabel("������ַ");
	private JTextField wangzhiTxt=new JTextField();
	private JButton paqu1=new JButton("��ȡ");
	private JButton paqu2=new JButton("��ȡ��ַ��");
	private JLabel wenben=new JLabel("��ҳ�ı�");
	private JTextArea wenbenTxt=new JTextArea();
	private JScrollPane jsp_wenben=new JScrollPane(wenbenTxt);
	private JLabel yuanma=new JLabel("HTMLԴ��");
	private JTextArea yuanmaTxt=new JTextArea();
	private JScrollPane jsp_yuanma=new JScrollPane(yuanmaTxt);
	private JLabel minganku=new JLabel("���дʿ�");
	private JButton daoru=new JButton("�������п�");
	private JTextArea minganci=new JTextArea();
	private JScrollPane jsp_minganci=new JScrollPane(minganci);
	private JButton pipei=new JButton("ƥ�����п�");
	private ArrayList<String>MinganWords=new ArrayList<String>();//�洢���д�

	public Spider() {
    	jf.setSize(1030,700);
    	jf.setLocation(400,200);
    	wangzhi.setBounds(10,10,80,50);
    	wangzhiTxt.setBounds(90,10,500,50);
    	paqu1.setBounds(590,10,60,50);
    	paqu2.setBounds(650,10,100,50);
    	yuanma.setBounds(10,60,80,50);
    	jsp_yuanma.setBounds(10,110,400,500);
		jsp_yuanma.setViewportView(yuanmaTxt);
		jsp_yuanma.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	wenben.setBounds(410,60,80,50);
    	jsp_wenben.setBounds(410,110,400,500);
		jsp_wenben.setViewportView(wenbenTxt);
		jsp_wenben.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	minganku.setBounds(810,60,80,50);
    	jsp_minganci.setBounds(810,110,200,400);
		jsp_minganci.setViewportView(minganci);
		jsp_minganci.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	daoru.setBounds(810,510,200,50);
    	pipei.setBounds(810,560,200,50);
    	jp.add(wangzhi);
    	jp.add(wangzhiTxt);
    	jp.add(paqu1);
    	jp.add(paqu2);   	
    	jp.add(wenben);
    	jp.add(jsp_wenben);
    	jp.add(yuanma);
    	jp.add(jsp_yuanma);
    	jp.add(minganku); 
    	jp.add(jsp_minganci);
    	jp.add(daoru);
    	jp.add(pipei);
    	jp.setLayout(null);
    	jf.add(jp);
		jf.setContentPane(jp);
		jf.setVisible(true);  //ʹ����ɼ�
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//���ô���رշ�ʽ	
		//��ȡ��ҳ��html�������ҳ�ı�
		paqu1.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	    if(wangzhiTxt.getText().equals("")) {
		    		    JOptionPane.showMessageDialog(jp,"��ַ������Ϊ��!","��ʾ",JOptionPane.INFORMATION_MESSAGE);
		    	    }
		    	    else getText(wangzhiTxt.getText());   	  
		      }
		});
		//��ȡ�ı��е���ַ��
		paqu2.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		  		  File f=new File("��ַ��.TXT");
				  BufferedReader in=null;
			      PrintStream out=null;
		    	  try {
			    	    if(minganci.getText().equals("")) {
			    		    JOptionPane.showMessageDialog(jp,"���дʲ�����Ϊ��!","��ʾ",JOptionPane.INFORMATION_MESSAGE);
			    	    }
			    	    else {
							in=new BufferedReader(new FileReader(f));
							out=new PrintStream(new File("���дʼ�¼.TXT"));				
							String str=null;
							String text=new String();
							while(!(str=in.readLine()).equals("###")) {
								out.println("��ַ"+str+"�����дʼ�¼��");
								getText(str);	
								text=wenbenTxt.getText();
								for(int i=0;i<MinganWords.size();i++) {//ͳ����ҳ�����дʳ��ִ���
									int index=0,sum=0;
									while((index=text.indexOf(MinganWords.get(i),index))>=0) {
										sum+=1;
										index+=MinganWords.get(i).length();
									}
									out.println("���дʡ�"+MinganWords.get(i)+"��������"+sum+"��");
								}
							}
			    	    }  
		    	  } catch (FileNotFoundException e) {
					  e.printStackTrace();
				  }catch(IOException e) {
					  e.printStackTrace();
				  }finally {
					  try {
						  in.close();
						  out.close();
					  } catch (IOException e) {
						  e.printStackTrace();
					 }
				  }
		      }
		});
		//�����дʿ⵼���ı���
		daoru.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {		    	  
		    	  minganci.setText("");
		  		  BufferedReader in=null;
				  try {
					  File f=new File("���дʿ�.TXT");
					  in=new BufferedReader(new FileReader(f));
					  String buf=null;
					  while((buf=in.readLine())!=null) {
						  minganci.append(buf+"\n");
						  MinganWords.add(buf);
					  }
				  } catch (FileNotFoundException e) {
					  e.printStackTrace();
				  }catch(IOException e) {
					  e.printStackTrace();
				  }finally {
					  try {
						  in.close();
					  } catch (IOException e) {
						  e.printStackTrace();
					 }
				  }
		      }	
		});
		//������ʾ��ҳ�е����д�	
		pipei.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  if(minganci.getText().equals("")) {
		    		  JOptionPane.showMessageDialog(jp,"���дʲ�����Ϊ��!","��ʾ",JOptionPane.INFORMATION_MESSAGE);
		    	  }
		    	  else {
			  		  Highlighter highlight=wenbenTxt.getHighlighter();
					  DefaultHighlightPainter xianshi=new DefaultHighlightPainter(Color.GREEN);
					  String text=wenbenTxt.getText();
					  int index=0;
					  for(int i=0;i<MinganWords.size();i++) {
						  while((index=text.indexOf(MinganWords.get(i),index))>=0) {//������ҳ�ı��г��ֵ����д�
							  try {
								  highlight.addHighlight(index, index+MinganWords.get(i).length(), xianshi);
								  index+=MinganWords.get(i).length();
							  } catch (BadLocationException e) {
								  e.printStackTrace();
							 }
						 }
					 }
		    	  }
		      }	
		});
	}
	//��ȡhtmlԴ�����ҳ�ı�
	public void getText(String str) {
  		String regex_script="<script[^>]*?>[\\s\\S]*?<\\/script>";//script��ǩ
		String regex_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //style��ǩ
		String regex_html="<[^>]+>";//html��ǩ
		String regex_luanma="&[\\S]*?;+";//���� 
		String regex_kongge="[\\s]{2,}";//�ո��س�
		Pattern p_script=Pattern.compile(regex_script,Pattern.CASE_INSENSITIVE);
		Pattern p_style=Pattern.compile(regex_style,Pattern.CASE_INSENSITIVE);
		Pattern p_html=Pattern.compile(regex_html,Pattern.CASE_INSENSITIVE);
		Pattern p_luanma=Pattern.compile(regex_luanma,Pattern.CASE_INSENSITIVE);
		Pattern p_kongge=Pattern.compile(regex_kongge,Pattern.CASE_INSENSITIVE);
	    String buf=null;
	    String htmltext=new String();
	    String text=new String();
	    URL url;
	    URLConnection urlconn;
	    BufferedReader reader = null;
	    yuanmaTxt.setText("");
	    wenbenTxt.setText("");
	    try {
	    	url=new URL(str);
		    urlconn=url.openConnection();
		    urlconn.connect();
		    reader=new BufferedReader(new InputStreamReader(urlconn.getInputStream(),"GBK"));	
		    while((buf=reader.readLine())!=null) {//��ȡhtmlԴ��
			    htmltext+=buf+"\n";
		    }
		    //��ȡ��ҳ�ı�
    		text=htmltext;
		    Matcher m_script=p_script.matcher(text);
		    text=m_script.replaceAll("");
		    Matcher m_style=p_style.matcher(text);
		    text=m_style.replaceAll("");
		    Matcher m_html=p_html.matcher(text);
		    text=m_html.replaceAll("");
		    Matcher m_kongge=p_kongge.matcher(text);
		    text=m_kongge.replaceAll("\n");
		    Matcher m_luanma=p_luanma.matcher(text);
		    text=m_luanma.replaceAll("");
		    yuanmaTxt.append(htmltext);
		    wenbenTxt.append(text);
	    }catch (Exception e) {
	    	JOptionPane.showMessageDialog(jp,"��ȡʧ��!","��ʾ",JOptionPane.INFORMATION_MESSAGE);
		    e.printStackTrace();
	    }finally {
	    	JOptionPane.showMessageDialog(jp,"��ȡ�ɹ�!","��ʾ",JOptionPane.INFORMATION_MESSAGE);       
	    	try {
			    reader.close();			    
		    } catch (IOException e) {
			    e.printStackTrace();
		    }
	    }
	}
	public static void main(String[] args) {
        Spider spider=new Spider(); 
	}
}
