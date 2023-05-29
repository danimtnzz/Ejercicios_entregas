package bouncingballfinal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Stage implements MouseListener {
    private LinkedList<Ball> balls = new LinkedList<>();
    private LinkedList<Ball> removed = new LinkedList<>();
    private int numBalls;
    private boolean paused;
    private Thread t;
    private volatile boolean running;
    private JFrame frame;
    private boolean showGreeting;
    private long greetingStartTime;
    private static final long GREETING_DURATION = 4000000000L;
    private Color redColor = new Color(255, 0, 0);

    public Stage(int numBalls) {
        this.numBalls = numBalls;
        
    }

    public synchronized void togglePause() {
        if (paused) {
            paused = false;
            notify();
        } else
            paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public void start(JFrame frame, int numBalls) {
        this.frame = frame;
        this.numBalls = numBalls;
        for (int i = 0; i < numBalls; i++)
            balls.add(Ball.random(this));
        (t = new Thread(this::loop)).start();
    }

    public void stop() {
        running = false;
        synchronized (this) {
            if (paused) {
                paused = false;
                notify();
            }
        }
        try {
            t.join();
        } catch (InterruptedException e) {
        }
    }

    public void loop() {
        long t0;
        long t1;
        long lapse;
        t0 = System.nanoTime();
        running = true;
        showGreeting = true;
        greetingStartTime = t0;
        while (running) {
            synchronized (this) {
                if (paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                    if (running)
                        t0 = System.nanoTime();
                    else
                        break;
                }
            }
            t1 = System.nanoTime();
            lapse = t1 - t0;
            t0 = t1;
            next(lapse);
            render();
        }
    }

    private synchronized void next(long lapse) {
        balls.forEach(b -> b.move(lapse));
        Iterator<Ball> i = removed.iterator();
        while (i.hasNext()) {
            Ball b = i.next();
            if (b.vanish(lapse)) {
                i.remove();
            numBalls--;
            }
        }

        
    }

    private void render() {
        BufferStrategy bufferStrategy = frame.getBufferStrategy();
        Graphics2D g = null;
        try {
            g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Font f = g.getFont();

            synchronized (this) {
                for (Ball b : balls) {
                    b.paint(g);
                }
                for (Ball b : removed)
                    b.paint(g);

                if (showGreeting && System.nanoTime() - greetingStartTime < GREETING_DURATION) {
                    g.setColor(Color.WHITE);
                    Rectangle2D rect = f.getStringBounds("HOLA", g.getFontRenderContext());
                    g.drawString("HOLA", (int) ((getWidth() - rect.getWidth())) / 2,
                            (int) ((getHeight() - rect.getHeight()) / 2) + g.getFontMetrics().getAscent());
                }

                // Mostrar el número de bolas en rojo
                g.setColor(redColor);
                g.setFont(f.deriveFont(Font.BOLD, 24));
                g.drawString("Número de bolas: " + numBalls, 10, 30);
            }
        } finally {
            if (g != null)
                g.dispose();
        }
        bufferStrategy.show();
    }

    public int getWidth() {
        return frame.getWidth();
    }

    public int getHeight() {
        return frame.getHeight();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse: " + e.getX() + ", " + e.getY());
        boolean correctBallClicked = false;
        if (balls.size() > 0 && balls.getLast().in(e.getX(), e.getY())) {
            synchronized (this) {
                removed.add(balls.removeLast());
                correctBallClicked = true;
            }
        }
        if (!correctBallClicked) {
            synchronized (this) {
                balls.add(Ball.random(this)); 
                numBalls++; 
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
