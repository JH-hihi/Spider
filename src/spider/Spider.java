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
	private JFrame jf=new JFrame("抓取网络敏感词");
	private JPanel jp=new JPanel();
	private JLabel wangzhi=new JLabel("输入网址");
	private JTextField wangzhiTxt=new JTextField();
	private JButton paqu1=new JButton("爬取");
	private JButton paqu2=new JButton("爬取网址库");
	private JLabel wenben=new JLabel("网页文本");
	private JTextArea wenbenTxt=new JTextArea();
	private JScrollPane jsp_wenben=new JScrollPane(wenbenTxt);
	private JLabel yuanma=new JLabel("HTML源码");
	private JTextArea yuanmaTxt=new JTextArea();
	private JScrollPane jsp_yuanma=new JScrollPane(yuanmaTxt);
	private JLabel minganku=new JLabel("敏感词库");
	private JButton daoru=new JButton("导入敏感库");
	private JTextArea minganci=new JTextArea();
	private JScrollPane jsp_minganci=new JScrollPane(minganci);
	private JButton pipei=new JButton("匹配敏感库");
	private ArrayList<String>MinganWords=new ArrayList<String>();//存储敏感词

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
		jf.setVisible(true);  //使窗体可见
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置窗体关闭方式	
		//获取网页的html代码和网页文本
		paqu1.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	    if(wangzhiTxt.getText().equals("")) {
		    		    JOptionPane.showMessageDialog(jp,"网址不可以为空!","提示",JOptionPane.INFORMATION_MESSAGE);
		    	    }
		    	    else getText(wangzhiTxt.getText());   	  
		      }
		});
		//爬取文本中的网址库
		paqu2.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		  		  File f=new File("网址库.TXT");
				  BufferedReader in=null;
			      PrintStream out=null;
		    	  try {
			    	    if(minganci.getText().equals("")) {
			    		    JOptionPane.showMessageDialog(jp,"敏感词不可以为空!","提示",JOptionPane.INFORMATION_MESSAGE);
			    	    }
			    	    else {
							in=new BufferedReader(new FileReader(f));
							out=new PrintStream(new File("敏感词记录.TXT"));				
							String str=null;
							String text=new String();
							while(!(str=in.readLine()).equals("###")) {
								out.println("网址"+str+"的敏感词记录：");
								getText(str);	
								text=wenbenTxt.getText();
								for(int i=0;i<MinganWords.size();i++) {//统计网页中敏感词出现次数
									int index=0,sum=0;
									while((index=text.indexOf(MinganWords.get(i),index))>=0) {
										sum+=1;
										index+=MinganWords.get(i).length();
									}
									out.println("敏感词“"+MinganWords.get(i)+"”出现了"+sum+"次");
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
		//将敏感词库导入文本框
		daoru.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {		    	  
		    	  minganci.setText("");
		  		  BufferedReader in=null;
				  try {
					  File f=new File("敏感词库.TXT");
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
		//高亮显示网页中的敏感词	
		pipei.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  if(minganci.getText().equals("")) {
		    		  JOptionPane.showMessageDialog(jp,"敏感词不可以为空!","提示",JOptionPane.INFORMATION_MESSAGE);
		    	  }
		    	  else {
			  		  Highlighter highlight=wenbenTxt.getHighlighter();
					  DefaultHighlightPainter xianshi=new DefaultHighlightPainter(Color.GREEN);
					  String text=wenbenTxt.getText();
					  int index=0;
					  for(int i=0;i<MinganWords.size();i++) {
						  while((index=text.indexOf(MinganWords.get(i),index))>=0) {//查找网页文本中出现的敏感词
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
	//获取html源码和网页文本
	public void getText(String str) {
  		String regex_script="<script[^>]*?>[\\s\\S]*?<\\/script>";//script标签
		String regex_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //style标签
		String regex_html="<[^>]+>";//html标签
		String regex_luanma="&[\\S]*?;+";//乱码 
		String regex_kongge="[\\s]{2,}";//空格或回车
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
		    while((buf=reader.readLine())!=null) {//获取html源码
			    htmltext+=buf+"\n";
		    }
		    //获取网页文本
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
	    	JOptionPane.showMessageDialog(jp,"爬取失败!","提示",JOptionPane.INFORMATION_MESSAGE);
		    e.printStackTrace();
	    }finally {
	    	JOptionPane.showMessageDialog(jp,"爬取成功!","提示",JOptionPane.INFORMATION_MESSAGE);       
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
