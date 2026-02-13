/*OnBot Java Notes


TERMINOLOGY

Class - Group of code that is shared. All code in Java is inside of a class.
Method - Group of code. Basically a set of instructions. Located inside a class. Mainly a function
Package - Directory where you store your class files. Effectively a house address on a directory.
Directory - a tree map that organizes files and other resources on a computer or network.
String- A class that stores the literal words and letters
Int- type of var that stores numbers
Double- Stores float numbers (decimal)
Boolean- True/False information
Scope- Region in the class where a var is recognized (usually defined by the {} it was made in)

=========================================
Methods and what they do

OPMode - The TeleOp and Auto period. Must have 2 methods. On is init() function and the other is the loop() function.
Init() - Runs once when Driver presses "INIT" (Initialize)
Loop() - Runs repeatedly after Driver presses "PLAY" but stops when Driver presses "STOP"
--------------------------------------
In OPMode, there are 3 optional methods; init_loop(), start(), and stop()

Init_loop() - Runs repeatedly after Driver presses "INIT" but stops when Driver presses "PLAY"
Start() - Runs once when Driver presses "PLAY"
Stop() - Runs once when Driver presses "STOP"
--------------------------------------
Telemetry- Collects data real-time (Data includes: Crash logs, user activity, performance, etc.)
Telemetry.addData- Adds the output of the code into the console log

&&- And comparator
||- Or comparator
!- Not comparator

Return- The method will "borrow" data, modify it, and the return will "return" the data back to us for other code that need that data

Public void- Class A can access this method even though it is located in Class B.
Private void- Class A CANNOT access this method because it is in Class B

=========================================
THE GAMEPAD (Make sure it is in X mode)

-On the trigger buttons, there is an analog that will be given a value when...
    -If pressed all the way in, gives analog of 1
    -If not pressed, gives analog of 0
-If you hold in between, analog will be a decimal number between 0 and 1 like 0.50, 0.25, and 0.75


-The joysticks have 2 analogs, one for the y-axis, and one for the x-axis
    -When pushed up, the y-analog will be -1
    -When pushed down, the y-analog will be 1
    -When pushed left, the x-analog will be -1
    -When pushed right, the x-analog will be 1
When the joystick is in the middle, the analog will be 0















































*/