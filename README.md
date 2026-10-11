# A simple Java Swing DSL

This is a simple DSL written in Kotlin for creating Swing applications to
run on a JVM.  

**GridBagLayout Alert WARNING.**  While the library can be used with
any LayoutManager, it leans heavily towards `GridBagLayout`.

Not all Swing components have been implemented, but most of the basic ones
like Frame, Dialog, Panel, Buttons of various sorts, TextFields etc. are
available.  Other Swing components and/or custom components can be easily
used within the DSL.

# Example
As a very simple example, here is a snippet of code to create a frame and
populate it with two buttons:

```Kotlin
val frame = frame {
    defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    button(text = "OK", layoutConstraint = BorderLayout.NORTH)
    button(text = "Not OK", layoutConstraint = BorderLayout.SOUTH)
}
frame.pack()
frame.isVisible = true
```

The DSL consists of a bunch of globally declared factory functions like `frame`, 
`panel`, `button`, `checkbox`, etc.  You create a Gui by calling appropriate 
functions in a nested, tree like fashion. Each function will create the appropriate component **and add 
it to the surrounding container** it was called from.

The factory functions take arguments, which can vary, depending on the component.  e.g. the `button`
function has an argument call *text* which can be used to set the text of the button.

All the factory functions have an argument called `layoutConstraints`.  What you pass in here
depends on the LayoutManager you are using.  For GridBagLayout it will be an instance
of `GridBagConstraints`.  

All the factory functions also have a common last argument which can be sent as a lambda.
The `this` argument in the lambda is the component that has just been created.  (Similar
to the way the `apply` function works in Kotlin).  You can do any kind of specific initialization
of the component in the lambda, including setting any properties.

The name `Gbc` is declared as a shorter typealias for GridBagConstraints.  And a function
called `gbc` is declared as a convenience function for creating GridBagConstraints objects.

A more elaborate example which uses GridBagLayout, and shows how you can set properties
on components using either arguments or a lambda.
The example below shows both techniques, with, e.g. the Frame `name` or the 
Button `text` being set as arguments to the function calls, and properties like `background` 
and `font` being initialized in the lambda.  

```Kotlin
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
```

# Best Practices
To be taken with much salt, since the only user so far has been me.  But I have
found that the best approach seems to be to keep the Gui creation code 
*completely separate* from the application code.  The Gui code should expose
the created components, which then get used in the application code, e.g.
by attaching listeners, etc.

See an example in org.ttl.kotdsl.examples.LessElaborateExample.kt

# License
MIT License

# Warnings, Caveats etc.
This was created by me for my own use.  It has served me fairly well.
Your mileage may, and probably will, vary.

Enjoy.