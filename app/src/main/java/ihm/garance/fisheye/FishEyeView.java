package ihm.garance.fisheye;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * TODO: document your custom view class.
 */
public class FishEyeView extends View {
    public ArrayList<MyPolygon> elts;
    public ArrayList<MyPolygon> eltsOrigin;

    private float[] origSize;
    private float iCentre;
    private float jCentre;

    public FishEyeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        origSize = new float[2];
        origSize[0] = 600;
        origSize[1] = 600;
        //(int[] originalSize, int marges, int nb)
        //width - height, marges, nb de polys
        elts = MyPolygon.generatePolygons(origSize, 2, 30);
        eltsOrigin = MyPolygon.generatePolygons(origSize, 2, 30);
        System.out.println("**********************************");
      /*  for(MyPolygon p : elts) {
            // liste des points sous la forme d'un tableau (x1, y1, x2, y2, ...)
            ptsOrigin = p.getPointsOrigin(); //On conserve la liste des points d'origine, pas déformés
            ptsDeform = ptsOrigin;
        }*/

    }

    @Override
    public void onMeasure(int w, int h) {
        setMeasuredDimension(w, h);
       // origSize[0] = getMeasuredWidth();
        //origSize[1] = getMeasuredHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        iCentre = (canvas.getWidth())/2;
        jCentre = (canvas.getHeight())/2;
        Paint paint = new Paint();
        for(MyPolygon p : elts) {
            paint.setColor(p.color);
            float[] pts = p.getPoints();
            for(int i = 0; i<pts.length-3; i=i+2) {
                canvas.drawLine(pts[i], pts[i + 1], pts[i + 2], pts[i + 3], paint);
            }
// ligne entre le dernier sommet et le premier sommet
            canvas.drawLine(pts[pts.length - 2], pts[pts.length - 1], pts[0], pts[1], paint);
        }
    }

    public void deformPoints(double o, double r, double z) {
        elts.clear();
        double scale = 1;
        for(MyPolygon p : eltsOrigin) {

            MyPolygon newPoly = new MyPolygon();
            for (int k = 0; k < p.getNbPoints(); k++) {
                double dist = dist(p.xPoint(k), p.yPoint(k));
                if (dist < 90) {
                    //si fdeform < à solution calculée avant, je fais, sinon on annule tooout !
                    scale = fDeform(dist, o, r, z) / dist;
                    newPoly.addPoint((float) (iCentre + (p.xPoint(k) - iCentre) * scale), (float) (jCentre + (p.yPoint(k) - jCentre) * scale));
                    newPoly.color = Color.BLACK;
                }
                else {
                    newPoly.addPoint(p.xPoint(k), p.yPoint(k));
                    newPoly.color = Color.BLACK;
                }
            }
            elts.add(newPoly);
        }
        invalidate();
    }

    public void resetPoints() {
        elts.clear();
        for (MyPolygon p : eltsOrigin) {
            elts.add(p);
        }
        invalidate();
    }

    private double fDeform(double dist, double o, double r, double z) {
        //z, o, r are params we can use to change the fisheye size
        double intermediate = sqrt((pow(dist, 2) + pow(z, 2))*(pow(r, 2) - pow((z-o), 2)) + pow(z, 2)*pow((z-o), 2));
        double numerateur = intermediate + z*(z-o);
        double denominateur = pow(dist, 2)+pow(z, 2);
        return dist * numerateur/denominateur;
    }

    private double dist(float i, float j) {
        return sqrt(pow(iCentre - i, 2) + pow(jCentre - j, 2));
    }

    public void deformPoints(double o, double r, double z, float xCenter, float yCenter) {
        //ici, besoin de faire en + le choix du centre
        iCentre = xCenter;
        jCentre = yCenter;
        deformPoints(o, r, z);
    }
}
