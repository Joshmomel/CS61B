package hw2;

public class Direction {
    public Position theNum;
    public Position top;
    public Position bottom;
    public Position left;
    public Position right;

    public Direction(Position theNum) {
        this.theNum = theNum;
        this.top = new Position(theNum.x, theNum.y + 1);
        this.bottom = new Position(theNum.x, theNum.y -1);
        this.left = new Position(theNum.x - 1, theNum.y);
        this.right = new Position(theNum.x + 1,theNum.y);
    }


}
