package publishing_script;

 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFileChooser;
 
/**
	Defines a input directory containing the twb file and a csv file containing additional details
	Uses the information in the csv file to publish the twb file to the mentioned tableau server.
 */
public class Server_Publish {
 
	//Stores the credential information read from the csv file
	String [] cred;
	//List the files in the input directory and pass the paths to the next function
    public void listFolders(String directoryName){
 
        File directory = new File(directoryName);
 
        //get all the files from a directory
        File[] fList = directory.listFiles();
        //for each file pass the absolute path to the subdirectories function
        for (File file : fList){
            if (file.isDirectory()){
                System.out.println(file.getName());
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }
 
    /**
     Determines the file extension and following steps based on the extension
     */
    public void listFilesAndFilesSubDirectories(String directoryName){
 
        File directory = new File(directoryName);
 
        //get all the files from a directory
        File[] fList = directory.listFiles();
 
        for (File file : fList){
            if (file.isFile()){
                System.out.println(file.getAbsolutePath());
                String extension = "";
                //Get file extension
                int i = file.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = file.getName().substring(i+1);
                }
                System.out.println("FileType:"+extension);
                //if extension is csv then get twb file name and username/pwd for the file
                if(extension.equals("csv")){
                	String[]b=csv(file.getAbsolutePath());
                	System.out.println(b[0]+b[1]+b[2]);
                	cred=b;
                	
                }
                /*
                 * if file extension is twb then build the publishing command and pass it to publish
                 *  function
                */
                else if(extension.equals("twb")){
                	File parent=file.getParentFile();
                	System.out.println(parent.getName());
                	/*
                	 * build the command used to publish the workbook with the name and username/pwd
                	 * obtained from the csv file and stored in cred array variable
                	 */
                	
                	String cmnd="tabcmd publish \""+ file.getAbsolutePath() + "\" -r \"" + parent.getName() +
                	"\" -o --db-username \"" + cred[1]+ "\" --db-password \"" + cred[2] + "\" --save-db-password --tabbed";
                	System.out.println(cmnd);
                	publish(cmnd);
                	
                }
            } 
            //if subdirectories exist reiterate to the subdirectory.
            else if (file.isDirectory()){
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }
    
    //reads the csv file and gets the file name and usename/pwd for the file
    String[] csv(String csvfile){
    	BufferedReader br = null;
    	String[] b = null;
    	 try {
    	  String splitBy = ",";
          br = new BufferedReader(new FileReader(csvfile));
          String line ;
         
			while((line=br.readLine()) !=null){
			       b = line.split(splitBy);
			       
			  }
			
			br.close();
			return b;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return b;
          
    }
    
   //final function which runs the built publish command in runtime cmd for java.
    void publish(String cmnd){
    	try 
        { 
    		//server login string concatenated with publish command(cmnd)
    		String pub="cmd /c cd C:\\Program Files\\Tableau\\Tableau Server\\8.2\\extras\\Command Line Utility && tabcmd login -s PLACE_SERVER_LINK_HERE -u PLACE_SERVER_LOGIN_NAME_HERE -p PLACE_SERVER_PWD_HERE && "+cmnd;
            System.out.println(pub);
    		Process p=Runtime.getRuntime().exec(pub); 
           //wait for successful publish to server
            p.waitFor(); 
            BufferedReader reader=new BufferedReader(
                new InputStreamReader(p.getInputStream())
            ); 
            String line; 
            while((line = reader.readLine()) != null) 
            { 
                System.out.println(line);
            } 
            

        }
        catch(IOException e1) {} 
        catch(InterruptedException e2) {} 

        System.out.println("Done"); 
    } 
    
    //Sets the output file and defines the input directory.
    public static void main (String[] args){
    	/*
    	Set Output to Text File to verify Script Result and Status
    	*/
    	PrintStream out;
		try {
			//path variable for output file
			out = new PrintStream(new FileOutputStream("C:\\Users\\xbblssg\\Desktop\\output.txt"));
			System.setOut(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		/*
		 JFileChooser is used here to enable a popup to choose the input directory for this script
		 The input directory contains the .twb file and a .csv file which has details regarding 
		 the .twb file.
		 */
        Server_Publish listFilesUtil = new Server_Publish();
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        f.showOpenDialog(null);

        System.out.println(f.getCurrentDirectory());
        System.out.println(f.getSelectedFile());
        
        //Calls the list folders function to get directories and start the publishing process.
        listFilesUtil.listFolders(f.getSelectedFile().toString());
       
    }
}