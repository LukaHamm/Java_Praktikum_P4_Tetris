package game;

import javax.swing.*;
import java.awt.*;

public class Vorschau extends JPanel {

    public Vorschau (){
        setBounds(11*Block.BLOCK_SIZE,0,11*Block.BLOCK_SIZE,
                20*Block.BLOCK_SIZE);
        setBackground(Color.darkGray);
        setLayout(null);
    }

    public void vorschau_anzeigen(Form naechsteForm){
        Form naechsteFormVorschau = new Form(naechsteForm,naechsteForm.getFarbe());
        removeAll();
        for (Block [] liste:naechsteFormVorschau.getBlockListe()){
            for (Block block: liste){
                if (block != null){
                    block.setLocation(block.getX() - 4*Block.BLOCK_SIZE,block.getY() + 10*Block.BLOCK_SIZE);
                    add(block);
                }
            }
        }
        repaint();
    }
}
