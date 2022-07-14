import javax.swing.*;
import java.awt.*;

public class ModoButton extends JButton  {

    public ModoButton(TipoBotao tipo) {

        // Configurando visualmente o Botão de Escolha de Modo
        this.setFocusable(false);
        this.setFocusPainted(false);
        this.setHorizontalTextPosition(JButton.LEFT);
        this.setVerticalAlignment(JButton.CENTER);
        this.setFont(new Font("Cooper Black", Font.BOLD, 20));
        this.setIconTextGap(15);
        this.setForeground(Color.black);
        this.setBackground(Color.yellow);

        // Se for Singleplayer, escreva o nome Singleplayer no Botão e o respectivo Ícone
        if(tipo == TipoBotao.SINGLEPLAYER){
            this.setIcon(new ImageIcon("media/singleplayer.png"));
            this.setText("Singleplayer");
        // Se for Multiplayer, escreva o nome Multiplayer no Botão e o respectivo Ícone
        } else if(tipo == TipoBotao.MULTIPLAYER){
            this.setIcon(new ImageIcon("media/multiplayer.png"));
            this.setText("Multiplayer");
        }
        // Botão do Tipo Cliente
        else if(tipo == TipoBotao.CLIENTE){
            this.setIcon(new ImageIcon("media/cliente.png"));
            this.setText("Conectar no Servidor");
        }
        // Botão do Tipo Servidor
        else if(tipo == TipoBotao.SERVIDOR){
            this.setIcon(new ImageIcon("media/servidor.png"));
            this.setText("Criar Servidor");
        }
    }
}
