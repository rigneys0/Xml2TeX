package fileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
public class TexWriter {
	private PrintWriter teXPrinter = null;
	private ArrayList<String> teXQueue;
	public TexWriter(){
		teXQueue = new ArrayList<String>();
	}
	public void open(String uri){
		try{
			teXPrinter = new PrintWriter(new FileOutputStream(uri));
		}
		catch(IOException eIO){
			System.err.println(eIO);
		}
	}//open
	public void addTeXItem(String item){
		teXQueue.add(item);
	}
	public void clearBatch(){
		teXQueue.clear();
	}
	public void writeBatch(){
		for(String line : teXQueue){
			teXPrinter.print(line);
		}
	}//writeBatch
	public void write(String data){
		teXPrinter.print(data);
	}//write;
	public void writeln(String data){
		teXPrinter.println(data);
	}//writeln
	public void close(){
		clearBatch();
		if(teXPrinter!=null){
			teXPrinter.close();
			if(teXPrinter.checkError()){
				System.err.println("Error while closing file writer");
			}
		}
	}//close
}
