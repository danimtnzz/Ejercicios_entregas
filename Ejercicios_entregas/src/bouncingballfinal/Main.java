package bouncingballfinal;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class Main extends JFrame implements KeyListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private Stage stage = new Stage(10);

	Main(String[] args) throws FontFormatException, IOException {
		super("Bouncing Ball");
		addKeyListener(this);
		addWindowListener(this);
		addMouseListener(stage);
		setIgnoreRepaint(true);
		setUndecorated(true);
		setAlwaysOnTop(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFont(Font.createFont(Font.PLAIN, Main.class.getResourceAsStream("/Digital Play Italic St.ttf")).deriveFont(290f));
	}

	private void start() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = ge.getScreenDevices();
		GraphicsDevice device = devices.length == 2 ? devices[1] : ge.getDefaultScreenDevice();
		device.setFullScreenWindow(this);
		createBufferStrategy(2);
		
		
		String input = JOptionPane.showInputDialog(Main.this,"Introduce el número de bolas con las que vas a jugar:");
        int numBalls;
        try {
            numBalls = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El valor introducido no es un número válido.");
            return;
        }
        stage.start(this,numBalls);
    }

	public static void main(final String[] args) throws IOException {
		SwingUtilities.invokeLater(() -> {
			try {
				new Main(args).start();
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_P:
			stage.togglePause();
			break;
		case KeyEvent.VK_ESCAPE:
			stage.stop();
			dispose();
			System.exit(0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}