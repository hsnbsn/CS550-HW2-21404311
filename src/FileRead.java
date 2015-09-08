
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;



public class FileRead {
    
   
    public static ArrayList<InputNode> inputLayer;
    public static ArrayList<HiddenNode> hiddenLayer;
    public static ArrayList<OutputNode> outputLayer;
    public static double data[][];
    public static double dataTest[][];
    public static double mean[] ;
    public static double deviation[] ;
    
    public static File log = new File("results_5.txt");
    public static FileWriter fileWriter ;
    public static BufferedWriter bufferedWriter;
   
   

    public static int index;
    public static int Errors;
    public static double testRealValue;
    public static double testComputedValue;
    
    public static void trainRead(String input) throws FileNotFoundException, InterruptedException, UnsupportedEncodingException, IOException {           
        
        index=0;
        Errors=0;
        testRealValue=0.0;
        testComputedValue=0.0;
        hiddenLayer= new ArrayList<HiddenNode>();
        outputLayer= new ArrayList<OutputNode>();
        inputLayer= new ArrayList<InputNode>();
        
      
        
        fileWriter = new FileWriter(log, true);
        bufferedWriter = new BufferedWriter(fileWriter);
            
            createInputlayer();
            createHiddenLayer();
            createOutputLayer();
        
          


            for (int epoch = 0; epoch < Structure.epoch; epoch++) {
                
            System.out.println("Epoch  : " + epoch);
             File f = new File(input);//dosya oku 
            Scanner sc = new Scanner(f);
            
            int instance=0;
             while(sc.hasNextLine())
  
                {
                    clearOutput();
                    clearHidden();
                    clearInput();
                    
                    
                    String line = sc.nextLine();
                    String[] details = line.split(",");
                
                
                  for (int i=0;i< inputLayer.size();i++)
                    
                  {
                inputLayer.get(i).setValue(Double.valueOf(details[i])); //get data from normaized dataset
                   }
                
 
                index = Integer.valueOf(details[3])-1;
                
                outputLayer.get(index).actualOutput=1; // traning datdan gelen 
               

              
                        FeedForward();
                        //System.out.println( "ERROR for "  +Integer.valueOf(details[256])+" "+ outputLayer.get(index).outputError);
                        backpropage();
                        
            
    instance++;
}
    
}
}

    private static void createHiddenLayer() {
		// TODO Auto-generated method stub
        for (int j = 0; j < Structure.hiddenLayerSize; j++) //hiddenLayeroluştur.
           
       {
           hiddenLayer.add(new HiddenNode());
           
           for (int k = 0; k < Structure.outputLayerSize; k++) 
           {  
                hiddenLayer.get(j).hiddenToOutputWeight [k] = (Structure.randomHiddenWeight+(k*0.001));
             
           }
          
       }
} //O.K.

    private static void createOutputLayer() {
		// TODO Auto-generated method stub
	   for (int j = 0; j < Structure.outputLayerSize; j++) //hiddenLayeroluştur.
           
       {
           outputLayer.add(new OutputNode());
           
          
       }
	} //O.K.

    private static void createInputlayer() {
		// TODO Auto-generated method stub
	for (int k=0;   k<Structure.inputLayerSize; k++) //inputLayer olu�tur, verileri ve weightler tan�mla.
        
	{
		inputLayer.add(new InputNode());

			for (int l=0; l<Structure.hiddenLayerSize;l++)
			{

				inputLayer.get(k).inputToHiddenWights[l]=(Structure.randomInputWeight+(l*0.001));
                                

			}

	}
		
	} //O.K.

