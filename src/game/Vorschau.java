package game;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Vorschau extends JPanel {

	private String punktzahl;
	private JLabel textArea;
	private JLabel jLabel;

	public Vorschau() {
		setBounds(11 * Block.BLOCK_SIZE, 0, 11 * Block.BLOCK_SIZE, 20 * Block.BLOCK_SIZE);
		setBackground(Color.darkGray);
		setLayout(null);
		jLabel = new JLabel("Punktzahl");
		jLabel.setBounds(0, 0, 2 * Block.BLOCK_SIZE, Block.BLOCK_SIZE);
		jLabel.setVisible(true);
		jLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		jLabel.setForeground(Color.WHITE);
		this.add(jLabel);
		textArea = new JLabel(punktzahl);
		textArea.setBounds(2 * Block.BLOCK_SIZE, 0, 2 * Block.BLOCK_SIZE, Block.BLOCK_SIZE);
		textArea.setVisible(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		textArea.setForeground(Color.WHITE);
		this.add(textArea);

	}

	public void vorschau_anzeigen(Form naechsteForm) {
		Form naechsteFormVorschau = new Form(naechsteForm, naechsteForm.getFarbe());
		removeAll();
		for (Block block : naechsteFormVorschau.getBlockListe()) {
			if (block != null) {
				block.setLocation(block.getX() - 4 * Block.BLOCK_SIZE, block.getY() + 10 * Block.BLOCK_SIZE);
				add(block);
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
