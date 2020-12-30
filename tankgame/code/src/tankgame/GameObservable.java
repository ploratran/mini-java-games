package tankgame;

import java.util.Observable;

public class GameObservable extends Observable {

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }


}