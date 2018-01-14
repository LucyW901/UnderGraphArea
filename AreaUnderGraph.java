//Lucy Wu
//May 2016
// The "AreaUnderGraph" class.
import java.io.*;
import java.awt.*;
import hsa.Console;

public class AreaUnderGraph
{
    static Console c;           // The output console

    public static void main (String[] args) throws IOException
    {
    	
    		c = new Console ();

		//declare variables
		int type;
		int n;
		double[] y = new double [5];
		int x1, x2;
		double del_x;
		double yv, yv2;
		double area;
		double sum = 0;
	
		//step1: ask the user for a math function
		c.println ("Welcome to the Area Under the Curve Program!");
		c.println ("Choose an equation type:");
		c.println ("1 Linear");
		c.println ("2 Quadratic");
		c.println ("3 Trigonometric");
		type = c.readInt ();
		y = equation (type);
	
		//step2: find the boundaries and delta x
		c.println ("Enter the left and right boundaries to find area under curve.");
		c.print ("Left boundary: ");
		x1 = c.readInt ();
		c.print ("Right boundary: ");
		x2 = c.readInt ();
		c.println ("Enter the number of rectangles.");
		n = c.readInt ();
		del_x = delta (x1, x2, n);
	
		//step3: draw the graph
		c.clear ();
		c.println ("Here is the graph.");
		c.drawLine (50, 330, 400, 330); //x-axis
		c.drawLine (70, 20, 70, 500); //y-axis
		for (int i = 60 ; i < 400 ; i += 10)
		{
		    c.drawLine (i, 330, i, 325);
		} //units on x-axis
		for (int i = 30 ; i < 500 ; i += 10)
		{
		    c.drawLine (70, i, 75, i);
		} //units on y-axis
		for (double i = 0 ; i <= 30 ; i += 0.01) //integer part
		{
		    yv = yValue (y, i);
		    draw (i, yv);
		} //plot the points to draw the graph
	
		//step4: draw the rectangle
		for (double i = x1 ; i < x2 ; i += del_x)
		{
		    yv = yValue (y, i);
		    yv2 = yValue (y, i + del_x);
		    rect (i, yv, i + del_x, yv2, del_x);
		} //for loop for each rectangle
	
		//step5: calculate, tracking and display the total area of the rectangles
		double[] X = new double [n]; // an array to store x values
		double[] f = new double [n];  // an array to store corresponding y values
		double[] xf = new double [n];  // an array to store area of the corresponding rectangles
		int index = 0;
		for (double i = x1 ; i < x2 ; i += del_x)
		{
		    X [index] = i;
		    yv = Math.abs (yValue (y, i));
		    f [index] = yv;
		    area = yv * del_x;
		    xf [index] = area;
		    sum = sum + area;
		    index++;
		} //for loop to calculate the area of each rectangle
		c.setCursor (24, 40);
		c.println ("The area is " + sum + " units^2");
	
		//step6: output the tracking data into a file
		out (X, f, xf, n);
	
		//extra step: label the graph with teh equation
		print (y);
	} // main method


    public static double[] equation (int a)   //a method to determnine the type of the inputing function and the equation for STEP1
    {
	double m, b;
	double f, d, e;
	String trig;
	double y = 0;
	double[] equ = new double [5];

	if (a == 1) //Linear Type
	{
	    c.println ("You chose Linear.");
	    c.println ("Please enter the slope and the intercept b for the linear equation.");
	    c.print ("Slope = ");
	    m = c.readDouble ();
	    c.print ("Intercept b = ");
	    b = c.readDouble ();
	    equ [0] = 1;
	    equ [1] = m;
	    equ [2] = b;
	}
	else if (a == 2) //Quadratic Type
	{
	    c.println ("You chose Quadratic.");
	    c.println ("Please enter a, b, c coefficients.");
	    c.print ("a = ");
	    f = c.readDouble ();
	    c.print ("b = ");
	    d = c.readDouble ();
	    c.print ("c = ");
	    e = c.readDouble ();
	    equ [0] = 2;
	    equ [1] = f;
	    equ [2] = d;
	    equ [3] = e;
	}
	else if (a == 3) //Trigonometric Type
	{
	    c.println ("You chose Trigonometric.");
	    c.println ("Please enter the type of trigonometric function you choose: sin or cos or tan?");
	    trig = c.readLine ();
	    trig = trig.toLowerCase ();
	    equ [0] = 3;
	    if (trig.equals ("sin"))
	    {
		equ [1] = 1;
	    } //sin graph
	    else if (trig.equals ("cos"))
	    {
		equ [1] = 2;
	    } //cos graph
	    else
	    {
		equ [1] = 3;
	    } //tan graph
	    c.println ("y = a * sin ( b * x ) + c");
	    c.print ("Please enter coefficient a: ");
	    equ [2] = c.readDouble ();
	    c.print ("Please enter coefficient b: ");
	    equ [3] = c.readDouble ();
	    c.print ("Please enter coefficient c: ");
	    equ [4] = c.readDouble ();
	}
	return equ;
    } //end of equation method structure


