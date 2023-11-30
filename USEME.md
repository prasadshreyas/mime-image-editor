# USEME

This document contains a detailed description of the program and all the features it supports.


Command-Line Arguments
---

This program supports three modes of operation, each triggered by specific command-line arguments. Below are the instructions for each:

Script File Execution Mode:

Use: 

```bash
java -jar Program.jar -file [path-of-script-file]
```
Description: Executes a script file and then shuts down the program. Replace [path-of-script-file] with the actual path to your script file.
Interactive Text Mode:

Use: 
```bash
java -jar Program.jar -text
```

Description: Opens the program in an interactive text mode, allowing the user to input and execute scripts line by line.
Graphical User Interface (GUI) Mode:

Use: 
```bash
java -jar Program.jar
```



Description: Launches the program's graphical user interface. This mode is also activated by double-clicking the JAR file.
Invalid Arguments
If any command-line arguments other than the above are used, the program will display an error message and terminate.



---

All the below commands can be run from a script file. To run a script file, enter the following command when prompted:



## Overview of the program

The program will support the following operations:

- Load an image from an ASCII PPM, JPG or PNG file.

```
load <image-file-path> <image-name>
```

- Save an image to an ASCII PPM, JPG or PNG file.

```
save <image-name> <file-path>
```

- Brighten or darken an image.

```
brighten <increment> <image-name> <dest-image-name>
```

- Create images that visualize individual R,G,B components of an image.

```
red-component <image-name> <dest-image-name>
green-component <image-name> <dest-image-name>
blue-component <image-name> <dest-image-name>
```

- Create images that visualize the value, intensity or luma of an image.
```
value-component <image-name> <dest-image-name>
intensity-component <image-name> <dest-image-name>
luma-component <image-name> <dest-image-name>
```

- Flip an image horizontally or vertically.

```
horizontal-flip <image-name> <dest-image-name>
vertical-flip <image-name> <dest-image-name>
```


- Split a single image into 3 images representing each of the three channels.

```
rgb-split <image-name> <dest-image-name-red> <dest-image-name-green> <dest-image-name-blue>
```

- Combine three greyscale image into a single color image whose R,G,B values come from the three images.

```
rgb-combine <image-name-red> <image-name-green> <image-name-blue> <dest-image-name>
```

- Blur or sharpen an image

```
blur <image-name> <dest-image-name>
sharpen <image-name> <dest-image-name>
```

- Convert an image into sepia

```
sepia <image-name> <dest-image-name>
```


- Compress an image to desired quality

```
compress <quality> <image-name> <dest-image-name> 
```

- Run a script containing a sequence of commands.
    
```
run-script <script-file-path>
```
- Create a histogram of an image
    
```
histogram <image-name> <dest-image-name>
```
- Level adjustment of an image

```
level-adjustment <black-value> <mid-value> <white-value> <image-name> <dest-image-name>
```
- Color Correction of an image

```
color-correction <image-name> <dest-image-name>
```

Also, the program will support the split view feature. The split view feature will allow the user to view two images side by side.
Features supported by the split view:
- blur
- sharpen
- sepia
- grayscale
- color correction
- level adjustment

For example, to view the blur of an image in split view, enter the following command:

```
blur <image-name> <dest-image-name> -split <p>
```

where `p` is the percentage of the image that will be displayed on the left side of the split view. The remaining percentage of the image will be displayed on the right side of the split view.