    public static void FeedForward(){   
    
   
    double expo=0.0;
    
 //------------------------------------------HIDDEN LAYER OUTPUT CALC-------------------------------------------//
                 for (int hidden = 0; hidden < Structure.hiddenLayerSize; hidden++) 
                 {
                     for (int input = 0; input < Structure.inputLayerSize; input++) 
                     {
                         hiddenLayer.get(hidden).value= 
                                 
                                 hiddenLayer.get(hidden).value + 
                                 (inputLayer.get(input).inputToHiddenWights[hidden]*
                                  inputLayer.get(input).value ); //sum of wji * xji
                     }
                    hiddenLayer.get(hidden).value=sigmoid(hiddenLayer.get(hidden).value);// sigmoid (sum(wi*xi))
                 }
//-------------------------------------------------------HIDDEN LAYER OUTPUT CALC-------------------------------// 
                 
//-------------------------------------------------------OUTPUT LAYER OUTPUT CALC-------------------------------//                  
                 
                 for (int i = 0; i < Structure.outputLayerSize; i++) //wkj * xkj
                 
                 {
                     for (int j = 0; j < Structure.hiddenLayerSize; j++) 
                     {
                         outputLayer.get(i).value=
                                 outputLayer.get(i).value+ 
                                 (hiddenLayer.get(j).hiddenToOutputWeight[i] * 
                                  hiddenLayer.get(j).value ) ;
                         
                      }
                  
                     
                 }
//-------------------------------------------------------OUTPUT LAYER OUTPUT CALC-------------------------------//
                 
//-------------------------------------------------------OUTPUT LAYER SOFTMAX CALC-------------------------------//
                 
                for (int i = 0; i < Structure.outputLayerSize; i++) //calcutae all outputs 
                {
                    expo=expo+Math.exp(outputLayer.get(i).value);
                }
                
                for (int i = 0; i < Structure.outputLayerSize; i++) 
                {
                    outputLayer.get(i).computedOutput=(Math.exp(outputLayer.get(i).value)/expo);
                    //System.out.println("Output " + i +"instance  "+ outputLayer.get(i).computedOutput);
                    outputLayer.get(i).outputError=Math.abs(outputLayer.get(i).actualOutput-outputLayer.get(i).computedOutput);
                    
                   
                }
//-------------------------------------------------------OUTPUT LAYER SOFTMAX CALC-------------------------------//                
  
} //O.K. Output
 
    public static double sigmoid(double x) {
    return (1/( 1 + Math.pow(Math.E,(-1*x))));
    
} //O.K.

    public static double calculateError() {
          
          double result=0.0;
          for (int i = 0; i <Structure.inputLayerSize; i++) {
              
              result+=Math.abs(outputLayer.get(i).outputError);
              
          }
    
  return result;  
} 
      
    public static void backpropage() {
		
//DELTA Vjk--------------------------------------------------------------------------------------------    //
    	for (int i=0; i< hiddenLayer.size();i++)
    	{
            for (int j = 0; j < Structure.outputLayerSize; j++) 
                
            {   
                    hiddenLayer.get(i).hiddenToOutputWeightDelta[j]= //delta v = n*(r-z)y'tj
                        
                	Structure.learningRate * //nü
                        (outputLayer.get(j).actualOutput-outputLayer.get(j).computedOutput)* // r -z 
                        hiddenLayer.get(i).value; //y'tj
            }
            
        }
        
//DELTA Vjk--------------------------------------------------------------------------------------------    //
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
//DELTA Wij--------------------------------------------------------------------------------------------    //  
        
        //--//////////////////////sigma (r-z)*Vkj/////////////////////////////
        for (int i = 0; i < Structure.hiddenLayerSize; i++) 
        {
            
            for (int j = 0; j < Structure.outputLayerSize; j++) 
            {
                hiddenLayer.get(i).error=hiddenLayer.get(i).error+
                        ((outputLayer.get(j).actualOutput-outputLayer.get(j).computedOutput)* //sigma (r-z)*Vkj
                          hiddenLayer.get(i).hiddenToOutputWeight[j]);
            }
        }
        //--//////////////////////sigma (r-z)*Vkj///////////////////////////////
        
        
       
        for (int i = 0; i < Structure.inputLayerSize; i++) 
        {
            
            for (int j = 0; j < Structure.hiddenLayerSize; j++) 
            {
                inputLayer.get(i).inputToHiddenWightsDelta[j]= //Delta Wij
                        
                        Structure.learningRate*
                        hiddenLayer.get(j).error*
                        hiddenLayer.get(j).value*
                        (1-hiddenLayer.get(j).value)*
                        inputLayer.get(i).value
                        
                        
                        
                        +(inputLayer.get(i).inputToHiddenWightsDelta[j] * Structure.momentum);// MOMENTUM BURAYA DİKKAT
                        
            }
        
        }
        
                    
                  
    		
    	for (int i = 0; i < Structure.inputLayerSize; i++) {
            
            for (int j = 0; j < Structure.hiddenLayerSize; j++) {
                
                inputLayer.get(i).inputToHiddenWights[j]= 
                        inputLayer.get(i).inputToHiddenWights[j]+ inputLayer.get(i).inputToHiddenWightsDelta[j]; 
        //DeltaWij+ Wij
                
            }
        
        }
        
        for (int i = 0; i < Structure.hiddenLayerSize; i++) {
            
            for (int j = 0; j < Structure.outputLayerSize; j++) {
                
                hiddenLayer.get(i).hiddenToOutputWeight[j]= 
                        hiddenLayer.get(i).hiddenToOutputWeight[j]+ hiddenLayer.get(i).hiddenToOutputWeightDelta[j]; 
                
            }
        
        }
	
                
                
    		

    	}
    	
