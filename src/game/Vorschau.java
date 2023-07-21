package game;

import javax.swing.*;
import java.awt.*;

public class Vorschau extends JPanel {

    private String punktzahl;
    private  JLabel textArea;
    private  JLabel jLabel;

    public Vorschau (){
        setBounds(11*Block.BLOCK_SIZE,0,11*Block.BLOCK_SIZE,
                20*Block.BLOCK_SIZE);
        setBackground(Color.darkGray);
        setLayout(null);
        jLabel = new JLabel("Punktzahl");
        jLabel.setBounds(0,0,100,50);
        jLabel.setVisible(true);
        this.add(jLabel);
        textArea = new JLabel(punktzahl);
        textArea.setBounds(100,0,100,50);
        textArea.setVisible(true);
        this.add(textArea);

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
        this.add(jLabel);
        this.add(textArea);
        textArea.setText(punktzahl);
        repaint();
    }

    public void setPunktzahl(String punktzahl) {
        this.punktzahl = punktzahl;
    }
}
