## Conditions
1. A rooted android device
2. Or sign you app with system signature file

## Direction for use

### How to show fps?
First, you need grant float window permission by activate 'Settings.ACTION_MANAGE_OVERLAY_PERMISSION'
Then, click 'begin' button to start.

#### How to stop?
Click the 'end' button

#### How it works?
1. Using 'dumpsys window | grep mFocusedWindow' command, to get ${current window name}
2. Then using 'dumpsys SurfaceFlinger --latency ${current window name}' to get frame timestap.
3. You will get {1+127} lines after execute command 2, the first line is device refresh rate, and the next 127 line
is 127 frames timestap



#### Modify data
1. If you want to modify fps information, please change SurfaceFlingerInfo.parseFPS() method.
2. If you want to modify float window UI or content, please change FloatInfoView class.




if there is any question about this project, you can contact me by Email: Crystal1017Chen@gmail.com