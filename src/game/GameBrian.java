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
    private float fallgeschwindigkeit;
    private int level;
    private long zeitinMs;
    private Spielfeld spielfeld;

    private JPanel vorschau;
    private Form form;
    private Form naechste_form;
    private Form [] formen = new Form[7];
    private Color [] farben;

    public boolean pfeiltaste_unten_gedrueckt;


    private GameThread gameThread;

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
        fallgeschwindigkeit = 0.5f;
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
        JScrollPane scrollPane = (JScrollPane) getParent().getParent();
        JScrollBar jScrollBar = scrollPane.getVerticalScrollBar();
        jScrollBar.setValue(4*Block.BLOCK_SIZE);
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
        fallgeschwindigkeit=1;
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
        Color farbe = farben[indexFarbe];


    return new Form(formen[index],farbe);
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
            repaint();
        }
    }



    private void check_spielende(){

    }

    private void punktzahl_berechnen(){

    }

    private void highscores_anzeigen(){

    }

    public void block_soll_explodieren(Block block, int radius){

    }

    public void eingabe_verarbeiten(KeyEvent event){
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
        spielfeld.uebernehme_bloecke(form);
    }

    public Form getNaechste_form() {
        return naechste_form;
    }
}
