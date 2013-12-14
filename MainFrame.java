import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class MainFrame extends JFrame {
	public int width;
	public int height;

	public MainFrame(int width, int height) {
		this.width = width;
		this.height = height;

		setTitle("Buffon's Needle Drop by Charles Daniel ("+width+"x"+height+")");
		setSize(width, height);

	}

}
