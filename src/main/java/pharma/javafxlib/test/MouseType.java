package pharma.javafxlib.test;

public enum MouseType {
    PRIMARY(0),SECONDARY(1),NULL(-1);;
    private  final int status;
    MouseType(int status){
        this.status=status;
    }


    public int getStatus() {
        return status;
    }
}