    public static void testRead (String input) throws FileNotFoundException, IOException {
    
            File f = new File(input);//dosya oku 
            Scanner sc = new Scanner(f);
            int instance=0;
            
            clearInput();
            createOutputLayer();
            
            
                while(sc.hasNextLine())
          
                {  
                    
                String line = sc.nextLine();
                String[] details = line.split(",");
     
                for (int i=0;i< inputLayer.size();i++)
                {
                	inputLayer.get(i).setValue(Double.valueOf(details[i]));
                      
                }
                
               testRealValue=Double.valueOf(details[3]);
               
               TestFeedForward();
               instance++;

                }
                
                double errorRate = 100 - (Errors/400.0) * 100;
                
                
                //output.write("Number of Misclasified Accuracy,Instances,,Number of Hidden Nodes,Number of Epochs,Learning Rate,Momentum,");
                bufferedWriter.write("\n");
                bufferedWriter.write(String.valueOf(errorRate)+","+String.valueOf(Errors)+","+String.valueOf(Structure.hiddenLayerSize)+","+String.valueOf(Structure.epoch)
                        +","+String.valueOf(Structure.learningRate)+
                        ","+ Structure.randomHiddenWeight+
                        ","+ Structure.randomInputWeight+
                        ","+Structure.testDataset+
                        ","+String.valueOf(Structure.momentum));
                

                //System.out.println("Test Details : -------------------------------------------------- " );
                //System.out.println("Number of Misclasified Instances : " + Errors);
                //System.out.println("Accuracy                         : %" + errorRate );
                //System.out.println("Number of Hidden Nodes           : " + Structure.hiddenLayerSize );
                //System.out.println("Number of Epochs                 : " + Structure.epoch );
                //System.out.println("Learning Rate                    : " + Structure.learningRate);
                //System.out.println("Momentum                         : " + Structure.momentum);
                //System.out.println("Error Threshold                  : " + Structure.errorThreshold);
                
                bufferedWriter.close();
                
                

}

    public static void TestFeedForward(){   
    clearOutput();
    clearHidden();
    clearDelta();
    clearValues();
   double expo=0.0;
    
                 for (int hidden = 0; hidden < Structure.hiddenLayerSize; hidden++) 
                 {
                     for (int input = 0; input < Structure.inputLayerSize; input++) 
                     {
                         hiddenLayer.get(hidden).value= 
                                 
                                 hiddenLayer.get(hidden).value + 
                                 (inputLayer.get(input).inputToHiddenWights[hidden]*inputLayer.get(input).value ); //sum of wji * xji
                         
                         
                     }
                     
                     hiddenLayer.get(hidden).value=sigmoid(hiddenLayer.get(hidden).value);// sigmoid (sum(wi*xi))
                    // System.out.println(hiddenLayer.get(hidden).value+ " "+ hidden + " Hidden Values--> ");
                 }
                 
                 
                 
                 for (int i = 0; i < Structure.outputLayerSize; i++) //wkj * xkj
                 
                 {
                     for (int j = 0; j < Structure.hiddenLayerSize; j++) 
                     {
                         outputLayer.get(i).value=
                                 outputLayer.get(i).value+ 
                                 hiddenLayer.get(j).hiddenToOutputWeight[i] * hiddenLayer.get(j).value;
                      }
                     
                 }
                 
                 
                for (int i = 0; i < Structure.outputLayerSize; i++) //calcutae all outputs 
                {
                    expo=expo+Math.exp(outputLayer.get(i).value);
                }
                
                for (int i = 0; i < Structure.outputLayerSize; i++) 
                {
                    outputLayer.get(i).computedOutput=(Math.exp(outputLayer.get(i).value)/expo);
                    
                    //System.out.println("CIKIS DEGER " + i +"instance  "+ outputLayer.get(i).computedOutput);
                    
                  
                }
                
                double max=0.0;
                int maxindex=0;
                
                 for (int i = 0; i < Structure.outputLayerSize; i++) 
                {
                    if (outputLayer.get(i).computedOutput> max){
                    max = outputLayer.get(i).computedOutput;
                    maxindex=i;
                    }
      
                }
                 
                testComputedValue=Double.valueOf(maxindex+1); 
                //System.out.println("Hesaplanan Değer "+ (maxindex+1) );
                
                if (testComputedValue!=testRealValue)
                {
                    System.out.println("Error ------");
                    System.out.println("Real Value --> "+ testRealValue + " Computed Value --> " +  testComputedValue  );
                    Errors++;
                }


}