    public static double yValue (double[] e, double x)  //a method to find y value with the given x value
    {
	double y;
	if (e [0] == 1)
	{
	    y = e [1] * x + e [2];
	} //linear equation
	else if (e [0] == 2)
	{
	    y = e [1] * x * x + e [2] * x + e [3];
	} //quadratic equation
	else
	{
	    if (e [1] == 1)
	    {
		y = e [2] * Math.sin (e [3] * x) + e [4];
	    } //sin
	    else if (e [1] == 2)
	    {
		y = e [2] * Math.cos (e [3] * x) + e [4];
	    } //cos
	    else
	    {
		y = e [2] * Math.tan (e [3] * x) + e [4];
	    } //tan
	} //trignometric equation
	return y;
    } //end of yValue method


    public static double delta (int p, int q, int o)  //a method to return delta x for STEP 2
    {
	double differ = (double) Math.abs (q - p);
	double r = differ / o;
	return r;
    } //end of delta method structure


    public static void draw (double x, double y)  // a method to draw plot the point with given x and y coordinate for STEP 3
    {
	int a = (int) Math.round (10 * x + 70);
	int b = (int) Math.round (330 - 10 * y);
	c.fillOval (a, b, 1, 1);
    } //end of draw method structure


    public static void rect (double m, double n, double o, double p, double q)  // a method to draw the rectangles for STEP 4
    {
	int q1 = (int) Math.round (10 * q); //width
	int y1 = (int) Math.round (330 - 10 * n); //y of top left point
	int y2 = (int) Math.round (330 - 10 * p);
	int y3 = (int) Math.round (10 * n); //length
	int x1 = (int) Math.round (10 * m + 70); //x of top left point
	int x2 = (int) Math.round (10 * o + 70);
	c.setColor (Color.red);
	c.fillRect (x1, y1, q1, y3); //rectangle
	c.setColor (Color.black);
	c.drawLine (x2, 330, x2, y2); //right boundary
	c.drawLine (x1, 330, x1, y1); //left boundary
	c.drawLine (x1, y1, x2, y1); //top line of the rectangle
    } //end of rect method structure


    public static void out (double[] first, double[] second, double[] third, int n) throws IOException // a method to output the data to a file for STEP 6
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("area.txt"));
	output.println ("X" + "\t\t\t" + "f(x)" + "\t\t\t" + "Xf(x)");
	for (int i = 0 ; i < n ; i++)
	{
	    output.println (first [i] + "\t\t\t" + second [i] + "\t\t\t" + third [i]);
	} //for loop to print arrays to the out data file
	output.close (); //to close the file after the program writes to it
    } //end of out method structure


    public static void print (double[] e)  // a method to print out the equation added by me for extra step
    {
	String equat;
	if (e [0] == 1)
	{
	    equat = "y = " + e [1] + "x + " + e [2];
	} //linear equation
	else if (e [0] == 2)
	{
	    equat = "y = " + e [1] + "x^2 + " + e [2] + "x + " + e [3];
	} //quadratic equation
	else
	{
	    if (e [1] == 1)
	    {
		equat = "y = " + e [2] + "sin ( " + e [3] + "x ) + " + e [4];
	    } //sin equation
	    else if (e [1] == 2)
	    {
		equat = "y = " + e [2] + "cos ( " + e [3] + "x ) + " + e [4];
	    } //cos equation
	    else
	    {
		equat = "y = " + e [2] + "tan ( " + e [3] + "x ) + " + e [4];
	    } //tan equation
	} //trignometric equation
	c.setCursor (5, 40);
	c.println (equat);
    } //end of print method structure
} // AreaUnderGraph class


