package initialScreen;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class twitterAuth extends JFrame {
	static twitterAuth frame;
	private JPanel contentPane;
	private JTextField txtVCode;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new twitterAuth();
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
	public twitterAuth() {
		setBackground(Color.WHITE);
		setTitle("Twitter OAuth");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 325);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblShowLink = new JLabel("Open following link in browser to authorize this application:");
		lblShowLink.setBounds(20, 37, 353, 29);
		contentPane.add(lblShowLink);
		
        //Get access token
		final OAuthService service=new ServiceBuilder().provider(TwitterApi.class).apiKey("DvVZSqd4k9dRAan8mU3KtyJYZ").apiSecret("ir2VkDAPD6DYApti4bs5UfcjT4aKey6YWmPFP6neqojZrTKjSW").build();
		//Get request token
		final Token reqToken=service.getRequestToken();
		//Making the user validate your request
		String authUrl=service.getAuthorizationUrl(reqToken);
		
		JLabel lblVLink = new JLabel("");
		lblVLink.setForeground(Color.BLUE);
		lblVLink.setBounds(10, 63, 552, 29);
		contentPane.add(lblVLink);
		
		//display authURL link
		lblVLink.setText(authUrl);
		System.out.println("Verification link: "+authUrl);
		
		JLabel lblVCode = new JLabel("Enter verification code:");
		lblVCode.setBounds(20, 135, 150, 22);
		contentPane.add(lblVCode);
		
		txtVCode = new JTextField();
		txtVCode.setBounds(180, 136, 107, 20);
		contentPane.add(txtVCode);
		txtVCode.setColumns(10);
		
		JButton btnAuthorize = new JButton("Authorize");
		btnAuthorize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Verifier vTkn=new Verifier(txtVCode.getText());
				Token axsTkn=service.getAccessToken(reqToken, vTkn);
				//Sign request
				OAuthRequest request;
				request= new OAuthRequest(Verb.GET,"https://api.twitter.com/1.1/account/verify_credentials.json"); 
				service.signRequest(axsTkn, request); 
				// the access token from step 4
				Response response = request.send();
				//System.out.println(response.getBody());
			
				postStatus obj=new postStatus();//service,axsTkn,request);
				frame.setVisible(false);
				obj.setVisible(true);
			
			}
		});
		btnAuthorize.setBounds(115, 168, 98, 23);
		contentPane.add(btnAuthorize);
	}

}
