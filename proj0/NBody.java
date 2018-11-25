import java.util.ArrayList;

public class NBody{

    public static String imageToDraw = "images/starfield.jpg";

    public static double readRadius(String path){
        In in = new In(path);
        in.readInt();
        double radius = in.readDouble();
        
        return radius;
    }

    public static Planet[] readPlanets(String path){
        In in = new In(path);
        in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[5];
        for (int i = 0; i < planets.length; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String location = in.readString();
            planets[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass,location);
        }
        
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.valueOf(args[0]);
        double dt = Double.valueOf(args[1]);
        String filename = args[2];
        
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        StdDraw.enableDoubleBuffering();

        double timeVariable = 0.0;

        while (timeVariable < T) {
            double[] xForce = new double[5];
            double[] yForce = new double[5];


            /* Draw background */
            StdDraw.setScale(-100, 100);
            StdDraw.clear();
            StdDraw.picture(0,0,imageToDraw);

            /* Draw planets */
             for (Planet p : planets) {
                 if (!p.imgFileName.contains("images")){
                    p.imgFileName = "images/" + p.imgFileName;
                 }
                 p.update(dt, p.calcNetForceExertedByX(planets), p.calcNetForceExertedByY(planets));
                 System.out.println("the path is : " + p.imgFileName);
                 System.out.println("the location is : " + p.xxPos + "," + p.yyPos);
                 p.draw();
             }

            StdDraw.show();
            StdDraw.pause(10);

            /* increase time */
            timeVariable = timeVariable + dt;
            // System.out.print(timeVariable);
        }

        /* printing the Universe */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                        planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }

    }   


}