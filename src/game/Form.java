package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Form {
    private Block [][] blockListe = new Block[5][5];

    protected int [] zentrum;

    private Color farbe;

    protected static final Block [][] GERADE_BLOCKLISTE = {{new Block(Block.BLOCK_SIZE*5,0,null),null,null,null,null},
                                                        {new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE,null),null,null,null,null},
                                                        {new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*2,null),null,null,null,null},
                                                        {new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*3,null),null,null,null,null},
                                                        {null,null,null,null,null}};

    protected static final Block [][] QUADRAT_BLOCKLISTE = {{null,new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*2,null),new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*2, null),null,null},
                                                         {null,new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*3,null),new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*3,null),null,null},
                                                         {null,null,null,null,null},
                                                         {null,null,null,null,null},
                                                         {null,null,null,null,null}};

    protected static final Block [][] PYRAMIDE_BLOCKLISTE = {{null,null,new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*2,null),null,null},
                                                         {null,new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*3,null),new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*3,null),new Block(Block.BLOCK_SIZE*7,Block.BLOCK_SIZE*3,null),null},
                                                         {null,null,null,null,null},
                                                         {null,null,null,null,null},
                                                         {null,null,null,null,null}};

    protected static final Block [][] L_FORM_BLOCKLISTE = {{new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE,null),null,null,null,null},
                                                        {new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*2,null),null,null,null,null},
                                                        {new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*3,null),new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*3,null),null,null,null},
                                                        {null,null,null,null,null},
                                                        {null,null,null,null,null}};

    protected static final Block [][] L_FORM_INVERTIERT_BLOCKLISTE = { {null,new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE,null),null,null,null},
                                                                    {null,new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*2,null),null,null,null},
                                                                    {new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*3,null),new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*3,null),null,null,null},
                                                                    {null,null,null,null,null},
                                                                    {null,null,null,null,null}};

    protected static final Block [][] ZFORM_FORM_BLOCKLISTE = {{new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*2,null),new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*2,null),null,null,null},
            {null,null,new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*3,null),new Block(Block.BLOCK_SIZE*7,Block.BLOCK_SIZE*3,null),null},
            {null,null,null,null,null},
            {null,null,null,null,null},
            {null,null,null,null,null}};

    protected static final Block [][] ZFORM_FORM_INVERTIERT_BLOCKLISTE = {{null,null,new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*2,null),new Block(Block.BLOCK_SIZE*7,Block.BLOCK_SIZE*2,null),null},
                                                                        {null,null,new Block(Block.BLOCK_SIZE*5,Block.BLOCK_SIZE*3,null),new Block(Block.BLOCK_SIZE*6,Block.BLOCK_SIZE*3,null),null},
                                                                        {null,null,null,null,null},
                                                                        {null,null,null,null,null},
                                                                        {null,null,null,null,null}};



    public Form() {

    }

    public Form (Form form,Color farbe){
        this.zentrum = form.zentrum;
        this.farbe = farbe;
        kopiere_Bloecke(form.getBlockListe());


    }

    private void kopiere_Bloecke (Block [][] blockListe){
        for (int i = 0;i<5;i++){
            for (int j =0;j<5;j++) {
                if (blockListe[i][j] != null) {
                    this.blockListe[i][j] = new Block(blockListe[i][j].getX(), blockListe[i][j].getY(),farbe);
                }
            }
        }
    }

    public Block [][] drehen(){
        Block zentrumBlock = new Block(this.blockListe[zentrum[0]][zentrum[1]].getX(),this.blockListe[zentrum[0]][zentrum[1]].getY(),farbe);
        Block [][] blockListeGedreht = new Block[5][5];
        for (int i = 0; i<5;i++){
            for (int j = 0;j<5;j++){
                Block block = this.blockListe[i][j];
                if (block != null) {
                    int xRelativ = block.getX() -zentrumBlock.getX();
                    int yRelativ = block.getY() - zentrumBlock.getY();

                    int xRotiert = zentrumBlock.getX() - yRelativ;
                    int yRotiert = zentrumBlock.getY() + xRelativ;

                    blockListeGedreht[i][j] = new Block(xRotiert,yRotiert,farbe);
                }
            }
        }
        return blockListeGedreht;
    }

    public Form (Block [][] blockListe){
        kopiere_Bloecke(blockListe);
    }

    public void seitlich_bewegen(KeyEvent event){
        int keycode = event.getKeyCode();
        int verschiebung = keycode==KeyEvent.VK_RIGHT?Block.BLOCK_SIZE:keycode==KeyEvent.VK_LEFT?-Block.BLOCK_SIZE:0;
            for (Block[] reihe : blockListe){
                for(Block block : reihe){
                    if (block != null) {
                        block.setLocation(block.getX() + verschiebung,block.getY());
                    }
                }
            }
    }

    public void uebernehme_Blockkoordinaten(Block[][] blockListe){
        for (int i = 0; i<5;i++){
            for (int j = 0; j<5;j++){
                if (blockListe[i][j] != null){
                    this.blockListe[i][j].setLocation(blockListe[i][j].getX(),blockListe[i][j].getY());
                }
            }
        }
    }

    public void fallen_lassen(float multiplicator){
        for (Block [] reihe: blockListe){
            for (Block block: reihe){
                if (block != null){
                    int dy = (int) (multiplicator*Block.BLOCK_SIZE);
                    block.setBounds(block.getX(), block.getY()+ dy,Block.BLOCK_SIZE,Block.BLOCK_SIZE);
                }
            }
        }
    }

    public void nachruecken(int distanz){
        for (Block [] reihe: this.getBlockListe()){
            for (Block block: reihe){
                if (block!= null){
                    block.setLocation(block.getX(),block.getY() +distanz);
                }
            }
        }
    }


    public boolean check_hat_explosivblock(){
        for (Block [] reihe: blockListe){
            for (Block block: reihe){
                if (block instanceof ExplosionBlock){
                    return true;
                }
            }
        }
        return false;
    }

    public Block get_untersten_Block(){
        Block untersterBlock = null;
        for (Block [] reihe: blockListe){
            for (Block block: reihe){
                if (block!= null){
                    if (untersterBlock == null || untersterBlock.getY() < block.getY()) {
                        untersterBlock = block;
                    }
                }
            }
        }
        return untersterBlock;
    }



    public void bloecke_einfrieren(){

    }

    public Block[][] getBlockListe() {
        return blockListe;
    }

    public Color getFarbe() {
        return farbe;
    }
}
