package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Diamantblock extends Block{

    private BufferedImage hintergrundbild;

    private final String src = "Diamantblock_Bild.png";
    public Diamantblock(int x, int y){
        super(x,y,null);
        try {
            InputStream diamant_inputstream =getClass().getResourceAsStream(src);
            hintergrundbild = ImageIO.read(diamant_inputstream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(hintergrundbild, 0, 0, getWidth(), getHeight(), this);
    }
}
