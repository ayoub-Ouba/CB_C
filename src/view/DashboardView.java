package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class DashboardView extends JFrame {
    
    private DefaultTableModel model; // modèle de table pour manipuler les données du tableau
    private JTable table;

    public DashboardView() {
        setTitle("Dashboard");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Panel de l'en-tête
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(getWidth(), 70));

        JLabel titleLabel = new JLabel("CORSIBUTTEGA", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(66, 133, 244));
        header.add(titleLabel, BorderLayout.CENTER);

        // Bouton "Ajouter Client"
        JButton btnAjouter = new JButton("➕ Ajouter Client");
        btnAjouter.setFont(new Font("Serif", Font.BOLD, 14));
        btnAjouter.setBackground(new Color(66, 133, 244));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setPreferredSize(new Dimension(180, 40));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(btnAjouter);
        header.add(rightPanel, BorderLayout.EAST);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(66, 133, 244));
        sidebar.setPreferredSize(new Dimension(210, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        sidebar.add(createSidebarButton("🏠 Accueil"));
        sidebar.add(createSidebarButton("👤 Clients"));
        sidebar.add(createSidebarButton("🛒 Commandes "));
        sidebar.add(createSidebarButton("🚪 Déconnexion"));

        // Bouton "Ajouter Commande"
        JButton btnAjouterCommande = createSidebarButton("🛒 Ajouter Commande");
        btnAjouterCommande.addActionListener(e -> {
            String numeroClient = JOptionPane.showInputDialog("Veuillez entrer le numéro du client:");
            if (numeroClient != null && !numeroClient.isEmpty()) {
                new AjouterCommande(this, numeroClient); // Ouvre la fenêtre pour ajouter une commande
            }
        });
        sidebar.add(btnAjouterCommande);

        // Modèle de données du tableau
        String[] columnNames = {"Numéro Client", "Nom", "Prénom", "Commande", "Prix Total (€)"};
        model = new DefaultTableModel(columnNames, 0) { // 0 lignes au début
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // La colonne "Commande" est éditable
            }
        };

        // Tableau
        table = new JTable(model);
        table.setRowHeight(100); // Augmenter la hauteur de ligne
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // Numéro Client
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Nom
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Prénom
        table.getColumnModel().getColumn(3).setPreferredWidth(650); // Commande (Grande colonne)
        table.getColumnModel().getColumn(4).setPreferredWidth(50);  // Prix Total 

        table.getColumnModel().getColumn(3).setCellRenderer(new CommandeRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(sidebar, BorderLayout.WEST);
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Action du bouton "Ajouter Client"
        btnAjouter.addActionListener(e -> {
            System.out.println("Bouton Ajouter Client cliqué !");
            new AjouterClient(this); // Ouvre la fenêtre d'ajout
        });
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(66, 133, 244));
        button.setMinimumSize(new Dimension(210, 50));
        button.setMaximumSize(new Dimension(210, 50));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    // Action du bouton "Ajouter Commande"
    class AjouterCommande extends JDialog {
        private JTextField produitField;
        private JTextField quantiteField;
        private JTextField prixField;
        private JButton btnAjouter;

        public AjouterCommande(JFrame parent, String numeroClient) {
            super(parent, "Ajouter Commande", true);
            setSize(400, 250);
            setLocationRelativeTo(parent);
            setLayout(new GridLayout(4, 2));

            // Label et champ pour le produit
            add(new JLabel("Produit :"));
            produitField = new JTextField();
            add(produitField);

            // Label et champ pour la quantité
            add(new JLabel("Quantité :"));
            quantiteField = new JTextField();
            add(quantiteField);

            // Label et champ pour le prix
            add(new JLabel("Prix (€) :"));
            prixField = new JTextField();
            add(prixField);

            // Bouton Ajouter
            btnAjouter = new JButton("Ajouter");
            btnAjouter.addActionListener(e -> ajouterCommande(numeroClient));
            add(btnAjouter);

            setVisible(true);
        }

        // Méthode pour ajouter la commande
        private void ajouterCommande(String numeroClient) {
            String produit = produitField.getText();
            String quantite = quantiteField.getText();
            String prix = prixField.getText();

            // Ajouter la commande dans la table
            if (!produit.isEmpty() && !quantite.isEmpty() && !prix.isEmpty()) {
                // Convertir la quantité et le prix en nombres pour effectuer des calculs si nécessaire
                try {
                    int quantiteInt = Integer.parseInt(quantite);
                    double prixDouble = Double.parseDouble(prix);

                    // Appel à la méthode de la vue principale pour ajouter la commande
                    ((DashboardView) getParent()).ajouterCommandeTableau(numeroClient, produit, quantiteInt, prixDouble);
                    dispose(); // Fermer la fenêtre après l'ajout
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides pour la quantité et le prix.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Ajouter une commande dans la table
    public void ajouterCommandeTableau(String numeroClient, String produit, int quantite, double prix) {
        SwingUtilities.invokeLater(() -> {
            // Recherche du client dans la table par son numéro
            for (int i = 0; i < model.getRowCount(); i++) {
                String numClient = (String) model.getValueAt(i, 0);
                if (numClient.equals(numeroClient)) {
                    // Récupérer l'objet CommandePanel de la cellule "Commande"
                    CommandePanel panelCommande = (CommandePanel) model.getValueAt(i, 3);

                    // Ajouter la commande dans le panneau CommandePanel
                    panelCommande.ajouterCommande(produit, quantite, prix);

                    // Mettre à jour le prix total dans la table
                    double prixTotal = (double) model.getValueAt(i, 4);
                    prixTotal += prix * quantite;
                    model.setValueAt(prixTotal, i, 4);
                    break;
                }
            }
        });
    }

    // Panel pour afficher les commandes
    class CommandePanel extends JPanel {
        private DefaultTableModel model;
        private JTable table;

        public CommandePanel() {
            setLayout(new BorderLayout());

            String[] columnNames = {"Produit", "Quantité", "Prix (€)", "Total (€)"};
            model = new DefaultTableModel(columnNames, 0);

            table = new JTable(model);
            table.setRowHeight(30);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
        }

        // Méthode pour ajouter une commande à ce client
        public void ajouterCommande(String produit, int quantite, double prix) {
            double total = quantite * prix;
            model.addRow(new Object[]{produit, quantite, prix, total});
        }
    }

    // Ajouter un client dans la table
    public void ajouterClientTableau(String numero, String nom, String prenom) {
        SwingUtilities.invokeLater(() -> {
            model.addRow(new Object[]{numero, nom, prenom, new CommandePanel(), "0.00"});
            table.revalidate();
            table.repaint();
        });
    }

    // Rendu personnalisé de la cellule Commande
    class CommandeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof CommandePanel) {
                return (CommandePanel) value;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardView().setVisible(true));
    }
}

