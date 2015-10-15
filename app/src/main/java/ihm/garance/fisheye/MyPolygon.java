package ihm.garance.fisheye;

import android.graphics.Color;
import android.graphics.Path;

import java.util.ArrayList;


public class MyPolygon {
	
	// ArrayList<Float> points  = new ArrayList<Float>() ;
	public int color;
	float[] res ;
    int length = 0;
	float[] resOrigin;

	public void addPoint(float dx, float dy) {
		// TODO Auto-generated method stub
		// points.add(dx);
		// points.add(dy);

        length += 1;

        // float [] newres = new float[points.size()];
        float [] newres = new float[length*2];
		if (res != null)
			{
			System.arraycopy(res, 0, newres, 0, res.length);
			newres[res.length] = dx;
			newres[res.length+1] = dy;
			}
		else 
			{
			newres[0] = dx;
			newres[1] = dy;
			}
		res = newres;
	}

	public int getNbPoints() {
		// TODO Auto-generated method stub
        // return points.size()/2;
        return length;
	}


	public float xPoint(int index)
	{
        // return points.get(index*2);
        return res[index*2];
	}
	
	public float yPoint(int index)
	{
		// return points.get(index*2+1);
        return res[index*2+1];
	}

	public float[] getPoints() {
		return res;
	}

	public void setyPoint(int index, float value) {
		res[index*2+1] = value;
	}

	public void setxPoint(int index, float value) {
		res[index*2] = value;
	}

    public Path getPath(int marges) {
        Path result = null ;
        if (res.length > 2) {
            result = new Path();
            int beforelast = res.length-1;
            result.moveTo(res[0]+marges, res[1]+marges);
            for(int i = 2; i < beforelast; i=i+2) {
                result.lineTo(res[i]+marges, res[i+1]+marges);
            }
            result.close();
        }
        return result;
    }

	static protected ArrayList<MyPolygon> generatePolygons(float[] originalSize, int marges, int nb)
	{
		ArrayList<MyPolygon> elements = new ArrayList<MyPolygon>();
// dimension dans laquelle s'inscrit un polygone
		float w = (originalSize[0] - marges*2) / (nb*2);
		float h = (originalSize[1] - marges*2) / (nb*2);
// pour faire quelques carres différents
		int tiers = nb /3 ;
		int sixieme = nb /6 ;
		int deuxtiers = 2*nb /3 ;
		int troisquarts = 3*nb /4 ;
		float pasW = w/4;
		float pasH = h/4;
// création de tous les polygones
		for(int i = 0; i < nb; i++)
		{
			for(int j = 0; j < nb; j++)
			{
				MyPolygon p = new MyPolygon();
				float dx = w*2*i+marges;
				float dy = h*2*j+marges;
// ajout des points constituants les polygones
				if ((i == tiers) && (j==sixieme)) {
					p.addPoint(dx, dy+pasH);
					p.addPoint(dx+pasW, dy);
				}
				else {
					p.addPoint(dx, dy);
				}
				p.addPoint(dx+w/2, dy);
				p.addPoint(dx+w, dy);
				p.addPoint(dx+w
						, dy+h/2);
				if ((i == troisquarts) && (j==deuxtiers)) {
					p.addPoint(dx+w, dy
							-
							pasH+h);
					p.addPoint(dx
							-
							pasW+w, dy+h);
				}
				else {
					p.addPoint(dx + w, dy + h);
				}
				p.addPoint(dx+w/2, dy+h
				);
				p.addPoint(dx, dy+h);
				p.addPoint(dx
						, dy+h/2);
				p.color = Color.BLACK;
				elements.add(p);
// ou une autre couleur qui dépend de i et de j elements.add(p);
			}
		}
		return elements;
	}


}