    private static void clearOutput() {
        
        for (int i = 0; i < Structure.outputLayerSize; i++) {
            
            outputLayer.get(i).computedOutput=0.0;
            outputLayer.get(i).actualOutput=0.0;
            outputLayer.get(i).outputError=0.0;
            outputLayer.get(i).value=0.0;
            
            
                 
            
        }

}
        
    private static void clearInput() {
        
        for (int i = 0; i < Structure.inputLayerSize; i++) {
            
            
            inputLayer.get(i).value=0.0;
            
            for (int j = 0; j < Structure.hiddenLayerSize; j++) {
                inputLayer.get(i).inputToHiddenWightsDelta[j]=0.0;
            }
        }   
       
    }

    private static void clearHidden() {
        
        for (int i = 0; i < Structure.hiddenLayerSize; i++) {
            
            
            hiddenLayer.get(i).value=0.0;
            hiddenLayer.get(i).error=0.0;
            
            for (int j = 0; j < Structure.outputLayerSize; j++) {
                
            
            hiddenLayer.get(i).hiddenToOutputWeightDelta[j]=0.0;
                    }
            
                 
            
        }   
       
    }

    private static void clearDelta() {
        
        for (int i = 0; i < Structure.inputLayerSize; i++) 
        {
            for (int j = 0; j < Structure.hiddenLayerSize; j++) 
            {
                inputLayer.get(i).inputToHiddenWightsDelta[j]=0.0;
                
            }
        
        }
        
        for (int i = 0; i < Structure.hiddenLayerSize; i++) 
        {
            for (int j = 0; j < Structure.outputLayerSize; j++) 
            {
                hiddenLayer.get(i).hiddenToOutputWeightDelta[j]=0.0;
                
            }
        
        }
       
    }

    private static void clearValues() {
        
       
            for (int j = 0; j < Structure.hiddenLayerSize; j++) 
            {
                hiddenLayer.get(j).value=0.0;
                hiddenLayer.get(j).error=0.0;
                
            }
        
  
            for (int j = 0; j < Structure.outputLayerSize; j++) 
            {
                outputLayer.get(j).value=0.0;
                
            }
        
       
    }
   
    public static void mean(double[][] doubles) {

        double sum = 0;
        
        

        for (int i = 0; i < 256; i++) 
        
        {
            for (int j = 0; j < 200; j++) 
            {
                mean[i]+=doubles[j][i];
            }
             mean[i]= mean[i]/200;
       
        }
        
    }
    
    public static void deviation (double [][] doubles){
        double sum=0.0;
        
        
          for (int i = 0; i < 256; i++) 
        
        {
            sum=0.0;
            
            for (int j = 0; j < 200; j++) 
            {
           
                sum = sum + (data[j][i]-mean[i])*(data[j][i]-mean[i]);
               
            }
 
            deviation[i]=Math.sqrt(sum/199);
         
           
             
        }
          
          
          
          
          
    }
    
