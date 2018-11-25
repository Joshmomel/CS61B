import java.lang.Math.*;

public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;    
    }

    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;  
    }

    public double calcDistance(Planet p2){
        double delX = this.xxPos - p2.xxPos;
        double delY = this.yyPos - p2.yyPos;
        double rSq = delX * delX + delY * delY;
        
        return java.lang.Math.sqrt(rSq);
    }

    public double calcForceExertedBy (Planet p){
        double upV = G * this.mass * p.mass;
        double downV = this.calcDistance(p) * this.calcDistance(p);

        return upV / downV;
    }

    public double calcForceExertedByX (Planet p){
        double force = this.calcForceExertedBy(p);
        double upV = force * (p.xxPos - this.xxPos);
        double downV = this.calcDistance(p);

        return upV / downV;
    }

    public double calcForceExertedByY (Planet p){
        double force = this.calcForceExertedBy(p);
        double upV = force * (p.yyPos - this.yyPos);
        double downV = this.calcDistance(p);

        return upV / downV;
    }
    
    public double calcNetForceExertedByX(Planet[] allPlanets){
        double sum = 0;
        for(Planet p : allPlanets){
            if(!p.equals(this)){
                sum += this.calcForceExertedByX(p);
            }
        }
        return sum;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets){
        double sum = 0;
        for(Planet p : allPlanets){
            if(!p.equals(this)){
                sum += this.calcForceExertedByY(p);
            }
        }
        return sum;
    }

    public void update(double dt, double fX, double fY){
        // a_netX
        double aNetX = fX / this.mass;
        double aNetY = fY / this.mass;
        //vel update
        this.xxVel = this.xxVel + dt * aNetX;
        this.yyVel = this.yyVel + dt * aNetY;
        //update position
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void draw(){
        StdDraw.setScale(- 3e+11, 3e+11);
        StdDraw.picture(this.xxPos, this.yyPos, this.imgFileName);
    }

}