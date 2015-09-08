/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author volka_000
 */
public class HiddenNode {
    
    public double[] hiddenToOutputWeight= new double[Structure.outputLayerSize];
     public double error;
    public double[] hiddenToOutputWeightDelta= new double[Structure.outputLayerSize];
    public double value;
    
    public HiddenNode(double value)
    
    {
        this.value=value;
        
    }
    
    public HiddenNode()
    
    {
     //System.out.println("Hidden Node");
    }
    
}
