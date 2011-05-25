package antonj.cubic.input;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;
import antonj.cubic.components.Component;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * User: Anton
 * Date: 28.02.2010
 * Time: 15:56:27
 */
public class InputProcessor {
    public static final int INPUT_QUEUE_SIZE = 20;

    private final Object inputMutex = new Object();

    private final ArrayBlockingQueue<InputObject> inputQueue =
            new ArrayBlockingQueue<InputObject>(INPUT_QUEUE_SIZE);

    public final ArrayBlockingQueue<InputObject> pool =
            new ArrayBlockingQueue<InputObject>(INPUT_QUEUE_SIZE);

    private Component[] components;

    public InputProcessor() {
        synchronized (inputMutex) {
            for (int i = 0; i < INPUT_QUEUE_SIZE; i++) {
                pool.add(new InputObject(pool));
            }
        }
    }

    public void setComponents(Component[] components) {
        this.components = components;
    }

    public void feed(int sourceType, MotionEvent event) {
        InputObject input = inputObject(sourceType, event);

        synchronized (inputMutex) {
            inputQueue.add(input);
        }
    }


    public void process(MainLoop loop) {
        synchronized (inputMutex) {
            try {
                while (!inputQueue.isEmpty()) {
                    InputObject input = inputQueue.take();

                    switch (input.sourceType) {
                        case InputObject.TOUCH_SCREEN:
                            for (Component component : components) {
                                component.handleTouchEvent(input.event, loop);
                            }

                            break;
                        case InputObject.TRACK_BALL:
                            for (Component component : components) {
                                component.handleTrackballEvent(input.event, loop);
                            }
                            break;
                    }

                    input.recycle();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private InputObject inputObject(int type, MotionEvent event) {
        try {
            InputObject inputObject = pool.take();
            inputObject.sourceType = type;
            inputObject.event = MotionEvent.obtain(event);
            return inputObject;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
