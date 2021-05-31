/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
class Node{
    Board board;
    int moves;
    Node prev;
    public Node(Board boardd, int movesd,Node prevd){
        board = boardd;
        moves = movesd;
        prev = prevd;
    }
}
public class Solver {

    // find a solution to the initial board (using the A* algorithm)


    private Board initialBoard;
    private boolean solved;
    private int numMoves;
    private Node lastNode;

    public Solver(Board initial){
        numMoves = -1;
        solved = false;
        initialBoard = initial;
        MinPQ<Node> origPriorityQueue  =  new MinPQ<Node>(new NodeComparator());
        origPriorityQueue.insert(new Node(initial,0,null));
        lastNode = null;
        MinPQ<Node> twinPriorityQueue = new MinPQ<Node>(new NodeComparator());
        twinPriorityQueue.insert(new Node(initial.twin(),0,null));
        boolean twinFinish = false;
        boolean origFinish = false;
        while (true)
        {
            Node orig = origPriorityQueue.delMin();
            Node twin = twinPriorityQueue.delMin();
            if(orig.board.isGoal()){
                origFinish = true;
                numMoves = orig.moves;
                lastNode = orig;
                break;
            }
            if(twin.board.isGoal()){
                twinFinish = true;
                break;
            }

            for(Board nei: orig.board.neighbors()){

                if(orig.prev == null || !nei.equals(orig.prev.board))
                    origPriorityQueue.insert(new Node(nei,1+orig.moves,orig));
            }

            for(Board nei: twin.board.neighbors()){
                if(twin.prev == null ||!nei.equals(twin.prev.board))
                    twinPriorityQueue.insert(new Node(nei,1+twin.moves,twin));
            }
        }



    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        if(numMoves >= 0) {
            return true;
        }else{
            return false;
        }
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        return numMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if(!isSolvable()){
            return null;
        }
        ArrayList<Board> list = new ArrayList<Board>();
        Node currentNode = lastNode;
        while(currentNode != null){
            list.add(currentNode.board);
            currentNode = currentNode.prev;
        }
        Collections.reverse(list);
        return list;

    }

    // test client (see below)
    public static void main(String[] args){}

}
class NodeComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2){
        if(n1.board.manhattan() + n1.moves < n2.board.manhattan() + n2.moves ){
            return -1;
        } else if(n1.board.manhattan() + n1.moves  > n2.board.manhattan() + n2.moves ) {
            return 1;
        }
        return 0;
    }
        }