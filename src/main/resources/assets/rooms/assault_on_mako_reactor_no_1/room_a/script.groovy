import de.hauschild.ff7rl.context.Context
import de.hauschild.ff7rl.context.ContextConstants

class Room {

    int getTop() {
        return 10;
    }

    int getLeft() {
        return 10;
    }

    void enter(final Context context) {
        context.set(ContextConstants.General.REGION, "Mako Reactor");
    }

}
