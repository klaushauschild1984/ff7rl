import de.hauschild.ff7rl.assets.rooms.Room
import de.hauschild.ff7rl.context.Context

class RoomScript {

    int getTop() {
        return 10;
    }

    int getLeft() {
        return 10;
    }

    void enter(final Context context) {
        Room.setRegion(context, "Mako Reactor");
    }

}
