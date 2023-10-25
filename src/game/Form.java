/****************************************
Projekt: Tetris Spiel 
File: Form.java
Author: Daniel Klemmer
Last changed: 14.10.2023
****************************************/

package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Form {
	
	/**
	 * Blockliste der Form
	 */
	private Block[] blockListe = new Block[4];

	/**
     * Variable für abgeleitete Formen um rotation durchführen zu können
     */
	public int zentrum;

	/**
	 * Farbe der Form
	 */
	private Color farbe;

	/**
     * Blocklisten für das Layout für die Formen
     */
	protected static final Block[] GERADE_BLOCKLISTE = {
			new Block(Block.BLOCK_SIZE * 5, 0, null),
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE, null),
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 3, null)};

	protected static final Block[] QUADRAT_BLOCKLISTE = {
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 3, null),
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 3, null)};

	protected static final Block[] PYRAMIDE_BLOCKLISTE = {
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 3, null),
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 3, null),
			new Block(Block.BLOCK_SIZE * 7, Block.BLOCK_SIZE * 3, null)};

	protected static final Block[] L_FORM_BLOCKLISTE = {
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE, null),
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 3, null),
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 3, null)};

	protected static final Block[] L_FORM_INVERTIERT_BLOCKLISTE = {
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE, null),
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 3, null),
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 3, null)};

	protected static final Block[] Z_FORM_BLOCKLISTE = {
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 3, null),
			new Block(Block.BLOCK_SIZE * 7, Block.BLOCK_SIZE * 3, null)};

	protected static final Block[] Z_FORM_INVERTIERT_BLOCKLISTE = {
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 7, Block.BLOCK_SIZE * 2, null),
			new Block(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 3, null),
			new Block(Block.BLOCK_SIZE * 6, Block.BLOCK_SIZE * 3, null)};

	public Form() {
	}

	/**
	 * copy Constructor
	 * @param form die Form von der kopiert werden soll
	 * @param farbe die Farbe die die neue Form haben soll
	 */
	public Form(Form form, Color farbe) {
		this.zentrum = form.zentrum;
		this.farbe = farbe;
		kopiere_Bloecke(form.getBlockListe());
	}
	
	/**
     * Constructor
     * @param blockListe die Blockliste mit der die Form erstellt wird
     */
	public Form(Block[] blockListe) {
		kopiere_Bloecke(blockListe);
	}

	/**
	 * kopiert eine gegebene Blockliste
	 * @param blockListe
	 */
	private void kopiere_Bloecke(Block[] blockListe) {
		for (int i = 0; i < 4; i++) {
			if (blockListe[i] != null) {
				if (blockListe[i] instanceof ExplosionBlock) {
					this.blockListe[i] = new ExplosionBlock(blockListe[i].getX(), blockListe[i].getY());
				} else {
					this.blockListe[i] = new Block(blockListe[i].getX(), blockListe[i].getY(), farbe);
				}
			}
		}
	}

	/**
     * Soll das blocklayout der Form drehen
     * @return gibt ein Blocklayout von der Form im gedrehten zustand zurück
     */
	public Block[] drehen() {
		if (zentrum == -1) {
			return null;
		}
		Block zentrumBlock = new Block(this.blockListe[zentrum].getX(), this.blockListe[zentrum].getY(), farbe);
		Block[] blockListeGedreht = new Block[5];
		for (int i = 0; i < 4; i++) {
			Block block = this.blockListe[i];
			if (block != null) {
				int xRelativ = block.getX() - zentrumBlock.getX();
				int yRelativ = block.getY() - zentrumBlock.getY();

				int xRotiert = zentrumBlock.getX() - yRelativ;
				int yRotiert = zentrumBlock.getY() + xRelativ;

				blockListeGedreht[i] = new Block(xRotiert, yRotiert, farbe);
			}	
		}
		return blockListeGedreht;
	}

	/**
     * bewegt abhängig von einem keyevent die Form nach links oder rechts
     * @param event Tastaturevent von java.awt
     */
	public void seitlich_bewegen(KeyEvent event) {
		int keycode = event.getKeyCode();
		int verschiebung = 0;
		if(keycode == KeyEvent.VK_RIGHT) {
			verschiebung = Block.BLOCK_SIZE;
		}
		else if(keycode == KeyEvent.VK_LEFT) {
			verschiebung = -Block.BLOCK_SIZE;
		}
		for (Block block : blockListe) {
			if (block != null) {
				block.setLocation(block.getX() + verschiebung, block.getY());
			}
		}
	}

	/**
     * kopiert von einer Blockliste die koordinaten auf die Blöcke der aktuellen Form
     * @param blockListe eine Liste an blöcken von der die Koordinaten kopiert werden soll
     */
	public void uebernehme_Blockkoordinaten(Block[] blockListe) {
		for (int i = 0; i < 4; i++) {
			if (blockListe[i] != null) {
				this.blockListe[i].setLocation(blockListe[i].getX(), blockListe[i].getY());
			}
		}
	}

	/**
     * bewegt Form nach unten
     * @param multiplicator Fallgeschwidigkeit/Bildrate
     */
	public void fallen_lassen(float multiplicator) {
		for (Block block : blockListe) {
			if (block != null) {
				int dy = (int) (multiplicator * Block.BLOCK_SIZE);
				block.setBounds(block.getX(), block.getY() + dy, Block.BLOCK_SIZE, Block.BLOCK_SIZE);
			}
		}
	}

	/**
     * macht ein Block im Blocklayout zu einen Explosivblock
     * @param blocknummer die Nummer des Blockes die zum Explosionblock wird
     */
	public void explosivblock_erzeugen(int blocknummer) {
		int zaehler = 0;
		for (int i = 0; i < 4; i++) {
			if (blockListe[i] != null) {
				zaehler++;
			}
			if (zaehler == blocknummer) {
				blockListe[i] = new ExplosionBlock(blockListe[i].getX(), blockListe[i].getY());
				break;
			}
		}

	}

	/**
     * bewegt den Block eine Feste anzahl an Blöcke nach unten
     * @param distanz die Anzahl an Pixeln die die Form nach unten verschoben wird
     */
	public void nachruecken(int distanz) {
		for (Block block : this.getBlockListe()) {
			if (block != null) {
				block.setLocation(block.getX(), block.getY() + distanz);
			}
		}
	}

	/**
     * prüft ob die Form einen explosivblock hat
     * @return gibt true zurück falls die Form einen explosivblock hat, false wenn nicht
     */
	public boolean check_hat_explosivblock() {
		for (Block block : blockListe) {
			if (block instanceof ExplosionBlock) {
				return true;
			}
		}
		return false;
	}

	/** 
     * gibt den tiefsten Block der Form zurück
     * @returnden tiefsten Block der Form zurück
     */
	public Block get_untersten_Block() {
		Block untersterBlock = null;
		for (Block block : blockListe) {
			if (block != null) {
				if (untersterBlock == null || untersterBlock.getY() < block.getY()) {
					untersterBlock = block;
				}
			}
		}
		return untersterBlock;
	}

	/**
     * getter für die blockliste
     * @return die blockliste der Form
     */
	public Block[] getBlockListe() {
		return blockListe;
	}

	/**
     * getter für die Color
     * @return die Color der Form
     */
	public Color getFarbe() {
		return farbe;
	}
}
