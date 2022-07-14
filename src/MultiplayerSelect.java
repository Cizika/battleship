import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiplayerSelect extends JFrame implements ActionListener {
    private final JLayeredPane mainPanel;
    private final Image logo;
    private final JButton existingConnectionButton;
    private final JButton newServerButton;
    private final String nome;

    public MultiplayerSelect(String nome) {
        // Iniciando Componentes
        this.logo = new ImageIcon("media/logo.png").getImage();
        this.nome = nome;
        this.mainPanel = new JLayeredPane();
        mainPanel.setLayout(new OverlayLayout(mainPanel));

        // Adicionando Painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        buttonPanel.setOpaque(false);

        // Adicionando Botão de Singleplayer
        existingConnectionButton = new ModoButton(TipoBotao.CLIENTE);
        existingConnectionButton.addActionListener(this);

        // Adicionando Botão de Multiplayer
        newServerButton = new ModoButton(TipoBotao.SERVIDOR);
        newServerButton.addActionListener(this);

        // Adicionando botões ao ButtonPanel
        buttonPanel.add(this.existingConnectionButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(this.newServerButton);

        // Adicionando MainPanel
        this.mainPanel.add(new MenuPanel(), Integer.valueOf(0));
        this.mainPanel.add(buttonPanel, Integer.valueOf(1));
        this.add(this.mainPanel);

        // Configurando Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Batalha Naval");
        this.setIconImage(this.logo);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == existingConnectionButton) {
            // Verificando se servidor está conectado
            if(Cliente.servidorEncontrado()){
                // Servidor encontrado, iniciando cliente
                this.dispose();
                new Cliente(this.nome);
            }
            else{
                JOptionPane.showMessageDialog(this, "Nenhum servidor para se conectar. Tente abrir um!", "Erro no Servidor", JOptionPane.WARNING_MESSAGE);
            }

        } else if (e.getSource() == newServerButton) {
            // Iniciando Servidor para Multiplayer
            this.dispose();
            Servidor servidor = new Servidor(this.nome);

            // Aguardando conexão do cliente
            servidor.ligar();
        }
    }
}
