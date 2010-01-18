package uk.co.darrenhurley.processing;

import processing.core.*;
import com.processinghacks.arcball.*;
import java.util.*;

/**
 * @author darrenh
 *
 */
public class Brownian3d extends PApplet
{
	
	ArrayList<Integer[]> points = new ArrayList<Integer[]>();
	
	int trailLength = 300;
	int trailDisplacement = 5;
	int floorSize = 110;
	int depth = 40;
	float alphaIncrement;
	
	public void setup()
	{
		size(1000, 500, P3D);
		smooth();
		frameRate(30);
		
		// create first point at (0,0,0)
		Integer[] startingPoint = {0, 0, 0};
		points.add(startingPoint);
		
		alphaIncrement = (float) 255 / trailLength;
		
		background(50);
		fill(60);
		
		mouseX = 1;
		mouseY = height/2;
		
		ArcBall arcball = new ArcBall(width/2, height/2, 0, min(width/2,height/2), this);
		
	}

	public void draw()
	{
		
		background(50);
		
		translate(width/2, height/2, 0);
		
		// calculate the new points
		int newX = points.get(0)[0] + (int) random(-trailDisplacement, trailDisplacement);
		int newY = points.get(0)[1] + (int) random(-trailDisplacement, trailDisplacement);
		int newZ = points.get(0)[2] + (int) random(-trailDisplacement, trailDisplacement);
		
		// constrain the new points
		newX = constrain(newX, -floorSize, floorSize);
		newY = constrain(newY, -depth, depth);
		newZ = constrain(newZ, -floorSize, floorSize);
		
		// calculate the new location
		Integer[] newPoint = {newX, newY, newZ};
		
		// add a point light to the start of the trail
		pointLight(255, 255, 255, newPoint[0], newPoint[1], newPoint[2]);
		
		// add this to the beginning of our points array
		points.add(0, newPoint);
		
		// draw the trail
		for(int i = 1; i < points.size(); i++){
			
			// prune the points array if greater than set trail length
			if(i == trailLength){
				
				points.subList(i, points.size());
				break;
				
			}
			
			// calculate the stroke opacity (older points are have more transparency)
			float strokeAlpha = 255 - (alphaIncrement * i);
			
			Integer[] firstPoint = points.get(i);
			Integer[] secondPoint = points.get(i - 1);
			
			stroke(255, strokeAlpha);
			strokeCap(ROUND);
			strokeWeight(5);
			
			line(firstPoint[0], firstPoint[1], firstPoint[2], secondPoint[0], secondPoint[1], secondPoint[2]);
			
		}
		
		
		stroke(255);
		strokeWeight(1);
		noFill();
		box(floorSize * 2, depth * 2, floorSize * 2);
		
	}

}