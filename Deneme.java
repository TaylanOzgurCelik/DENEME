import java.util.Random;


public class Deneme {
    


    public static void main(String[] args) {
        int[][][] myImage= new int[500][500][3]; // Effectively creating a 500*500 pixel image with rgb channels.

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

}
