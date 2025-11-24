package projects.maze;

public class Space {
    public enum Type {
        PATH,START,END
        
    }
    private Type type;
    private boolean visited;
    private int x,y;
    public Space(
        Type intype,
        int inx, int iny
    ) {
        type = intype;
        visited = false;
        x = inx;
        y = iny;

    }
    public static Space fromString(String intype, int inx, int iny){
        Space result = switch (intype.toLowerCase()){
            case "o" -> new Space(Type.PATH, inx, iny);
            case "s" -> new Space(Type.START, inx, iny);
            case "e" -> new Space(Type.END, inx, iny);
            default -> null;
            
        };

        return result;
    }

    public String getTypeString(){
        return switch (type){
            case Type.PATH -> "O" ;
            case Type.START -> "S" ;
            case Type.END -> "E" ;
            default -> null;
            
        };
    }

    public boolean isOfType(Type intype){
        return type == intype;

    }

}
