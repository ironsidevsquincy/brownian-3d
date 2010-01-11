package uk.co.darrenhurley.processing;

import processing.core.*;
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
	int floorSize = 30;
	int depth = 10;
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
		
	}

	public void draw()
	{
		
		background(50);
		
		camera(
			mouseX, mouseY - height, 100, // eyeX, eyeY, eyeZ
			0, 0, 0, // centerX, centerY, centerZ
			0, 1.0f, 0 // upX, upY, upZ
		);
		
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
			float alpha = 255 - (alphaIncrement * (i - 1));
			
			Integer[] firstPoint = points.get(i);
			Integer[] secondPoint = points.get(i - 1);
			
			stroke(255, alpha);
			
			line(firstPoint[0], firstPoint[1], firstPoint[2], secondPoint[0], secondPoint[1], secondPoint[2]);
			
		}
		
		pushMatrix();
		translate(0, depth + 1, 0);
		rotateX(PI/2);
		stroke(50);
		box(floorSize * 2, floorSize * 2, 1);
		popMatrix();
		
	}

}