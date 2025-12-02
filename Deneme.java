import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Deneme {
    
    public static String outLoc="C:\\Users\\PC\\Desktop\\Coding\\Misc\\outputs folder\\DENEME (color process)\\image.png";
    public static String outLoc2="C:\\Users\\PC\\Desktop\\Coding\\Misc\\outputs folder\\DENEME (color process)\\image2.png";

    public static void main(String[] args) {
        int[][][] myImage= new int[30][30][3]; // Effectively creating a 30*30 pixel image with rgb channels.
        // (REC function can't handle too much calls)
        assignRandomlyColoredImage(myImage);
        saveImage(myImage, outLoc);
        changeColorChannelImage_REC(10, 0, myImage, 0);
        saveImage(myImage, outLoc2);
        
        
    }

    // method below turns the given image to a randomly colored image (for each pixel).
    public static void assignRandomlyColoredImage(int[][][] image){

        for (int i = 0; i < image.length; i++) {
            for (int j = 0 ; j < image[0].length; j++){

                image[i][j]= createRandomColor();
                
            }
        }

    }

    
    public static int[] createRandomColor(){ // The method for a completely random color.

        Random rn=new Random();
        int r=rn.nextInt(256);
        int g=rn.nextInt(256);
        int b=rn.nextInt(256);
        int[] color= {r,g,b};
        return color;
    }

    
    // method below is recursive because i wanted to practice recursive methods.
    // method below is void type because arrays are inputted by reference anyway.
    // please keep in mind that index is not dependent on the last dimension of the array, as we don't iterate on colors like this.
    // the first two dimensions are the ones the index iis calculated upon, and last dimension is always color.
    // So, basically, this index is to find the appopriate PIXEL.

    // From experimantation, if the image is large enough, recursive function (despite containing no error)
    // causes a StackOverflowError. This is a clear sign to stay away from recursive functions
    // when dealing with a task that is bound to repeat itself thousands of times.
    // an iterative function should be a better choice.
    public static void changeColorChannelImage_REC(float weight,int colorIndex,int[][][] input, int index){ 


        if (index < ((input.length)*(input[0].length))){ // ensuring that we are not "out of bounds" of the image.
            int[] indexArray= intIndexTo_3DarrayLocation(input, index);
            
            input[indexArray[0]][indexArray[1]][colorIndex]= multiplyColorChannel(weight, input[indexArray[0]][indexArray[1]][colorIndex]);
            // done the color transform with code above.
            changeColorChannelImage_REC(weight, colorIndex, input, index+1);
        }        


    }
    // method below exists so the index parameter in changecolorchannelImage can actually connect to matching 3d array index.
    // please keep in mind that index is not dependent on the last dimension of the array, as we don't iterate on colors like this.
    // the first two dimensions are the ones the index iis calculated upon, and last dimension is always color.
    // So, basically, this index is to find the appopriate PIXEL.
    public static int[] intIndexTo_3DarrayLocation(int[][][] array, int index){
        
        if (index < ((array.length)*(array[0].length))){ // ensuring that we are not "out of bounds" of the image.

            int index0=index/(array[0].length);
            int index1=index%(array[0].length);
            int[] output= {index0,index1};
            return output;
        }   
        System.out.println("OutOfBounds Exception at indexFinder.");
        return null;

    }

    public static int multiplyColorChannel(float weight, int colorValue){ // This method is seperate for setting boundaries between 0-255
        return Math.clamp((int)(colorValue*weight), 0, 255);
    }



    // Taken from GEMINI!!!
    public static void saveImage(int[][][] pixels, String outputPath) {
        // Assume array structure is pixels[y][x][channels]
        int height = pixels.length;
        int width = pixels[0].length;

        // Create a BufferedImage
        // TYPE_INT_RGB indicates we are packing Red, Green, Blue into an int
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = pixels[y][x][0]; // Red
                int g = pixels[y][x][1]; // Green
                int b = pixels[y][x][2]; // Blue

                // COMBINE CHANNELS:
                // We use bitwise shifting to pack 3 integers (0-255) into one.
                // Red moves 16 bits to the left, Green 8 bits, Blue stays.
                int rgb = (r << 16) | (g << 8) | b;

                // Set the pixel color at x, y
                image.setRGB(x, y, rgb);
            }
        }

        try {
            // Write the file
            File outputFile = new File(outputPath);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved successfully: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }
}
