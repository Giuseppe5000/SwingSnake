/*
 * @author Tutino Giuseppe
 * @version 1.1
 * */

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;

public class Main extends JPanel implements KeyListener, ActionListener
{
        //Dimensione di un quadrato
        final int X = 30;
        final int Y = 30;

        //limite della mappa
        final int max_X = X*33;
        final int max_Y = Y*20;

        //Coordinate della testa
        int testa_x = X*8;
        int testa_y = Y*3;

        //Numero di quadrati occupati
        int lunghezza;

        //Direzione
        final int UP = 1;
        final int LEFT = 2;
        final int DOWN = 3;
        final int RIGHT = 4;
        int direzione = RIGHT;

        //Mela
        Random r = new Random();
        int mela_x = (r.nextInt(33)+1)*X;
        int mela_y = (r.nextInt(20)+1)*X;

        //Vettore che contiene tutti i quadrati occupati dal serpentee
        Vector<Snake> Snake = new Vector<>();

        //Velocità del serpente
        int speed = 300;
        Timer t;

        public Main() {
                super();
                setFocusable(true);
                addKeyListener(this);

                Snake.add(new Snake(testa_x,testa_y,X)); //primo elemento è la testa;
                Snake.add(new Snake(X*7,Y*3,X));
                Snake.add(new Snake(X*6,Y*3,X));
                Snake.add(new Snake(X*5,Y*3,X));
                Snake.add(new Snake(X*4,Y*3,X));

                t = new Timer(speed,this);
                t.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
                lunghezza = Snake.size();
                super.paintComponent(g);

                setBackground(Color.BLACK);
                //Disegno il background
                for ( int x = X; x <= 1000; x += X )
                        for ( int y = Y; y <= 600; y += Y )
                                g.drawRect( x, y, X, Y );

                //Disegno il serpente
                g.setColor(Color.WHITE);
                for(int i=0; i<lunghezza; i++){
                        g.fillRect(Snake.elementAt(i).x,Snake.elementAt(i).y,X,Y);
                }
                g.setColor(Color.RED);
                g.fillOval(mela_x,mela_y,X,Y);
        }

        //
        public void Key_snake(int x)
        {
                testa_x = Snake.elementAt(0).x;
                testa_y = Snake.elementAt(0).y;
                //Il ciclo ovviamente esclude il primo elemento, ossia la testa
                for (int i=1;i<lunghezza;i++) {
                        if(testa_x == Snake.elementAt(i).x && testa_y == Snake.elementAt(i).y) {
                                t.stop();
                                setVisible(false);
                                JOptionPane.showMessageDialog(null,"Hai perso, \nhai totalizzato "+ lunghezza + " punti!");
                                System.exit(0);
                        }
                }
                switch(x){
                        case KeyEvent.VK_DOWN -> {
                                //Controllo se può essere fatto il movimento
                                if (direzione != UP) {
                                        direzione = DOWN;
                                        Down();
                                }
                        }
                        case KeyEvent.VK_UP -> {
                                if (direzione != DOWN) {
                                        direzione = UP;
                                        Up();
                                }
                        }
                        case KeyEvent.VK_RIGHT -> {
                                if (direzione != LEFT) {
                                        direzione = RIGHT;
                                        Right();
                                }
                        }
                        case KeyEvent.VK_LEFT -> {
                                if (direzione != RIGHT) {
                                        direzione = LEFT;
                                        Left();
                                }
                        }
                }

                if(Snake.elementAt(0).x == mela_x && Snake.elementAt(0).y == mela_y) {

                        //Coda del serpente
                        int coda_x = Snake.elementAt(lunghezza-1).x;
                        int coda_y = Snake.elementAt(lunghezza-1).y;

                        //Switch per capire dove inserire la nuova coda
                        switch (direzione){
                                case RIGHT -> Snake.add(new Snake(coda_x-X, coda_y,X));
                                case LEFT ->  Snake.add(new Snake(coda_x+X, coda_y,X));
                                case UP ->    Snake.add(new Snake(coda_x, coda_y+Y,X));
                                case DOWN ->  Snake.add(new Snake(coda_x, coda_y-Y,X));
                        }

                        //Cambio coordinate della mela
                        mela_x = (r.nextInt(33)+1)*X;
                        mela_y = (r.nextInt(20)+1)*Y;
                }

        }

        private void Right()
        {
                //Cicla il serpente
                for (int z=lunghezza-1; z>0; z--) {

                        //x e y dell'ultimo elemento diventano il penultimo
                        Snake.elementAt(z).x = Snake.elementAt(z-1).x;
                        Snake.elementAt(z).y = Snake.elementAt(z-1).y;
                }
                //Il serpente va a destra
                Snake.elementAt(0).x += X;

                //Limite della mappa superato
                for(int i=0;i<lunghezza;i++) {
                        if(Snake.elementAt(i).x > max_X) {
                                Snake.elementAt(i).x = X;
                        }
                }

                repaint();
        }

        private void Left()
        {
                for (int z=lunghezza-1; z>0; z--) {
                        Snake.elementAt(z).x = Snake.elementAt(z-1).x;
                        Snake.elementAt(z).y = Snake.elementAt(z-1).y;
                }
                Snake.elementAt(0).x -= X;

                //Limite della mappa superato
                for(int i=0;i<lunghezza;i++) {
                        if(Snake.elementAt(i).x < X) {
                                Snake.elementAt(i).x = max_X;
                        }
                }

                repaint();
        }

        private void Down()
        {
                for (int z=lunghezza-1; z>0; z--) {
                        Snake.elementAt(z).x = Snake.elementAt(z-1).x;
                        Snake.elementAt(z).y = Snake.elementAt(z-1).y;
                }
                Snake.elementAt(0).y += Y;

                //Limite della mappa superato
                for(int i=0;i<lunghezza;i++) {
                        if(Snake.elementAt(i).y > max_Y) {
                                Snake.elementAt(i).y = Y;
                        }
                }

                repaint();
        }

        private void Up()
        {
                for (int z=lunghezza-1; z>0; z--) {
                        Snake.elementAt(z).x = Snake.elementAt(z-1).x;
                        Snake.elementAt(z).y = Snake.elementAt(z-1).y;
                }
                Snake.elementAt(0).y -= Y;

                //Limite della mappa superato
                for(int i=0;i<lunghezza;i++) {
                        if(Snake.elementAt(i).y < Y) {
                                Snake.elementAt(i).y = max_Y;
                        }
                }

                repaint();
        }

        @Override
        public void keyTyped(KeyEvent e){}

        @Override
        public void keyPressed(KeyEvent e){
                int x = 0;

                //Nuova direzione
                switch (e.getKeyCode()){
                        case KeyEvent.VK_UP -> x = 1;
                        case KeyEvent.VK_LEFT -> x = 2;
                        case KeyEvent.VK_DOWN -> x = 3;
                        case KeyEvent.VK_RIGHT -> x = 4;
                }

                //Se la direzione è la stessa non succede niente
                if(direzione != x)
                        Key_snake(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e){}

        @Override
        public void actionPerformed(ActionEvent e)
        {
                switch (direzione){
                        case UP -> Key_snake(KeyEvent.VK_UP);
                        case DOWN -> Key_snake(KeyEvent.VK_DOWN);
                        case RIGHT -> Key_snake(KeyEvent.VK_RIGHT);
                        case LEFT -> Key_snake(KeyEvent.VK_LEFT);
                }
        }

        public static void main(String[] args)
        {
                JFrame f = new JFrame();
                f.setSize(1050, 670);
                f.setResizable(false);

                f.add(new Main());

                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
        }
}
