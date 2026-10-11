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

package org.ttl.kotdsl.swing


import java.awt.Container
import java.awt.Dimension
import java.awt.Frame
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JLayeredPane
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JPopupMenu
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.JTextPane
import kotlin.reflect.full.createInstance

var defaultId = 0
//var compMap = mutableMapOf<String, Container>()

inline fun Container.panel(id: String = "Panel" + ++defaultId,
                           layoutConstraint: Any? = null,
                           preInit: JPanel.() -> Unit = {},
                           postInit: JPanel.() -> Unit = {},
                           init: JPanel.() -> Unit = {}): JPanel =
        makeComponent(id, layoutConstraint, preInit, postInit, init)

inline fun Container.layeredPane(id: String = "LayeredPane" + ++defaultId,
                                 layoutConstraint: Any? = null,
                                 preInit: JLayeredPane.() -> Unit = {},
                                 postInit: JLayeredPane.() -> Unit = {},
                                 init: JLayeredPane.() -> Unit = {}): JLayeredPane =
        makeComponent(id, layoutConstraint, preInit, postInit, init)

inline fun Container.textPane(id: String = "TextPane" + ++defaultId,
                              layoutConstraint: Any? = null,
                              preInit: JTextPane.() -> Unit = {},
                              postInit: JTextPane.() -> Unit = {},
                              init: JTextPane.() -> Unit = {}): JTextPane =
        makeComponent(id, layoutConstraint, preInit, postInit, init)

inline fun Container.scrollPane(id: String = "ScrollPane" + ++defaultId,
                                layoutConstraint: Any? = null,
                                preInit: JScrollPane.() -> Unit = {},
                                postInit: JScrollPane.() -> Unit = {},
                                init: JScrollPane.() -> Unit = {}): JScrollPane =
        makeComponent(id, layoutConstraint, preInit, postInit, init)

inline fun Container.label(id: String = "Label" + ++defaultId,
                           text: String = "",
                           layoutConstraint: Any? = null,
                           preInit: JLabel.() -> Unit = {},
                           postInit: JLabel.() -> Unit = {},
                           init: JLabel.() -> Unit = {}): JLabel {
    val component = makeComponent(id, layoutConstraint, preInit, postInit, init)
    //Test to make sure text was not initialized in init
    if (component.text.isNullOrEmpty()) {
        component.text = text
    }
    //Post Initialization
    component.postInit()
    return component
}

inline fun Container.checkBox(id: String = "CheckBox" + ++defaultId,
                            text: String = "",
                            layoutConstraint: Any? = null,
                            preInit: JCheckBox.() -> Unit = {},
                            postInit: JCheckBox.() -> Unit = {},
                            init: JCheckBox.() -> Unit = {}): JCheckBox {
    val component = makeComponent(id, layoutConstraint, preInit, postInit, init)
    //Test to make sure text was not initialized in init
    if (component.text.isNullOrEmpty()) {
        component.text = text
    }

    //Post Initialization
    component.postInit()
    return component
}

inline fun Container.button(id: String = "Button" + ++defaultId,
                            text: String = "",
                            layoutConstraint: Any? = null,
                            preInit: JButton.() -> Unit = {},
                            postInit: JButton.() -> Unit = {},
                            init: JButton.() -> Unit = {}): JButton {
    val component = makeComponent(id, layoutConstraint, preInit, postInit, init)
    //Test to make sure text was not initialized in init
    if (component.text.isNullOrEmpty()) {
        component.text = text
    }

    //Post Initialization
    component.postInit()
    return component
}

inline fun Container.textField(id: String = "TextField" + ++defaultId,
                               columns: Int = 10,
                               text: String = "",
                               layoutConstraint: Any? = null,
                               preInit: JTextField.() -> Unit = {},
                               postInit: JTextField.() -> Unit = {},
                               init: JTextField.() -> Unit = {}): JTextField {
    val component = makeComponent(id, layoutConstraint, preInit, postInit, init)
    //Test to make sure text was not initialized in init
    if (component.text.isNullOrEmpty()) {
        component.text = text
    }
    component.columns = columns

    //Post Initialization
    component.postInit()
    return component

}

inline fun Container.textArea(id: String = "TextArea" + ++defaultId,
                              text: String = "",
                              layoutConstraint: Any? = null,
                              preInit: JTextArea.() -> Unit = {},
                              postInit: JTextArea.() -> Unit = {},
                              init: JTextArea.() -> Unit = {}): JTextArea {
    val component = makeComponent(id, layoutConstraint, preInit, postInit, init)
    //Test to make sure text was not initialized in init
    if (component.text.isNullOrEmpty()) {
        component.text = text
    }
    //Post Initialization
    component.postInit()
    return component

//    compMap.put(id, textField)
}

inline fun Container.passwordField(id: String = "PasswordField" + ++defaultId,
                                   size: Int = 10,
                                   layoutConstraint: Any? = null,
                                   preInit: JPasswordField.() -> Unit = {},
                                   postInit: JPasswordField.() -> Unit = {},
                                   init: JPasswordField.() -> Unit = {}): JPasswordField =
        makeComponent(id, layoutConstraint, preInit, postInit, init)

/**
 * Generic makeComponent Function.  Called from more convenience functions
 * in this class.  Can also be called directly.  For example, to make a
 * JButton:
 * val button = makeComponent<JButton>(...) {
 *     text = "OK"
 *      ...
 * }
 *
 */
