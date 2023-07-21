package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ExplosionBlock extends Block {
    public static final int explosionsRadius = 3;

    private final String src = "Explosivfass_Bild.png";

    private BufferedImage hintergrundbild;

    public ExplosionBlock (int x, int y){
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
