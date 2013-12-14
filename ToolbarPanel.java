import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

public class ToolbarPanel extends JPanel {
	private StartStopButton startButton;
	private PauseResumeButton pauseButton;
	private JButton quitButton;

	private LabeledSlider needleSizeSlider;
	private LabeledSlider numDropsSlider;
	private LabeledSlider precisionSlider;
	private LabeledSlider delaySlider;

	private Floor floor;
	private GraphicSimulator gsim;

	private Thread runningThread;


	class LabeledSlider extends JPanel implements ChangeListener {
		String originalLabel;
		JLabel label;
		JSlider slider;

		LabeledSlider(String labelString, int minValue, int maxValue, int tickSpacing, int orientation) {
			this.originalLabel = labelString;
			if(orientation == JSlider.HORIZONTAL) {
				setLayout(new GridLayout(2,1));
			}
			else {
				setLayout(new GridLayout(1,2));
			}

			slider = new JSlider(orientation, minValue, maxValue, minValue);
			slider.setMajorTickSpacing(tickSpacing);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.addChangeListener(this);

			add(label = new JLabel());
			add(slider);

			setValue(minValue);
		}

		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			if(!source.getValueIsAdjusting()) {
				label.setText(originalLabel +" "+ Integer.toString(getValue()));
			}
		}

		int getValue() {
			return (int)slider.getValue();
		}

		void setValue(int value) {
			label.setText(originalLabel +" "+ Integer.toString(value));
			slider.setValue(value);
		}
	}


	class FloorScaler extends LabeledSlider implements ChangeListener {
		FloorScaler() {
			super("Floor Zoom:", 1, 10, 1, JSlider.HORIZONTAL);

			setValue(5);
			slider.addChangeListener(this);
			floor.setPixelScaling(5);
			floor.repaint();
		}

		public void stateChanged(ChangeEvent e) {
			super.stateChanged(e);
			JSlider source = (JSlider)e.getSource();
			if(!source.getValueIsAdjusting()) {
				int scaling = (int) source.getValue();
				floor.setPixelScaling(scaling);
				floor.repaint();
			}
		}
	}


	class StartStopButton extends JButton implements ActionListener, Runnable {
		private byte toggle;

		StartStopButton() {
			super("Start");
			toggle = 0;

			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			if(toggle == 0) {
				pauseButton.resetButton();

				floor.clean();
				toggle = 1;
				setText("Stop");

				runningThread = new Thread(this);
				runningThread.start();
			}
			else {
				gsim.terminate();				

				resetButton();
			}
		}

		public void run() {
			pauseButton.setText("Pause");

			gsim.simulate((int)needleSizeSlider.getValue(), ((int)numDropsSlider.getValue() * 10000), (int)precisionSlider.getValue(), (int)delaySlider.getValue());

			runningThread = null;
			resetButton();
		}

		public void resetButton() {
			toggle = 0;
			setText("Start");

			pauseButton.resetButton();
		}

	}

	class PauseResumeButton extends JButton implements ActionListener {
		private byte toggle;

		PauseResumeButton() {
			super("Clear");
			toggle = 0;

			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			if(runningThread != null) {
				if(toggle == 0) {
					gsim.pause();

					setText("Resume");
					toggle = 1;
				}
				else {
					gsim.resume();

					setText("Pause");
					toggle = 0;
				}
			}
			else {
				gsim.reset();
			}
		}

		public void resetButton() {
			toggle = 0;
			gsim.resume();
			setText("Clear");
		}
			
	}

	ToolbarPanel(GraphicSimulator gsim, Floor floor) {
		this.gsim = gsim;
		this.floor = floor;

		runningThread = null;

		setLayout(new GridLayout(2,3));

		needleSizeSlider = new LabeledSlider("Needle Size:", 2, 10, 1, JSlider.HORIZONTAL);
		numDropsSlider = new LabeledSlider("Max Drops (x10000):", 1, 10, 1, JSlider.HORIZONTAL);
		precisionSlider = new LabeledSlider("Precision:", 1, 5, 1, JSlider.HORIZONTAL);
		delaySlider = new LabeledSlider("Drop Delay (ms):", 0, 1000, 500, JSlider.HORIZONTAL);

		startButton = new StartStopButton();
		pauseButton = new PauseResumeButton();
		quitButton = new JButton("Quit");

		quitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			}
		);

		startButton.disable();
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(quitButton);

		add(needleSizeSlider);
		add(precisionSlider);
		add(new FloorScaler());

		add(numDropsSlider);
		add(delaySlider);
		add(buttonPanel);

	}

}