inline fun <reified T : Container> Container.makeComponent(id: String = "" + ++defaultId,
                                                           layoutConstraint: Any? = null,
                                                           preInit: T.() -> Unit = {},
                                                           postInit: T.() -> Unit = {},
                                                           init: T.() -> Unit = {}): T {
    //Reified gives you access to the class, which you can use
    //to reflectively create an instance of the component.
    val component = T::class.createInstance()
    //Pre Initialization
    component.preInit()

    component.init()
    layoutConstraint?.let {
        add(component, layoutConstraint)
    } ?: run {
        add(component)
    }

    return component
}

//Menus
inline fun JFrame.menuBar(id: String = "MenuBar" + ++defaultId,
                          init: JMenuBar.() -> Unit = {}): JMenuBar {
    val menuBar = JMenuBar()
    menuBar.init()

    jMenuBar = menuBar

    return menuBar
}


inline fun JMenuBar.menu(id: String = "Menu" + ++defaultId,
                         label: String,
                         init: JMenu.() -> Unit = {}): JMenu {

    val menu = JMenu(label)
    menu.init()

    add(menu)
    return menu
}


/**
 * Have to make this JComponent to have both
 * JMenu and JPopupMenu use this function
 */
inline fun JComponent.item(id: String = "MenuItem" + ++defaultId,
                           label: String,
                           init: JMenuItem.() -> Unit = {}): JMenuItem {

    val menuItem = JMenuItem(label)
    menuItem.init()

    add(menuItem)
    return menuItem
}

inline fun JButton.actionListener(noinline listener: (e: ActionEvent) -> Unit) {
    addActionListener(listener)
}

inline fun JTextField.actionListener(noinline listener: (e: ActionEvent) -> Unit) {
    addActionListener(listener)
}

//inline fun <T> proto(tf: T, protoInit: T.() -> Unit) {
//    tf.protoInit()
//}

/**
 * Create a "prototype" initializer that can be used to do
 * common initializations.  example Usage
 * <pre>
 *     //common Button initialization
 *     val commonButton = makeProto<JButton> {
 *        font = Font("Arial", Font.BOLD, 20)
 *        foreground = Color.RED
 *     }
 *
 *     //And then make some Buttons
 *     commonButton(button(layoutConstraint = BorderLayout.CENTER, text = "CenterButton"))
 *     commonButton(button(layoutConstraint = BorderLayout.NORTH, text = "NorthButton"))
 * </pre>
 *
 * @param commonInit An extension function to do common initialization on some type T.
 *                   We need to make it crossinline because we are going to call it in another
 *                   lambda (the one we are returning).  So we need to make sure that whatever
 *                   code is passed in as commonInit does *NOT* have a 'return' statement in it.
 *                   If it does, the crossinline here will make that 'return' a compile error.
 *
 * @return A function that will accept some type T and call the
 *  commonInit function on it.  The returned function will return the object
 *  on which it is invoked, allowing for chaining if needed.
 */
inline fun <T> makeProto(crossinline commonInit: T.() -> Unit): (T) -> T {
    return { comp: T ->
        comp.commonInit();
        //Return the argument
        comp
    }
}

inline fun frame(id: String = "Frame" + ++defaultId,
                 name: String = "",
                 dimension: Dimension? = null,
                 init: JFrame.() -> Unit = {}): JFrame {
    val frame = JFrame(name)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.init()
    dimension?.let {
        frame.size = it
    } ?: run {
        frame.pack()
    }
    return frame
}

inline fun layeredPane(id: String = "LayeredPane" + ++defaultId,
                       name: String = "",
                       dimension: Dimension? = null,
                       init: JLayeredPane.() -> Unit = {}): JLayeredPane {
    val component = JLayeredPane()
    component.init()
    dimension?.let {
        component.size = it
    }
    return component
}

inline fun panel(id: String = "Panel" + ++defaultId,
                 init: JPanel.() -> Unit = {}): JPanel {
    val panel = JPanel()
    panel.init()

    return panel
}

inline fun popupMenu(id: String = "PopupMenu" + ++defaultId,
                     label: String,
                     init: JPopupMenu.() -> Unit = {}): JPopupMenu {
    val popup = JPopupMenu()
    popup.init()

    return popup
}

inline fun dialog(id: String = "Dialog" + ++defaultId,
                  label: String,
                  parent: Frame? = null,
                  init: JDialog.() -> Unit = {}): JDialog {

    val dialog = JDialog(parent, label)
    dialog.init()
    return dialog
}

typealias Gbc = GridBagConstraints
typealias Gbl = GridBagLayout

fun gbc(x: Int = Gbc.RELATIVE, y: Int = Gbc.RELATIVE,
        w: Int = 1, h: Int = 1,
        weightx: Double = 0.0, weighty: Double = 0.0,
        anchor: Int = Gbc.CENTER, fill: Int = Gbc.NONE,
        insets: Insets = Insets(0, 0, 0, 0),
        ipadx: Int = 0, ipady: Int = 0) = Gbc(x, y, w, h, weightx,
        weighty, anchor, fill, insets, ipadx, ipady)

//ExtensionFunction on GridBagConstraints to let us do a copy
fun GridBagConstraints.copy(
    x: Int = this.gridx,
    y: Int = this.gridy,
    w: Int = this.gridwidth,
    h: Int = this.gridheight,
    weightx: Double = this.weightx,
    weighty: Double = this.weighty,
    anchor: Int = this.anchor,
    fill: Int = this.fill,
    insets: Insets = this.insets,
    ipadx: Int = this.ipadx,
    ipady: Int = this.ipady
): GridBagConstraints {
    val gbc = gbc(x, y, w, h, weightx, weighty, anchor, fill, insets, ipadx, ipady)
    return gbc
}
