
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author volka_000
 */
public class InputNode {
	
    //ArrayList <Double> inputToHiddenWights3 = new ArrayList<Double>();
    double [] inputToHiddenWightsDelta = new double [Structure.hiddenLayerSize];
    double [] inputToHiddenWights = new double [Structure.hiddenLayerSize];//değişecek
    double value;
    
    public InputNode(double input) 
    {
	this.value=input;
    }
    
    public void setValue(double value)
    {
    	this.value=value;
    }

	public InputNode() {
		// TODO Auto-generated constructor stub
	}
    
}
