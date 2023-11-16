# Checklist

### Image Compression
- [ ] Implement the Haar Wavelet Transform for image compression including averaging and differencing.
- [ ] Develop the transform function to compress a sequence recursively.
- [ ] Create the invert function to decompress the sequence recursively.
- [ ] Apply 2D Haar wavelet transform techniques for images (haar and invhaar functions).
- [ ] Integration of lossy compression by implementing thresholding.
- [ ] Implement a method to calculate the compression ratio (C).
- [ ] Add the `compress percentage image-name dest-image-name` script command implementation.

### Image Histograms
- [ ] Develop a function to compute the histogram data for an image.
- [ ] Visualize the histogram as a 256x256 image showing line graphs for the RGB channels.
- [ ] Implement the `histogram image-name dest-image-name` script command.

### Color Correction
- [ ] Create an algorithm for color correction based on aligning meaningful histogram peaks.
- [ ] Implement the `color-correct image-name dest-image-name` script command.

### Levels Adjustment
- [ ] Implement the levels adjustment technique based on quadratic curve fitting.
- [ ] Add the functionality to apply levels adjustment via script command, including shadow, mid, and highlight control.

### Operation Preview with Split View
- [ ] Implement the split view preview feature for operations: blur, sharpen, sepia, grayscale, color correction, and levels adjustment.
- [ ] Allow an optional percentage parameter for the split view in the script commands for these operations.

### Command-Line Script File Input
- [X] Enable the processing of a script file via a command-line argument (e.g., `-file 
  name-of-script.txt`).
- [X] Run and process the script file commands automatically on program execution with the 
  correct command-line argument.
- [X] Maintain interactive command entry when no script file is specified.
- [X] Update README file with all the supported script commands.

### Design Considerations & Documentation
- [ ] Analyze and document design changes required for the new features in the README file.
- [ ] Ensure all the developments adhere to the MVC architecture.

### Testing
- [ ] Create unit tests for new features to ensure functionality and robustness.

### Project Build and JAR Creation
- [ ] Configure the IDE to create a JAR file that includes dependencies.
- [ ] Verify the JAR file works correctly standalone and via the command-line arguments.

### Example Resources and Documentation
- [ ] Prepare example images and script files to showcase all features.
- [ ] Place example outputs for each new feature in the res/ folder.
- [ ] Include images that show the results of split view operations.
- [ ] Update the class diagram to reflect the updated project structure.
- [ ] Ensure README file contains comprehensive documentation of changes and justifications.
- [ ] Ensure USEME file provides clear instructions on how to use supported script commands.

### Final Submission
- [ ] Ensure all necessary files, including the source code, tests, resources (images and script file), JAR file, updated class diagram, README, and USEME files, are included in the submission.
- [ ] Check that all files are correctly named and placed in the appropriate folders as specified.


----

## Some notes on refactoring
Certainly! Let's break down how this refactored design works. We have structured the code into interfaces each focusing on a specific area of functionality. This helps us separate concerns and maintain the Single Responsibility Principle, where each class or interface is in charge of one part of the functionality.

Here's how the system is organized:

1. **IImageProcessingModel Interface**: This is the main interface for the model that the controller of the MVC architecture can interact with. It provides methods to load and retrieve images and perform high-level operations such as transformation, filtering, and color manipulation. The controller doesn't need to know how these operations are implemented; it only knows that the model offers these capabilities.

2. **IImage Interface**: This represents the basic image data structure that other components will work with. It provides methods to retrieve image dimensions and the `BufferedImage` itself. Any image operation that can be performed should work with the `IImage` interface rather than a specific implementation.

3. **IImageTransformer Interface**: This interface contains methods to transform images such as flipping an image horizontally or vertically. It is supposed to work on an `IImage` instance and return a new `IImage` instance with the transformation applied. The actual algorithms for these transformations will be implemented by classes that implement this interface.

4. **IImageFilter Interface**: Here, you have methods that apply various filters like brightness adjustment (brighten), blur, and sharpening. These methods take an `IImage` instance and produce a new modified `IImage`. The implementation class will use algorithms to apply the desired effect on the image.

5. **IColorManipulator Interface**: This interface defines methods for manipulating the color aspects of an image such as applying a sepia tone or splitting the image into RGB components. Similar to other interfaces, it works with `IImage` objects and modifications result in new `IImage` objects.

6. **IImageIO Interface**: This interface abstracts the input/output operations for images, meaning loading from and saving to the file system or other media. These operations will be implemented in accordance with the underlying storage mechanism (could be a filesystem, a network location, etc).

Now, how does all this work together in a cohesive system?

- The **Controller** in your MVC setup calls methods on the `IImageProcessingModel` to perform operations on images. This model acts as a facade, hiding the complexities of the internal workings.

- The **Model** internally uses implementations of `IImage`, `IImageTransformer`, `IImageFilter`, and `IColorManipulator` based on the operation it needs to perform. The model may combine several of these operations to carry out more complex tasks.

- Each **operation-specific interface** such as `IImageFilter`, `IImageTransformer`, etc., defines methods for their area of work, and any class implementing these interfaces will provide the actual algorithmic logic.

- The **IImageIO** interface allows the model to load and save images without caring about the storage details, making the saving and loading processes interchangeable (could be from a local drive, a database, or a web source).

This design not only adheres to sound principles of object-oriented design but also makes the system highly flexible and maintainable. If you ever need to add new functionality or change an existing algorithm, you would create or modify implementations of these interfaces without making changes to the other parts of the system. This modular design makes testing easier by allowing you to mock interfaces or inject different implementations for testing without changing the surrounding code.