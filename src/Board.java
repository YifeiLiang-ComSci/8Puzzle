/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] tiles;
    public Board(int[][] tilesd){
        tiles = tilesd;
    }

    // string representation of this board
    public String toString(){
        String str = "" + dimension() + "\n";
        for(int row = 0; row < dimension();row++){
            str += " ";
            for(int col = 0; col < dimension(); col++){
                str+= tiles[row][col];
                if(col < dimension()-1){
                    str += " ";
                }
            }
            str += "\n";
        }
        return str;
    }

    // board dimension n
    public int dimension(){
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming(){
        int count = 0;
        for(int row = 0; row < tiles.length; row++){
            for(int col = 0; col < tiles.length; col++){
                int value = tiles[row][col];
                if(value== 0){
                    continue;
                }
                if(Math.abs(col - getCol(value)) + Math.abs(row-getRow(value))!= 0){
                    count +=1;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int dist = 0;
        for(int row = 0; row < tiles.length; row++){
            for(int col = 0; col < tiles.length; col++){
                int value = tiles[row][col];
                if(value== 0){
                    continue;
                }
                dist += Math.abs(col - getCol(value)) + Math.abs(row-getRow(value));
            }
        }
        return dist;

    }
    private int getCol(int value){
        return (value - 1) % tiles.length;
    }
    private int getRow(int value){
        return (value - 1) / tiles.length;
    }

    // is this board the goal board?
    public boolean isGoal(){
        if(manhattan() == 0){
            return true;
        }else{
            return false;
        }
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;
        for (int row = 0; row < tiles.length; row ++) {
            for(int col =0; col < tiles.length;col++) {
                if (this.tiles[row][col] != that.tiles[row][col])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        int zero_row =0;
        int zero_col = 0;
        for (int row = 0; row < tiles.length; row ++) {
            for(int col =0; col < tiles.length;col++) {
                if (this.tiles[row][col] == 0) {
                    zero_row = row;
                    zero_col = col;
                }
            }
        }
        ArrayList<Board> list = new ArrayList<>();
        if(zero_row - 1 >= 0){
            int[][] copy = copy2DArray();
            int temp = copy[zero_row-1][zero_col];
            copy[zero_row-1][zero_col] = 0;
            copy[zero_row][zero_col] = temp;
            list.add(new Board(copy));
        }
        if(zero_row + 1 < dimension()){
            int[][] copy = copy2DArray();
            int temp = copy[zero_row + 1][zero_col];
            copy[zero_row + 1][zero_col] = 0;
            copy[zero_row][zero_col] = temp;
            list.add(new Board(copy));
        }
        if(zero_col - 1 >= 0){
            int[][] copy = copy2DArray();
            int temp = copy[zero_row][zero_col - 1];
            copy[zero_row][zero_col-1] = 0;
            copy[zero_row][zero_col] = temp;
            list.add(new Board(copy));
        }
        if(zero_col + 1 < dimension()){
            int[][] copy = copy2DArray();
            int temp = copy[zero_row][zero_col + 1];
            copy[zero_row][zero_col + 1] = 0;
            copy[zero_row][zero_col] = temp;
            list.add(new Board(copy));
        }
        return list;

    }
    private int[][] copy2DArray(){
        int[][] matrix = tiles;
        int [][] myInt = new int[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
            myInt[i] = matrix[i].clone();
        return myInt;
    }
    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int row1 = 0;
        int col1 = 0;
        int row2 = 0;
        int col2 = 0;
        int count = 0;
        for (int row = 0; row < tiles.length; row ++) {
            for(int col =0; col < tiles.length;col++) {
                if (this.tiles[row][col] != 0){
                    if(count == 0){
                        count = 1;
                        row1 = row;
                        col1 = col;
                    } else if (count ==1){
                        count = 2;
                        row2 = row;
                        col2 = col;
                    }
                }


            }
            if(count ==2){
                break;
            }
        }
        int[][] copy = copy2DArray();
        int temp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = temp;
        return new Board(copy);
    }

    // unit testing (not graded)
    public static void main(String[] args){}

}
