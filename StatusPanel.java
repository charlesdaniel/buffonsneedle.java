import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class StatusPanel extends JPanel {
	private JLabel message;
	private JLabel dropsTotal;
	private JLabel dropsCrossing;
	private JLabel dropsMax;
	private JLabel needleSize;
	private JLabel precision;
	private JLabel estimation;


	StatusPanel() {
		setLayout(new GridLayout(3,1));

		JPanel msgBar = new JPanel(new GridLayout(1,2));
		msgBar.add(new JLabel("Status:"));
		msgBar.add(message = new JLabel());
		message.setHorizontalAlignment(JLabel.LEFT);
		
		JPanel infoBar = new JPanel(new GridLayout(1, 4));
		infoBar.add(new JLabel("Needle Size:"));
		infoBar.add(needleSize = new JLabel());
		needleSize.setHorizontalAlignment(JLabel.LEFT);

		infoBar.add(new JLabel("Precision:"));
		infoBar.add(precision = new JLabel());
		precision.setHorizontalAlignment(JLabel.LEFT);


		JPanel statsBar = new JPanel(new GridLayout(2,5));

		statsBar.add(new JLabel("Max Drops:"));
		statsBar.add(new JLabel("Dropped:"));
		statsBar.add(new JLabel("Crossing:"));
		statsBar.add(new JLabel("Estimated PI:"));
		statsBar.add(dropsMax = new JLabel());
		statsBar.add(dropsTotal = new JLabel());
		statsBar.add(dropsCrossing = new JLabel());
		statsBar.add(estimation = new JLabel());

		add(msgBar);
		add(infoBar);
		add(statsBar);

		resetStatus();
	}

	public void resetStatus() {
		message.setText("Buffon's Needle Drop by Charles Daniel");
		dropsTotal.setText("0");
		dropsCrossing.setText("0");
		dropsMax.setText("0");
		needleSize.setText("0");
		precision.setText("0");
		estimation.setText("0");
	}

	public void setDropsTotal(int x) {
		dropsTotal.setText(Integer.toString(x));
	}
	public void setDropsMax(int x) {
		dropsMax.setText(Integer.toString(x));
	}
	public void setDropsCrossing(int x) {
		dropsCrossing.setText(Integer.toString(x));
	}
	public void setPrecision(int x) {
		precision.setText(Integer.toString(x));
	}
	public void setNeedleSize(int x) {
		needleSize.setText(Integer.toString(x));
	}
	public void setEstimation(double x) {
		estimation.setText(Double.toString(x));
	}
	public void setMessage(String x) {
		message.setText(x);
	}
}
