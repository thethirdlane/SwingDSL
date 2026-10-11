package org.ttl.kotdsl.swing

import org.ttl.kotdsl.swing.ui.FontFiddler
import java.awt.Font
import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.UIManager

object TestTextArea {

   @JvmStatic
   fun main(args: Array<String>) {
      //AP Set Fonts
      val minSize = FontFiddler.getMinFontSizeForScreen()
      val newFonts =
         FontFiddler.setUIMinFontSize(minSize)
      newFonts.forEach({ k, v -> UIManager.put(k, v) })
      testTextPane()
   }

   fun testTextPane() {
      val frame = frame(name = "TestTextPane") {
         layout = GridBagLayout()

         var gbc = gbc(w = Gbc.REMAINDER, weightx = 1.0, weighty = 1.0, fill = Gbc.BOTH)
         val tp = textArea(layoutConstraint = gbc, text = "A lazy cow side stepped the moon.") {
            columns = 20
            rows = 10
            border = BorderFactory.createEmptyBorder()
            font = Font("Helvitica", Font.PLAIN, 28)
         }
      }

      frame.pack()
//      frame.size = Dimension(500, 500)
      frame.setLocationRelativeTo(null)
      frame.isVisible = true
   }
}