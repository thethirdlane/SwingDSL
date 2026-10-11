/*
Copyright (c) 2026 The Third Lane

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package org.org.ttl.kotdsls.swing

import org.junit.jupiter.api.Test
import org.ttl.kotdsl.swing.Gbc
import org.ttl.kotdsl.swing.button
import org.ttl.kotdsl.swing.frame
import org.ttl.kotdsl.swing.gbc
import org.ttl.kotdsl.swing.makeComponent
import org.ttl.kotdsl.swing.makeProto
import org.ttl.kotdsl.swing.ui.FontFiddler
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JTextPane
import javax.swing.UIManager

/**
 * @author whynot
 */
class TestSwingDsl {

    @Test
    fun testSwingDsl() {
        val frame = frame(name = "Test Swing DSL") {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE

            button(text = "OK", layoutConstraint = BorderLayout.NORTH)
            button(text = "Not OK", layoutConstraint = BorderLayout.SOUTH)
        }

        frame.pack()
        frame.isVisible = true
        val i = 0
    }

    @Test
    fun wtf() {

    }
}

fun testSimpleSwing(args: Array<String>) {
    val frame = frame {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        button(layoutConstraint = BorderLayout.NORTH, text = "OK")
        button(layoutConstraint = BorderLayout.SOUTH, text = "Not OK")
    }

    frame.pack()
    frame.isVisible = true
}

fun testLessSimpleSwing(args: Array<String>) {
    val frame = frame(name = "Another Example") {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        layout = GridBagLayout()

        var gbc = gbc(w = Gbc.REMAINDER, weightx = 1.0, fill = Gbc.HORIZONTAL)
        button(layoutConstraint = gbc, text = "OK")

        gbc = gbc(anchor = Gbc.WEST, fill = Gbc.NONE, insets = Insets(10, 10, 10, 10))
        button(layoutConstraint = gbc, text = "Not OK") {
            font = font.deriveFont(20)
            background = Color.RED
        }
    }

    frame.size = Dimension(500, 200)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}

fun testInitFunction(args: Array<String>) {
    val frame = frame {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        makeComponent<JButton>(layoutConstraint = BorderLayout.NORTH) {
            text = "OK Init"
        }
        makeComponent<JButton>(layoutConstraint = BorderLayout.SOUTH) {
            text = "Not OK Init"
        }
    }

    frame.pack()
    frame.isVisible = true
}

val infoFont = Font("TimesRoman", Font.BOLD, 30)

fun testTextPane() {
    val tpWrapper: JTextPane.() -> Unit = {
        font = infoFont
        contentType = "text/html";
        isEditable = false;
        background = null;
        border = null;
    }
    val frame = frame {
        val tp = makeComponent<JTextPane>(layoutConstraint = BorderLayout.CENTER, preInit = tpWrapper) {
            text = "<html><p style=\"color:red\">Boo</p></html>"
        }
    }

    frame.setSize(200, 200);
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.isVisible = true;
}

fun testElaborate(args: Array<String>) {
    val frame = frame {
        layout = GridBagLayout()

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val buttonCommon: (JButton) -> JButton = makeProto<JButton> {
            font = Font("Helvetica", Font.BOLD, 25)
        }

        val buttonCommonWithReceiver: JButton.() -> Unit = {
            font = Font("Helvetica", Font.BOLD, 25)
        }

        var gbc = gbc(x = 0, y = 0)
        buttonCommon(button(text = "OK Elaborate", layoutConstraint = gbc))
        gbc = gbc(x = 0, y = 1)
        button(text = "Not OK Elaborate", layoutConstraint = BorderLayout.NORTH,
            postInit = buttonCommonWithReceiver)
    }

    frame.pack()
    frame.isVisible = true
}

object RunTests {

    @JvmStatic
    fun main(args: Array<String>) {


        //AP Set Fonts
        val minSize = FontFiddler.getMinFontSizeForScreen()
        val newFonts =
            FontFiddler.setUIMinFontSize(minSize)
        newFonts.forEach({ k, v -> UIManager.put(k, v) })

//        testSimpleSwing(args)
        testLessSimpleSwing(args)
//        testInitFunction(args)
//        testTextPane()
    }
}