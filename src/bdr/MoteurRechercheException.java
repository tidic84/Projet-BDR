package bdr;

public class MoteurRechercheException extends Exception{
    public MoteurRechercheException(String message){
        super(message);
    }

@Override
    public String toString(){
        return this.getClass().getName() + " :" + this.getMessage();
    }
}
