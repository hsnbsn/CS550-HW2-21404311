
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author volka_000
 */
public class main {
    public static void main (String args[]) throws FileNotFoundException, InterruptedException, IOException{
        
        
        
    long startTime = System.currentTimeMillis();
    
    
    
      //  for (int hiddenNode = 4; hiddenNode < 33; hiddenNode=hiddenNode*2) { //Node Count
      //      Structure.hiddenLayerSize=hiddenNode;
      //      for (int epoch = 1000; epoch < 3001; epoch=epoch+1000) { // EpochCount
       //         Structure.epoch=epoch;
       //         for (double randomInputWeight = -0.5; randomInputWeight <= 0.50; randomInputWeight=randomInputWeight+0.1) {
       //             Structure.randomInputWeight=randomInputWeight;
        //            for (double randomHiddenWeight = -0.5; randomHiddenWeight <= 0.50; randomHiddenWeight+=0.1) {
       //                 Structure.randomHiddenWeight=randomHiddenWeight;
        //                 for (double learningRate = 0.1; learningRate <=1.0 ; learningRate+=0.1) {
       //                      Structure.learningRate=learningRate;
                             //for (double momentum = 0; momentum < 1.1; momentum+=0.1) {
                                 //Structure.momentum=momentum;
                            
                                //FileRead.normalize("ORL_tr.txt");
                                FileRead.trainRead("f1train.txt");
    
                                //FileRead.normalizeTest(Structure.testDataset);
                                FileRead.testRead(Structure.testDataset);  
                            // }
         //               }
                        
        //            }
        //        }
  
       //     }
            
      //  }
    
    //System.out.println(hiddenNode + " " + epoch);
    
   
   // FileRead.normalize("ORL_tr.txt");
   // FileRead.trainRead("ORL_tr.txt");
    
   // FileRead.normalizeTest(Structure.testDataset);
   // FileRead.testRead(Structure.testDataset);
    //FileRead.trainReadOneZero("ZeroOneNormTrain.txt");
    //FileRead.testReadOneZero("ZeroOneNormTest.txt");
      
    long endTime   = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    
    NumberFormat formatter = new DecimalFormat("#0.0000");
    System.out.println("Execution Time                   : " + formatter.format((totalTime) / 1000d) + " seconds");
    
    
    
    
    }
    
}
