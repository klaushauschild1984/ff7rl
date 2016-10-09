import de.hauschild.ff7rl.Actor
import de.hauschild.ff7rl.context.Context
import de.hauschild.ff7rl.context.KernelContext
import de.hauschild.ff7rl.context.RoomContext

class RoomScript {

    Context context
    int top = 12;

    void initialize(final Context context) {
        this.context = context;
    }

    int getTop() {
        return 10;
    }

    int getLeft() {
        return 10;
    }

    void enter() {
        KernelContext.setRegion(context, "Mako Reactor");
        RoomContext.placeActor(context, Actor.CLOUD, top, 12);
        RoomContext.activeActor(context, Actor.CLOUD);
    }

    int update() {
        top++;
        RoomContext.placeActor(context, Actor.CLOUD, top, 12);
        return 500;
    }

}
