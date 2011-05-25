package antonj.cubic.components;

import antonj.cubic.MainLoop;
import antonj.cubic.model.Cube;
import antonj.utils.Math2;

/**
 * User: Anton
 * Date: 28.02.2010
 * Time: 20:57:13
 */
public class RandomRotation extends Component {
    @Override
    public void update(long frameDelta, MainLoop loop) {
        Cube cubic = loop.cube;
        
        if (!cubic.isLocked()) {
            int direction = 1 - 2 * Math2.RNG.nextInt(2);
            int axis = Math2.RNG.nextInt(cubic.slices.length);
            int slice = Math2.RNG.nextInt(cubic.config.size);
            cubic.rotate(axis, slice, direction);
        }
    }
}
