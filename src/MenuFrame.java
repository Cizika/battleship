import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame implements ActionListener {

    private JLayeredPane mainPanel;
    private Image logo;
    private JButton singleplayerButton;
    private JButton multiplayerButton;


    public MenuFrame() {

        // Iniciando Componentes
        this.logo = new ImageIcon("logo.png").getImage();
        this.mainPanel = new JLayeredPane();
        mainPanel.setLayout(new OverlayLayout(mainPanel));

        // Adicionando Painel dos Botoes
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
        buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        buttonPanel.setOpaque(false);
        this.singleplayerButton = new ModoButton(true);
        this.multiplayerButton = new ModoButton(false);

        buttonPanel.add(this.singleplayerButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(this.multiplayerButton);

        // Adicionando MainPanel
        this.mainPanel.add(new MenuPanel(), Integer.valueOf(0));
        this.mainPanel.add(buttonPanel,Integer.valueOf(1));
        this.add(this.mainPanel);

        // Inicializando Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Batalha Naval");
        this.setIconImage(this.logo);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
