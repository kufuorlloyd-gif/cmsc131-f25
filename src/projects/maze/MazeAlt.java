package projects.maze;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MazeAlt {
    private Space[] spaces;
    private Space start;
    private Space end;
    private int width;

    public boolean load(String filename){
        File inputFile = new File(filename);
        try {
            int count = 0;
            Scanner counter = new Scanner(inputFile);
            while(counter.hasNextLine()){
                String line = counter.nextLine();
                if(width == 0){
                    String[]row = line.split(",");
                    width = row.length;
                
                }

                count += width;
            }
            counter.close();
            if (count == 0) {
                return true;
            }
            
            spaces = new Space[count];

            int j = 0;
            Scanner scan = new Scanner(inputFile);
            while (scan.hasNextLine()){
                 String[]row = scan.nextLine().trim().split(",");
                 for (int i = 0; i < row.length; i++){
                    Space sp = Space.fromString(row[i], i, j);
                    if(sp!= null){
                        spaces[coordToIndex(j,i)] = sp;
                        if (sp.isOfType(Space.Type.START)){
                            start = sp;
                        }
                        if (sp.isOfType(Space.Type.END)){
                            end = sp;
                        }
                    System.out.print(sp.getTypeString() + " ");
                    }
                    else
                    {
                     System.out.print("x ");
                    }
                 }
                    System.out.print("\n");
                 j++;

            }
            scan.close();

            return true;
        } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
        }

    }
     public boolean save (String filename){
        File file = new File(filename);
        FileWriter writer;
        try {
            writer = new FileWriter(file);

            int col = 1;
            for (int i = 0; i < spaces.length; i++ ){
                Space sp = spaces[i];
                if (sp != null){
                    writer.append(sp.getTypeString());

                } else {
                    writer.append("X");

                }
                if (col < width){
                    col++;
                    writer.append(",");
                }
                else{
                    col = 1;
                    writer.append("\n");

                }
        }


            writer.close();
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int coordToIndex(int inx, int iny){
        return inx + (iny * width);
    }
}
