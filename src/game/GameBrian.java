package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameBrian extends JPanel {

    public static final int Bildwiederholrate = 30;

    private final int zufallsZahlExplosivBlock = 3;
    private float fallgeschwindigkeit;

    private int geloeschteReihenInsgesamt;

    private int punktzahl;

    private int level;
    private long zeitinMs;
    private Spielfeld spielfeld;

    private Form form;
    private Form naechste_form;
    private Form [] formen = new Form[7];
    private Color [] farben;

    private boolean isSpielende;


    public GameBrian (){
        this.setBounds(0,0,10*Block.BLOCK_SIZE,20*Block.BLOCK_SIZE);
        GeradeForm gerade = new GeradeForm();
        formen[0] = gerade;
        QuadratForm quadrat = new QuadratForm();
        formen[1] = quadrat;
        LForm l_form = new LForm();
        formen[2] = l_form;
        LInvertiertForm l_form_invertiert = new LInvertiertForm();
        formen[3] = l_form_invertiert;
        ZForm z_form = new ZForm();
        formen[4] = z_form;
        ZInvertiertForm z_form_invertiert = new ZInvertiertForm();
        formen[5] = z_form_invertiert;
        PyramideForm pyramide = new PyramideForm();
        formen[6] = pyramide;
        fallgeschwindigkeit = 1;
        level = 1;
        zeitinMs = System.currentTimeMillis();
        farben = new Color[]{Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN, Color.CYAN, Color.PINK, Color.ORANGE};
        naechste_form = form_generieren();
        setLayout(null);
        spielfeld = new Spielfeld();


    }

    @Override
    protected void paintComponent(Graphics g) {
        //Linien zeichnen
        super.paintComponent(g);
        for (int i = 0; i<28;i++){
            for (int j = 0; j<10; j++){
                g.setColor(Color.BLACK);
                g.drawRect(j*Block.BLOCK_SIZE,i*Block.BLOCK_SIZE,Block.BLOCK_SIZE,Block.BLOCK_SIZE);
            }
        }

    }

    public void diamantblock_generieren(){
        HashMap<Block,Diamantblock> blockMap =spielfeld.diamantBlockGenerieren();
       for (Map.Entry<Block,Diamantblock> entry: blockMap.entrySet()){
           this.remove(entry.getKey());
           this.add(entry.getValue());
           entry.getValue().repaint();
       }
    }

    public void generiere_naechste_Form(){
        form = naechste_form;
        naechste_form = form_generieren();
        form_anzeigen();
    }


    public boolean form_bewegen(){
        int schritt = (int) (fallgeschwindigkeit*Block.BLOCK_SIZE/ Bildwiederholrate);
        boolean beruehrtBoden = spielfeld.beruehrt_boden(form, schritt);
        if (!beruehrtBoden) {
            form.fallen_lassen(fallgeschwindigkeit/30);
            return true;
        }
        spielfeld.setze_auf_Boden(form);
        return false;
    }

//Threads

    private Form form_generieren (){
        Random random = new Random();
        int index = random.nextInt(1,8)-1;
        int indexFarbe = random.nextInt(1,7)-1;
        int wKeitExplosivblock = random.nextInt(1,11);
        Color farbe = farben[indexFarbe];
        Form generierte_Form = new Form(formen[index],farbe);
        if (wKeitExplosivblock == zufallsZahlExplosivBlock){
             int blocknummer = random.nextInt(1,5);
            generierte_Form.explosivblock_erzeugen(blocknummer);
        }

    return generierte_Form;
    }

    private void form_anzeigen(){
        for (Block[] reihe: form.getBlockListe()){
            for (Block block: reihe){
                if (block!= null){
                    this.add(block,0);
                }
            }
        }
    }


    public void loeschen_und_nachruecken(){
        List<Block> geloeschteBloecke = spielfeld.loeschen_und_nachruecken();
        for (Block block: geloeschteBloecke){
            this.remove(block);
        }
        repaint();
        punktzahl_berechnen(geloeschteBloecke.size()/10);
        geloeschteReihenInsgesamt += geloeschteBloecke.size()/10;

    }


    public void levelWechseln(){
        if (geloeschteReihenInsgesamt >= 10){
            level++;
            fallgeschwindigkeit = 1 + 0.5f*level;
            geloeschteReihenInsgesamt = 0;
        }
        if (level%5 == 0){

        } else if (level%10==0) {

        }

    }


    public boolean check_spielende(){
        return isSpielende;
    }

    private void punktzahl_berechnen(int reihen){
        switch (reihen){
            case 0:
                break;
            case 1:
                punktzahl = punktzahl + 40*level;
                break;
            case 2:
                punktzahl = punktzahl + 100*level;
                break;
            case 3:
                punktzahl = punktzahl +300*level;
                break;
            default:
                punktzahl = punktzahl +1200*level;
                break;
        }
    }

    private void highscores_anzeigen(){

    }

    public void block_soll_explodieren(){
            List<Block> blockListe = spielfeld.block_durchfuehren_explodieren();
            for (Block block: blockListe){
                this.remove(block);
            }
            repaint();
    }

    public void eingabe_verarbeiten(KeyEvent event){
        if (isSpielende){
            return;
        }
        int keyCode = event.getKeyCode();
        if (keyCode==KeyEvent.VK_LEFT) {
            if (!spielfeld.beruehrt_seitlich(form,-Block.BLOCK_SIZE)) {
                form.seitlich_bewegen(event);
            }
        } else if (keyCode==KeyEvent.VK_RIGHT) {
            if (!spielfeld.beruehrt_seitlich(form,Block.BLOCK_SIZE)) {
                form.seitlich_bewegen(event);
            }
        } else if (keyCode==KeyEvent.VK_DOWN) {


        } else if (keyCode==KeyEvent.VK_SPACE) {
            Block [][] gedrehteBlockliste = form.drehen();
            if (gedrehteBlockliste != null && spielfeld.position_valid(gedrehteBlockliste)){
                form.uebernehme_Blockkoordinaten(gedrehteBlockliste);
            }
        }
    }

    public void taste_losgelassen(KeyEvent event){
        if (isSpielende){
            return;
        }
        int keyCode = event.getKeyCode();
        if (keyCode==KeyEvent.VK_DOWN) {
            for (; ; ) {
                if (!spielfeld.beruehrt_boden(form, (int) (fallgeschwindigkeit * Block.BLOCK_SIZE))) {
                    form.fallen_lassen(fallgeschwindigkeit);
                } else {
                    spielfeld.setze_auf_Boden(form);
                    break;
                }
            }
        }
    }

    public void uebernehme_bloecke(){
        try {
            spielfeld.uebernehme_bloecke(form);
        }catch (FormOutOfBoundException e){
            isSpielende =true;
        }

    }

    public Form getNaechste_form() {
        return naechste_form;
    }

    public int getPunktzahl() {
        return punktzahl;
    }
}
