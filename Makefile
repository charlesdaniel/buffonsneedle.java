all: build run_gui

build:
	javac GraphicSimulator.java
	javac Simulator.java

run_gui:
	java GraphicSimulator

run_text:
	java Simulator

clean:
	rm -f *.class
