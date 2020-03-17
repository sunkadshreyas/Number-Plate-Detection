package application;
	

import java.io.File;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


public class Main extends Application {
	private ImageView myImageView;
	static File file;
	static String output_file;
	
	// function for reading the text
	public static void ocr_run()
	{
		// location of input image file which will be selected by user
		String input_file =  file.toString();
		//output text file to which text is written
		 output_file="C:\\Users\\sunka\\out";
		 // contains file path for Tesseract executable
		 String tesseract_install_path="C:\\Program Files (x86)\\Tesseract-OCR\\tesseract";
		 String[] command =
		    {
		        "cmd",
		    };
		    Process p;
		 try {
		 p = Runtime.getRuntime().exec(command);
		        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
		        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
		        PrintWriter stdin = new PrintWriter(p.getOutputStream());
		        stdin.println("\""+tesseract_install_path+"\" \""+input_file+"\" \""+output_file+"\" -l eng");
		        stdin.close();
		        p.waitFor();
		        System.out.println(Read_File.read_a_file(output_file+".txt"));
		    } catch (Exception e) {
		 e.printStackTrace();
		    }
		  }
	
	
	public void start(Stage primaryStage) throws Exception
	{
        primaryStage.setTitle("Number Plate Detection");
 
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane);
        
        Text text = new Text();
        text.setX(500);
        text.setY(500);
        
        // button1 to open the image 
        Button btn1 = new Button("Open Image");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent arg0) {
        		FileChooser fileChooser = new FileChooser();
        		file = fileChooser.showOpenDialog(null);
        		if (file!=null)
        		{
        			Image image = new Image(file.toURI().toString());
        			myImageView.setImage(image);
        		}
        	}
        });
        
        // button2 is used to call the tesseract to process the image
        Button btn2 = new Button("Read the Number Plate");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent arg0)
        	{
        		System.out.println("Started reading the number plate");
        		ocr_run();
        		System.out.println("Finished reading the Number Plate");
        		
        	}
        });
        
        // display the text from file into the Textarea
        Button btn3 = new Button("Display the text");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent arg0)
        	{
        		System.out.println("Rading from file");
        		text.setFont(Font.font(40));
        		text.setText("\t"+Read_File.read_a_file(output_file+".txt"));
        		System.out.println("Finished reading from file");
        	}
        });
        
        
        myImageView = new ImageView();
        
        HBox rootBox = new HBox();
        rootBox.getChildren().addAll(btn1,myImageView,btn2,btn3,text);
        pane.setCenter(rootBox);
        
        HBox toolBarArea = new HBox(10);
        toolBarArea.setPadding(new Insets(10));
        
        primaryStage.setScene(scene);
        primaryStage.setWidth(700);
        primaryStage.setHeight(700);
        primaryStage.show();
        
        toolBarArea.getChildren().addAll(btn1,btn2,btn3);
        pane.setTop(toolBarArea);
	}
	
	public static void main(String args[])
	{
		launch(args);
	}
	
}
