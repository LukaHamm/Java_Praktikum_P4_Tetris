package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Steuerung extends JFrame {

    private static GameBrian gameBrian;
    private static GameThread gameThread;
    private JPanel contentPane;

    private JScrollPane scrollPane;


    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Steuerung frame = new Steuerung();
                    frame.setVisible(true);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public Steuerung (){
        Vorschau vorschau = new Vorschau();
        gameBrian = new GameBrian();
        gameThread = new GameThread(gameBrian, vorschau);
        scrollPane = new JScrollPane(gameBrian);
        scrollPane.setBounds(0,0,11*Block.BLOCK_SIZE,
                20*Block.BLOCK_SIZE);

        //verticalScrollBar.setEnabled(false);
        gameBrian.setPreferredSize(new Dimension(10 * Block.BLOCK_SIZE, 28 * Block.BLOCK_SIZE));
        gameThread.start();
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,16*Block.BLOCK_SIZE +Block.BLOCK_SIZE,
                20*Block.BLOCK_SIZE + Block.BLOCK_SIZE);
        setLayout(new GridLayout(1,2));
        contentPane = new JPanel();
        //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(scrollPane);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(185);
        verticalScrollBar.setEnabled(false);
        verticalScrollBar.setVisible(false);
        contentPane.add(vorschau);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameBrian.eingabe_verarbeiten(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                gameBrian.taste_losgelassen(e);
            }
        });
    }


}
