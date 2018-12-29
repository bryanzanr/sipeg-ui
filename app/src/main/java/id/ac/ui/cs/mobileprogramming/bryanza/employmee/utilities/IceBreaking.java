package id.ac.ui.cs.mobileprogramming.bryanza.employmee.utilities;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class IceBreaking {
    public static class RendererWrapper implements Renderer {
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // No-op
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClear(gl.GL_COLOR_BUFFER_BIT);
        }
    }
    public static class JNIlib {
        static
        {
            try
            {
                // Load necessary libraries.
//                System.loadLibrary("opencv_java");
//                System.loadLibrary("nonfree");
//                System.loadLibrary("mytest_lib");
                System.loadLibrary("native-lib");
            }
            catch( UnsatisfiedLinkError e )
            {
                System.err.println("Native code library error.\n");
            }
        }
//        public static native String stringFromJNI();
    }
}