    public static void normalize (String input) throws FileNotFoundException{
          data=new double[200][256];  
       
        
        mean= new double [256];
        deviation = new double [256];
        
            File f = new File(input);//dosya oku 
            Scanner sc = new Scanner(f);
            int sayac=0; 
            
         
             while(sc.hasNextLine())
                 
             {
                                     
                    String line = sc.nextLine();
                    String[] details = line.split(",");
                
                for (int i=0;i< 256;i++)
                    
                    {
                	data[sayac][i]=Double.valueOf(details[i]);
                   
                    }

                sayac++;
             }
             
             mean(data);
             deviation(data);
             
             
       for (int i = 0; i < 256; i++) 
        
        {
            for (int j = 0; j < 200; j++) 
            {
              data[j][i]=(data[j][i]-mean[i])/deviation[i];

            }
           
             
        }
       
       

             
 
}

    public static void normalizeTest (String input) throws FileNotFoundException{
         dataTest=new double[200][256]; 
            File f = new File(input);//dosya oku 
            Scanner sc = new Scanner(f);
            int sayac=0; 
            
         
             while(sc.hasNextLine())
                 
             {
                                     
                    String line = sc.nextLine();
                    String[] details = line.split(",");
                
                for (int i=0;i< 256;i++)
                    
                    {
                	dataTest[sayac][i]=Double.valueOf(details[i]);
                   
                    }

                sayac++;
             }
            
             
                 mean(dataTest);
             deviation(dataTest);
             
       for (int i = 0; i < 256; i++) 
        
        {
            for (int j = 0; j < 200; j++) 
            {
              dataTest[j][i]=(dataTest[j][i]-mean[i])/deviation[i];

            }
           
             
        }
             
 
}
    
    public static void trainReadOneZero(String input) throws FileNotFoundException, InterruptedException {           
           
            createInputlayer();
            createHiddenLayer();
            createOutputLayer();
          

            for (int epoch = 0; epoch < Structure.epoch; epoch++) {
                System.out.println("Epoch  : " + epoch);
        
    
            File f = new File(input);//dosya oku 
            Scanner sc = new Scanner(f);
             
            int instance=0;
             while(sc.hasNextLine())
  
                {
                    clearOutput();
                    clearHidden();
                    clearInput();
                    
                    
                    String line = sc.nextLine();
                    String[] details = line.split(",");
                
                for (int i=0;i< inputLayer.size();i++)
                    
                    {
                	inputLayer.get(i).setValue(Double.valueOf(details[i]));
                    }
                
                  //for (int i=0;i< inputLayer.size();i++)
                    
                  //{
                //inputLayer.get(i).setValue(data[instance][i]); //get data from normaized dataset
                  //  }
                
 
                index = Integer.valueOf(details[256])-1;
                
                outputLayer.get(index).actualOutput=1; // traning datdan gelen 
               

                while (outputLayer.get(index).actualOutput-outputLayer.get(index).computedOutput > Structure.errorThreshold)
                
             
                    {
                      
                        FeedForward(); 
                        backpropage();
                        clearDelta();
                        clearValues();
                        

                    }

    clearInput();
    instance++;
}
    
}
}
    public static void testReadOneZero (String input) throws FileNotFoundException {
    
            File f = new File(input);//dosya oku 
            Scanner sc = new Scanner(f);
            int instance=0;
            
            clearInput();
            createOutputLayer();
            
            
                while(sc.hasNextLine())
          
                {  
                    
                String line = sc.nextLine();
                String[] details = line.split(",");
                
                for (int i=0;i< inputLayer.size();i++)
               {
                	inputLayer.get(i).setValue(Double.valueOf(details[i]));
                       
                }
                
                //for (int i=0;i< inputLayer.size();i++)
                //{
                //	inputLayer.get(i).setValue(dataTest[instance][i]);
                      
               //}
                
               testRealValue=Double.valueOf(details[256]);
               
               TestFeedForward();
               instance++;

                }
                
                double errorRate = 100 - (Errors/200.0) * 100;
                
                System.out.println("Test Details : -------------------------------------------------- " );
                System.out.println("Number of Misclasified Instances : " + Errors);
                System.out.println("Accuracy                         : %" + errorRate );
                System.out.println("Number of Hidden Nodes           : " + Structure.hiddenLayerSize );
                System.out.println("Number of Epochs                 : " + Structure.epoch );
                System.out.println("Learning Rate                    : " + Structure.learningRate);
                System.out.println("Momentum                         : " + Structure.momentum);
                System.out.println("Error Threshold                  : " + Structure.errorThreshold);
               
                

}
}



   