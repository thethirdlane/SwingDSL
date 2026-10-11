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
@file:JvmName("FontFiddler")

package org.ttl.kotdsl.swing.ui

import java.awt.*
import javax.swing.UIDefaults
import javax.swing.UIManager
import javax.swing.plaf.FontUIResource

object FontFiddler {
    @JvmStatic
    fun setUIMinFontSize(minSize: Int): Map<String, FontUIResource> {
        val defaults = UIManager.getLookAndFeelDefaults()

        val result = HashMap<String, FontUIResource>()
        //  Build of Map of items and a Map of attributes for each item
        val enumm = defaults.keys()
        while (enumm.hasMoreElements()) {
            val key = enumm.nextElement().toString()

            val value = defaults[key]


            if (key.contains("font")) {
                val ofr = value as FontUIResource
                val nfr = if (ofr.size >= minSize)
                    ofr
                else
                    FontUIResource(ofr.fontName, ofr.style, minSize)
                result.put(key, nfr)
            }
        }

        uiFonts = result
        return result
    }

    @JvmStatic
    fun changeUIFontSizes(oldResources: Map<String, FontUIResource>, increment: Int): Map<String, FontUIResource> {
        //  Build of Map of items and a Map of attributes for each item
        val result = oldResources.mapValues { entry ->
            val key = entry.key
            val ofr = entry.value
            val size = Math.max(1, ofr.size + increment)
            val nfr = FontUIResource(ofr.fontName, ofr.style, size)
            nfr
        }
        uiFonts = result
        return result
    }

    @JvmStatic
    var uiFonts = getUIFonts()

    @JvmStatic
    fun getCurrentFont(name: String): FontUIResource? {
        val fuir = uiFonts.get(name)
        return fuir
    }

    @JvmStatic
    fun getUIFonts(): Map<String, FontUIResource> {
        val defaults: UIDefaults = UIManager.getDefaults()

        val result = HashMap<String, FontUIResource>()
        //  Build of Map of items and a Map of attributes for each item
        val enumm = defaults.keys()
        while (enumm.hasMoreElements()) {
            val key = enumm.nextElement().toString()


            if (key.contains("font")) {
                val value = defaults[key] as FontUIResource
                result.put(key, value)
            }

        }

        uiFonts = result

        return result
    }

    @JvmStatic
    fun getMinFontSizeForScreen(): Int {

        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val screenResolution = Toolkit.getDefaultToolkit().screenResolution

        val width = screenSize.width
        val minSize =
            if (width > 2000) {
                20
            } else if (width > 1500) {
                18
            } else if (width > 1000) {
                14
            } else {
                10
            }

//    println("screesize: $screenSize, resolution = $screenResolution, minSize = $minSize")
        return minSize
    }

    @JvmStatic
    public fun getMonitorSizes(): List<Pair<Int, Int>> {
        val ge: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val gs: Array<GraphicsDevice> = ge.getScreenDevices()
        val sb = StringBuilder()
        val sizes = gs.map { gd ->
            val dm: DisplayMode = gd.getDisplayMode()

            sb.append("width: ${dm.getWidth()}, height: ${dm.getHeight()}")
            dm.getWidth() to dm.getHeight()
        }.toList()

        println(sb.toString())
        return sizes
    }

    @JvmStatic
    fun getScreenCenterPixels(): Pair<Int, Int> {
        val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
        return Pair(screenSize.width / 2, screenSize.height / 2)
    }

    @JvmStatic
    fun doDefaultFontFix() {
//AP - "scale" fonts
        val minSize = getMinFontSizeForScreen()
        val newFonts = setUIMinFontSize(minSize)
        newFonts.forEach { (k: String?, v: FontUIResource?) -> UIManager.put(k, v) }
    }
}
