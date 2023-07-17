package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Spielfeld {

    private Block [][] spielfeld_blockliste = new Block[20][10];

    public List<Block> loeschen_und_nachruecken (){
        List<Integer> reihen_zum_loeschen = new ArrayList<>();
        List<Block> bloeckeGeloeschteReihe = new ArrayList<>();
        for (int i = 0; i<20; i++){
            int bloeckeInReihe = 0;
            for (int j = 0; j<10; j++){
                Block spielblock = this.spielfeld_blockliste[i][j];
                if (spielblock instanceof Diamantblock){
                    break;
                }
                if (spielblock != null){
                    bloeckeInReihe++;
                }
            }
            if (bloeckeInReihe==10){
                reihen_zum_loeschen.add(i);
            }
        }
        for (int i = 0; i<reihen_zum_loeschen.size();i++){
            for (int j = 0; j<10;j++){
                Block spielfeldBlock = spielfeld_blockliste[reihen_zum_loeschen.get(i)][j];
                bloeckeGeloeschteReihe.add(spielfeldBlock);
                for (int k = reihen_zum_loeschen.get(i)-1;k>= 0; k--){
                    Block spielfeldBlockObereReihe = spielfeld_blockliste[k][j];
                    if (spielfeldBlockObereReihe != null){
                        spielfeldBlockObereReihe.setLocation(spielfeldBlockObereReihe.getX(),spielfeldBlockObereReihe.getY() +Block.BLOCK_SIZE);
                    }
                    spielfeld_blockliste[k+1][j] = spielfeldBlockObereReihe;
                }
            }
        }
        return bloeckeGeloeschteReihe;
    }

    public HashMap<Block,Diamantblock> diamantBlockGenerieren(){
        HashMap<Block,Diamantblock> blockMap = new HashMap<>();
        for (int i = 0; i<10;i++){
            int blockzaehler = 0;
            for (int j = 0;j<20;j++){
                Block spielblock = this.spielfeld_blockliste[j][i];
                if (blockzaehler >= 8 && spielblock != null){
                       int x = spielblock.getX();
                       int y = spielblock.getY();
                       Diamantblock diamantblock = new Diamantblock(x,y);
                       this.spielfeld_blockliste[j][i] = diamantblock;
                       blockMap.put(spielblock,diamantblock);
                }
                if (spielblock != null){
                    blockzaehler++;
                }

            }
        }
        return blockMap;
    }

    public Block [] block_durchfuehren_explodieren(){
        return null;
    }

    public boolean beruehrt_boden(Form form, int schritt){
        for(Block [] reihe: form.getBlockListe()){
            for (Block block: reihe){
                if (block != null){
                    if (block.getY() +Block.BLOCK_SIZE +schritt >= Block.BLOCK_SIZE*24){
                        return true;
                    }
                    for (Block [] spielfeld_reihe: this.spielfeld_blockliste){
                        for (Block spielfeld_block: spielfeld_reihe){
                            if (spielfeld_block != null){
                                if (block.getY() + Block.BLOCK_SIZE + schritt >= spielfeld_block.getY() && block.getX() == spielfeld_block.getX() && (block.getY() + schritt-spielfeld_block.getY()) < Block.BLOCK_SIZE){
                                    return true;
                                }
                            }
                        }
                    }

                }
            }
        }
        return false;
    }


    public boolean beruehrt_seitlich(Form form,int schritt){
        for(Block [] reihe: form.getBlockListe()){
            for (Block block: reihe){
                if (block != null){
                    if (block.getX() +schritt >= Block.BLOCK_SIZE*10 || block.getX() +schritt < 0){
                        return true;
                    }
                    for (Block [] spielfeld_reihe: this.spielfeld_blockliste){
                        for (Block spielfeld_block: spielfeld_reihe){
                            if (spielfeld_block != null){
                                if (block.getX() + schritt == spielfeld_block.getX() && block.getY() +Block.BLOCK_SIZE >= spielfeld_block.getY() && (block.getY()-spielfeld_block.getY()) < Block.BLOCK_SIZE){
                                    return true;
                                }
                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    public boolean position_valid(Block [][] blockListe){
        for (Block [] reihe: blockListe){
            for (Block block: reihe){
                if (block != null){
                    if (block.getY() +Block.BLOCK_SIZE > Block.BLOCK_SIZE*24 || block.getX() + Block.BLOCK_SIZE > 10*Block.BLOCK_SIZE || block.getY() < Block.BLOCK_SIZE*4 || block.getX() < 0){
                        return false;
                    }
                    for (Block [] spielfeldreihe: this.spielfeld_blockliste){
                        for (Block spielfeldblock: spielfeldreihe){
                            if (spielfeldblock != null){
                                if (spielfeldblock.getY() == block.getY() && spielfeldblock.getX() == block.getX()){
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
          return true;
    }


    public void setze_auf_Boden(Form form){
        int kleinstedistanz = 24*Block.BLOCK_SIZE;
        for (Block [] reihe: form.getBlockListe()){
            for (Block block:reihe){
                if (block != null){
                    for (Block [] spielfeldReihe:this.spielfeld_blockliste){
                        for (Block spielfeldblock: spielfeldReihe){
                            if (spielfeldblock != null && spielfeldblock.getX() == block.getX()){
                                int distanz = spielfeldblock.getY() - Block.BLOCK_SIZE -block.getY();
                                if (distanz >= 0 && distanz < kleinstedistanz){
                                    kleinstedistanz = distanz;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (kleinstedistanz == Block.BLOCK_SIZE*24){
            kleinstedistanz = Block.BLOCK_SIZE*24 -Block.BLOCK_SIZE- form.get_untersten_Block().getY();
        }
        form.nachruecken(kleinstedistanz);
    }

    public void uebernehme_bloecke(Form form){
        for (Block [] reihe: form.getBlockListe()){
            for (Block block: reihe){
                if (block != null){
                    int indexX = (block.getX())/Block.BLOCK_SIZE;
                    int indexY = (block.getY() -4*Block.BLOCK_SIZE)/Block.BLOCK_SIZE;
                    spielfeld_blockliste[indexY][indexX] = block;
                }
            }
        }
    }

}
