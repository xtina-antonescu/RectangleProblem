//Rectangle problem
//Given some points in cartesian coordinate, (X, Y) find the number of rectangles that can be created by those points.
//Take into consideration only the rectangles that are parallel with the X, Y axes.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RectangleProblem {

    //create a new class for the point entity, which has as attributes the
    //cartesian coordinates x and y
    public static class Point{

        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    public static ArrayList<Point> readPointsFromFile(String filePath) {
        ArrayList<Point> pointsArray = new ArrayList<>();

        try {
            //read input from file, each point being on one single line
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                line = line.replaceAll("[^\\d,]", ""); // remove non-digit characters
                String[] values = line.split(",");
                int x = Integer.parseInt(values[0]);
                int y = Integer.parseInt(values[1]);
                Point p = new Point(x, y);
                pointsArray.add(p);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return pointsArray;
    }

    public static boolean isInArray(ArrayList<Point> pointArray, Point p){

        //method which verifies if a point is in the array
        for(int i = 0; i < pointArray.size(); i++) {
            if (pointArray.get(i).getX() == p.getX() && pointArray.get(i).getY() == p.getY()) {
                return true;
            }
        }
        return false;
    }

    public static int numberOfFormedRectangles(ArrayList<Point> pointArray){

        int rectangles = 0;

        //IDEA: traverse the list using 2 for loops and verify if the 2 points have different x and y coordinates
        //points that are on the same axis will not be considered for this verification
        //because, since I need to find the rectangles that have the edges parallel to the Ox and Oy axis
        //it is easier to consider 2 points which have different coordinates and "build" the triangle with these
        //different coordinates

        for(int i = 0; i < pointArray.size(); i++){
            for(int j = i+1; j < pointArray.size(); j++){
                if( (pointArray.get(i).getX() != pointArray.get(j).getX()) && (pointArray.get(i).getY() != pointArray.get(j).getY())){
                    //build the new 2 points with coordinates (xi, yj) and (xj, yi)
                    Point p1 = new Point(pointArray.get(i).getX(), pointArray.get(j).getY());
                    Point p2 = new Point(pointArray.get(j).getX(), pointArray.get(i).getY());

                    if(isInArray(pointArray, p1) && isInArray(pointArray, p2)){
                        rectangles++;
                    }
                }
            }
        }
        //divide by 2 because this method will compute the double number of rectangles
        return rectangles / 2;
    }

    public static void main(String[] args){
        ArrayList<Point> points = readPointsFromFile("input.txt");
        System.out.println("Number of rectangles: " + numberOfFormedRectangles(points));
    }
}
