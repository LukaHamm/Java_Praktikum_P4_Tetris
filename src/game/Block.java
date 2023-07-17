package game;

import javax.swing.*;
import java.awt.*;

public class Block extends JPanel {

    public static final int BLOCK_SIZE = 45;

    private Color farbe;

    public Block(int xPosition, int yPosition, Color farbe) {
       if (farbe != null){
           this.farbe = farbe;
           setBackground(farbe);
       }else {
           setBackground(Color.BLACK);
        }
        setBounds(xPosition,yPosition,BLOCK_SIZE,BLOCK_SIZE);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(BLOCK_SIZE,BLOCK_SIZE));

        this.setLayout(null);
    }

    public Block() {
        this.setLayout(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (farbe != null){
            g.setColor(farbe);
        }else {
            g.setColor(Color.BLACK);
        }
        g.fillRect(0, 0, Block.BLOCK_SIZE, Block.BLOCK_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, Block.BLOCK_SIZE, Block.BLOCK_SIZE);
    }
}
