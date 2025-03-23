package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import view.LoginView;

public class LoginView extends JFrame {
	   private JTextField emailField;
	    private JPasswordField passwordField;
	    private JButton loginButton;
    

    public LoginView() {
    	    setTitle("Login");
	        setSize(950, 700);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setLayout(new BorderLayout());

	        // Panel principal divisé en 2 parties
	        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
	        add(mainPanel, BorderLayout.CENTER);

	        // Partie gauche (Bleu avec le logo)
	        JPanel leftPanel = new JPanel();
	        leftPanel.setBackground(new Color(66, 133, 244));
	        leftPanel.setLayout(new GridBagLayout());
	        JLabel logo = new JLabel("CORSIBUTEGGA");
	        logo.setFont(new Font("Serif", Font.BOLD, 25));
	        logo.setForeground(Color.WHITE);
	        leftPanel.add(logo);
	        mainPanel.add(leftPanel);

	        // Partie droite (Blanche avec le formulaire)
	        JPanel rightPanel = new JPanel(new GridBagLayout());
	        rightPanel.setBackground(Color.WHITE);
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10);
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.anchor = GridBagConstraints.CENTER;

	        JLabel title = new JLabel("Benvinutu");
	        title.setFont(new Font("Serif", Font.BOLD, 25));
	        rightPanel.add(title, gbc);

	        gbc.gridy++;
	        JLabel subtitle = new JLabel("Connecter à votre compte");
	        subtitle.setFont(new Font("Serif", Font.PLAIN, 13));
	        rightPanel.add(subtitle, gbc);

	        // Champ de texte pour l'email avec placeholder
	        gbc.gridy++;
	        emailField = new JTextField(20);
	        emailField.setPreferredSize(new Dimension(400, 35));
	        emailField.setBorder(new RoundedBorder(10, "I"));
	        emailField.setForeground(Color.GRAY);
	        emailField.setText("Email");

	        emailField.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                if (emailField.getText().equals("Email")) {
	                    emailField.setText("");
	                    emailField.setForeground(Color.BLACK);
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                if (emailField.getText().isEmpty()) {
	                    emailField.setForeground(Color.GRAY);
	                    emailField.setText("Email");
	                }
	            }
	        });

	        rightPanel.add(emailField, gbc);

	        // Champ de mot de passe avec placeholder
	        gbc.gridy++;
	        passwordField = new JPasswordField(20);
	        passwordField.setPreferredSize(new Dimension(400, 35));
	        passwordField.setBorder(new RoundedBorder(10, "I"));
	        rightPanel.add(passwordField, gbc);

	        // Bouton de connexion
	        gbc.gridy++;
	        loginButton = new JButton("Connexion");
	        loginButton.setPreferredSize(new Dimension(200, 40));
	        loginButton.setForeground(Color.WHITE);
	        loginButton.setBorder(new RoundedBorder(10, "B"));
	        loginButton.setBackground(new Color(66, 133, 244));

	        // Effet de survol pour le bouton
	        loginButton.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                loginButton.setBackground(new Color(52, 120, 230));
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                loginButton.setBackground(new Color(66, 133, 244));
	            }
	        });

	        rightPanel.add(loginButton, gbc);
	        mainPanel.add(rightPanel);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Classe pour créer une bordure arrondie
    class RoundedBorder implements Border {
        private int radius;
        private String type_input;

        public RoundedBorder(int radius, String type_input) {
            this.radius = radius;
            this.type_input = type_input;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (type_input.equals("B")) {
                g2d.setColor(new Color(66, 133, 244)); // Couleur bleue pour le bouton
            } else {
                g2d.setColor(new Color(220, 236, 255)); // Couleur claire pour les champs de texte
            }

            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }


}