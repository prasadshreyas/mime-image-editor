# USEME

This document contains a detailed description of the program and all the features it supports.

All the below commands can be run from a script file. To run a script file, enter the following command when prompted:


```bash
run-script <script-file-path>
```


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
brightness <factor> <image-name>
```

- Create images that visualize individual R,G,B components of an image.
- Create images that visualize the value, intensity or luma of an image.
- Flip an image horizontally or vertically.
- Split a single image into 3 images representing each of the three channels.
- Combine three greyscale image into a single color image whose R,G,B values come from the three images.
- Blur or sharpen an image
- Convert an image into sepia
- Compress an image to desired quality
- Run a script containing a sequence of commands.
- Create a histogram of an image
- Level adjustment of an image
- Color Correction of an image