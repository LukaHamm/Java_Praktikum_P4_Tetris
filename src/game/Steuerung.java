package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class Steuerung extends JFrame {

    private static GameBrian gameBrian;
    private static GameBrian.GameThread gameThread;
    private JPanel contentPane;

    private static JScrollPane scrollPane;

    private JButton spielStarten;
    private JButton spielBeenden;

    private JPanel menue;


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
                    JScrollBar jScrollBar = scrollPane.getVerticalScrollBar();
                    jScrollBar.setValue(4*Block.BLOCK_SIZE);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                    JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                    verticalScrollBar.setValue(185);


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
        gameThread = gameBrian. new GameThread(gameBrian, vorschau);
        scrollPane = new JScrollPane(gameBrian);
        scrollPane.setBounds(0,0,11*Block.BLOCK_SIZE,
                20*Block.BLOCK_SIZE);

        //verticalScrollBar.setEnabled(false);
        gameBrian.setPreferredSize(new Dimension(10 * Block.BLOCK_SIZE, 28 * Block.BLOCK_SIZE));
        //gameThread.start();
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
        contentPane.add(vorschau);
        menue = new JPanel();
        menue.setBounds(0,0,16*Block.BLOCK_SIZE +Block.BLOCK_SIZE,
            20*Block.BLOCK_SIZE + Block.BLOCK_SIZE);
        menue.setLayout(null);
        spielStarten = new JButton("Spiel starten");
        spielStarten.setBounds(6*Block.BLOCK_SIZE,4*Block.BLOCK_SIZE,4*Block.BLOCK_SIZE,Block.BLOCK_SIZE);
        spielStarten.setFocusable(false);
        spielStarten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameThread.start();
                menue.setVisible(false);
                spielBeenden.setVisible(false);
                spielStarten.setVisible(false);
            }
        });

        spielBeenden = new JButton("Spiel beenden");
        spielBeenden.setBounds(6*Block.BLOCK_SIZE,6*Block.BLOCK_SIZE,4*Block.BLOCK_SIZE,Block.BLOCK_SIZE);
        spielBeenden.setFocusable(false);
        spielBeenden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menue.add(spielStarten);
        menue.add(spielBeenden);
        contentPane.add(menue,0);

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
