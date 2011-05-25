package antonj.opengl;

import static antonj.utils.Arrays2.*;

/**
 * User: Anton
 * Date: 02.12.2009
 * Time: 21:17:48
 */
public class Collisions {
    private static final float EPSILON = 0.0001f;

    private static final float[] edge1 = new float[3];
    private static final float[] edge2 = new float[3];
    private static final float[] tvec = new float[3];
    private static final float[] pvec = new float[3];
    private static final float[] qvec = new float[3];

    public static float u;
    public static float v;
    public static float t;

    public static boolean intersectTriangle(float[] origin,
            float[] direction,
            float[] vertex0,
            float[] vertex1,
            float[] vertex2) {
        float det;
        float inv_det;

        /* find vectors for two edges sharing vert0 */
        sub3(edge1, vertex1, vertex0);
        sub3(edge2, vertex2, vertex0);

        /* begin calculating determinant - also used to calculate U parameter */
        cross3(pvec, direction, edge2);

        /* if determinant is near zero, ray lies in plane of triangle */
        det = dot3(edge1, pvec);

        if (det < EPSILON)
            return false;

        /* calculate distance from vert0 to ray origin */
        sub3(tvec, origin, vertex0);

        /* calculate U parameter and test bounds */
        u = dot3(tvec, pvec);
        if (u < 0 || u > det) {
            return false;
        }

        /* prepare to test V parameter */
        cross3(qvec, tvec, edge1);
        v = dot3(direction, qvec);

        /* calculate V parameter and test bounds */
        if (v < 0 || u + v > det) {
            return false;
        }

        /* calculate t, scale parameters, ray intersects triangle */
        t = dot3(edge2, qvec);
        inv_det = 1.0f / det;
        t *= inv_det;
        u *= inv_det;
        v *= inv_det;

        return true;
    }

    public static boolean intersectTriangle(float[] origin,
            float[] direction,
            float[] vertices,
            int offset) {
        float det;
        float inv_det;

        /* find vectors for two edges sharing vert0 */
        sub3(edge1, vertices, offset, 1, 0);
        sub3(edge2, vertices, offset, 2, 0);

        /* begin calculating determinant - also used to calculate U parameter */
        cross3(pvec, direction, edge2);

        /* if determinant is near zero, ray lies in plane of triangle */
        det = dot3(edge1, pvec);

        if (det < EPSILON)
            return false;

        /* calculate distance from vert0 to ray origin */
        sub3(tvec, origin, 0, vertices, offset);

        /* calculate U parameter and test bounds */
        u = dot3(tvec, pvec);
        if (u < 0 || u > det) {
            return false;
        }

        /* prepare to test V parameter */
        cross3(qvec, tvec, edge1);
        v = dot3(direction, qvec);

        /* calculate V parameter and test bounds */
        if (v < 0 || u + v > det) {
            return false;
        }

        /* calculate t, scale parameters, ray intersects triangle */
        t = dot3(edge2, qvec);
        inv_det = 1.0f / det;
        t *= inv_det;
        u *= inv_det;
        v *= inv_det;

        return true;
    }

    //TODO: check if there is no intersection (the vector is parallel to the plane)

    public static void findIntersectionCoord(float[] rayStart,
            float[] rayEnd,
            float[] planeNormal,
            float distance, float[] coords) {
        float Vd = dot3(planeNormal, rayEnd);
        float V0 = -(dot3(planeNormal, rayStart) + distance);
        float t = V0 / Vd;

        coords[0] = rayStart[0] + rayEnd[0] * t;
        coords[1] = rayStart[1] + rayEnd[1] * t;
        coords[2] = rayStart[2] + rayEnd[2] * t;
    }
}
