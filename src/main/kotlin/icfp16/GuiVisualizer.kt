package icfp16

import icfp16.data.Problem
import icfp16.problem.ProblemParser
import icfp16.visualizer.Visualizer
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import java.awt.Image
import javax.swing.*

private fun createAndShowGUI() {
  //Create and set up the window.
  val frame = JFrame("Visualizer")

  frame.contentPane = JPanel(MigLayout("", "[100px][100px]"))

  frame.contentPane.size = Dimension(400, 400)

  frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

  val imageLabel = JLabel(ImageIcon())
  frame.contentPane.add(imageLabel, "w 300px!, h 300px!, center")

  val foldsLabel = JLabel(ImageIcon())
  frame.contentPane.add(foldsLabel, "w 300px!, h 300px!, center")

  val input = JTextPane()
  frame.contentPane.add(JScrollPane(input), "w 600px!, h 300px!, cell 0 1 2")

  val button = JButton("Visualize!")
  frame.contentPane.add(button, "w 600px!, h 50px!, cell 0 2 2")

  button.addActionListener {
    val state = ProblemParser().parseState(input.text)

    val image = Visualizer().visualizationOf(Problem(emptyList(), emptyList()), state)
    imageLabel.icon = ImageIcon(image.getScaledInstance(300, 300, Image.SCALE_FAST))

    val folds = Visualizer().visualizeFolds(state)
    foldsLabel.icon = ImageIcon(folds.getScaledInstance(300, 300, Image.SCALE_FAST))
  }

  //Display the window.
  frame.pack()
  frame.isVisible = true
}

fun main(args: Array<String>) {
  SwingUtilities.invokeLater { createAndShowGUI() }
}
