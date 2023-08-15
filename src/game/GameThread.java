package game;

public class GameThread extends Thread{

    private GameBrian gameBrian;
    private Vorschau vorschau;

    public GameThread(GameBrian gameBrian, Vorschau vorschau){
        this.gameBrian=gameBrian;
        this.vorschau = vorschau;
    }

    @Override
    public void run() {
        while (!gameBrian.check_spielende()) {
            gameBrian.generiere_naechste_Form();
            vorschau.setPunktzahl(gameBrian.getPunktzahl() +"");
            vorschau.vorschau_anzeigen(gameBrian.getNaechste_form());
            while (gameBrian.form_bewegen()) {
                try {
                    Thread.sleep(1000/30);
                } catch (InterruptedException e) {
                   System.out.println(e.getMessage());
                }
            }
            gameBrian.uebernehme_bloecke();
            gameBrian.loeschen_und_nachruecken();
            gameBrian.block_soll_explodieren();
            gameBrian.diamantblock_generieren();
            gameBrian.levelWechseln();
        }
    }
}
