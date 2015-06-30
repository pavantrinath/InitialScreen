package initialScreen;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import uk.co.mccann.gsb.engine.GoogleSafeBrowser;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import org.scribe.model.OAuthRequest;
import org.scribe.oauth.OAuthService;

import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.awt.Color;
import java.awt.Font;
public class postStatus extends JFrame {
static postStatus frame;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new postStatus();
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
	public postStatus() {
		setTitle("Post Status");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Initial Screen");
		lblTitle.setFont(new Font("Segoe UI Light", Font.BOLD, 22));
		lblTitle.setBounds(124, 20, 140, 24);
		contentPane.add(lblTitle);
		
		JLabel lblEnterStatus = new JLabel("Enter Status:");
		lblEnterStatus.setBounds(44, 47, 118, 24);
		contentPane.add(lblEnterStatus);
		
		//Put word limit using regular expr
		final JTextArea txtAStatus = new JTextArea();
		txtAStatus.setBackground(Color.lightGray);
		txtAStatus.setBounds(44, 82, 227, 63);
		
		contentPane.add(txtAStatus);
		
		
		final JButton btnTweet = new JButton("Tweet");
		btnTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String tweetStatus=txtAStatus.getText();
				Pattern p = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",Pattern.CASE_INSENSITIVE);
			  String[] val=tweetStatus.split(" ");
			  String chkURL=tweetStatus;
			  for(int i=0;i<val.length;i++)
			  {
				  chkURL=val[i];
				  Matcher m = p.matcher(chkURL);
			    while (m.find())
			    chkURL=val[i].substring(m.start(0),m.end(0));
			    break;
			}   System.out.println(tweetStatus);
				final Twitter twitter = new TwitterFactory().getInstance();

				twitter.setOAuthConsumer("DvVZSqd4k9dRAan8mU3KtyJYZ", "ir2VkDAPD6DYApti4bs5UfcjT4aKey6YWmPFP6neqojZrTKjSW");
				AccessToken accessToken = new AccessToken("125242821-et4aHegrwtvfWiUg0nw1eRziH6pmdkfT9a9UZden","y1D2pfOgx59ALeK4QGxRnBS61eUTJGtFuEBq3wndqxa8t");

				twitter.setOAuthAccessToken(accessToken);
				//check url before posting tweet
				String url = "https://sb-ssl.google.com/safebrowsing/api/lookup?client=TweetApp&key=AIzaSyAI_Myi_fNOt_Ta-8rvdmrjoCpCu4T6lWo&appver=1.5.2&pver=3.1&url="+chkURL;
				JLabel lblBadUrl = new JLabel("");
				lblBadUrl.setFont(new Font("Arial", Font.BOLD, 12));
				lblBadUrl.setForeground(Color.RED);
				lblBadUrl.setBounds(32, 213, 355, 38);
				contentPane.add(lblBadUrl); 
				
				URL obj=null;
				try {
					obj = new URL(url);
				} catch (MalformedURLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				HttpURLConnection con=null;
				try {
					con = (HttpURLConnection) obj.openConnection();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		 
				// optional default is GET
				try {
					con.setRequestMethod("GET");
				} catch (ProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 
				//add request header
				con.setRequestProperty("User-Agent", "Googlebot/2.1");
		 
				int responseCode=0;
				try {
					responseCode = con.getResponseCode();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					lblBadUrl.setText("Oops! We see a possible malware link. Consider retweet");
				}
				System.out.println("\nSending 'GET' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);
		 
				BufferedReader in=null;
				try {
					in = new BufferedReader(
					        new InputStreamReader(con.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					lblBadUrl.setText("Oops! We see a possible malware link. Consider retweet");
				}
				String inputLine;
				StringBuffer response = new StringBuffer();
		 
				try {
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					lblBadUrl.setText("Oops! We see a possible malware link. Consider retweet");
				}
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 
				//print result
				System.out.println(response.toString());
				try {
					String result="Positive";
					// String text = "I am deeply offended by our governments new policy";
					    Properties props = new Properties();
					    props.setProperty("annotators","tokenize, ssplit, parse, sentiment");
					    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
					    Annotation annotation = pipeline.process(tweetStatus);
					    String[] sentimentText = { "Very Negative","Negative",  "Positive", "Very Positive"};
					    for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
					     Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
					     int score = RNNCoreAnnotations.getPredictedClass(tree);
					     result=sentimentText[score];
					     System.out.println(sentimentText[score]);
					     					    }
					    if(result.equalsIgnoreCase("Positive")||result.equalsIgnoreCase("Very Positive"))
					    {
					    	twitter.updateStatus(tweetStatus);
					    	
					    }
					    else
					    {
					    	btnTweet.setEnabled(false);
					    	lblBadUrl.setText("Your tweet looks offensive. Are you sure you want to continue?");
					    	JButton btnPost = new JButton("Post");
					    	btnPost.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {try {
								twitter.updateStatus(tweetStatus);
							} catch (TwitterException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}});
					    	btnPost.setBounds(80, 260, 89, 23);
						contentPane.add(btnPost);
						JButton btnCancel = new JButton("Cancel");
						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {frame.setVisible(false);}});
						btnCancel.setBounds(215, 260, 89, 23);
						contentPane.add(btnCancel);
					    	
					    }
				} catch (/*Twitter*/Exception e) {
					// TODO Auto-generated catch block
					lblBadUrl.setText("Oops! We see a possible malware link. Consider retweet");
				}
				System.out.println("Successfully updated the status in Twitter.");
			
			}
		});
		
		btnTweet.setBounds(129, 171, 89, 23);
		contentPane.add(btnTweet);
		
		
	}
}
