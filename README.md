# MIME

This is a project for the course CS 5010: Program Design Paradigms at Northeastern University.

## Project Description

This project is a command-line image processing program. It will read in an image from a file, perform some operations on it, and write the result to another file.
Please see the [USEME.md](USEME.md) file for a detailed description of the program.
This program also supports reading from scripts containing a sequence of commands, and writing the results to files. The program will also support conventional file formats.

## How to run

### Prerequisites

- Java 11
- JUnit 4.x

### Running the program

- Clone the repository
- Navigate to the root/src/ime directory
- Run the Main class
- When prompted, either enter

```bash
run-script <script-file-path>
```
or enter commands manually.
```
load <image-file-path> <image-name>
```
and so on.


When you are done, enter
```
quit
```
to exit the program.

**NOTE**: images are saved in the root/resources directory. New will not be saved if the image name already exists. Delete the image from the resources directory if you want to save a new image with the same name.




## Overview of the code

`ime` package contains the entire source code for the program. The `ime` package contains the following sub-packages:
model, controller, view.

This the high-level architecture diagram for the program:
![MVC](resources/MVC.png)

### Model
The model is responsible for the data and the logic of the program.

![Model](resources/MODEL.png)


### View
There are two views in the program: the command-line view and the script view. The command-line view is the default view. The script view is used when the user enters the `run-script` command.

![View](resources/View.png)

### Controller

The controller is responsible for the communication between the model and the view. It is also responsible for the execution of the commands.

![Controller](resources/Controller.png)

Every command is a class that implements the `Command` interface. The `Command` interface has a single method `execute()`. The `execute()` method is called by the controller when the user enters a command. The `execute()` method is responsible for executing the command and returning the result to the controller.




## Resource used
The image used in the program is a picture from Unsplash by photographer [Petr Slováček](https://unsplash.com/@grwood).

The link to the image is [here](https://unsplash.com/photos/a-lighthouse-on-top-of-a-hill-near-the-ocean-YrQuagwtEbM).

Under the Unsplash License, the image is free to use for any purpose.


## Changes from the original design

- Pixel wise operations are replaced with channel wise operations.