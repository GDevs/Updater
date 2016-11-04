package FileMng;

import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/*
 * Noch zu verbessern: 
 * 
 * Dialog bei falschem directory nach erlaubniss neues anzulegen
 *
 */
public class DirectoryManager implements FilenameFilter {
	private String homePath = ""; 
	private File dir;
	private boolean isBroken = false; // Represents the state of the directory

	/*
	 * Checks the integrity of the directory and, if broken or incomplete 
	 * automatically creates all folders etc.
	 */
	public DirectoryManager() {
		System.out.println("waiting for user input...");
		
		dir = askForDirectory();         // User chosen directory
		this.homePath = dir.getPath();

		System.out.println("Checking directory-Root-Path");
		File check = new File(this.homePath);
		File home[];
		if (check.exists() && check.isDirectory()) // If the home directory
													// exists proceed to check
													// the child files
		{
			System.out.println("Root exists \nChecking integrity of First-Level-Structures");
			home = check.listFiles();
			System.out.println(home.length);
			if (home.length == 0) 
			{
				this.isBroken = true;
			}
			else for (int i = 0; i < Paths.FIRST_LEVEL_CHILDS.length; i++) 
			{
					int j = i ;

					System.out.println("Looking for File :" + Paths.FIRST_LEVEL_CHILDS[j]);

					File temp = new File(this.homePath + Paths.FIRST_LEVEL_CHILDS[j]);
					
					
					
					
					if (	temp.exists() 
						&&  Pattern.matches("(\\w{0,}\\.{1,}\\w{0,}){1,}",Paths.FIRST_LEVEL_CHILDS[i])) 
					{
						
						System.out.println("Found!");
					}
					else if(temp.isDirectory())
					{
						System.out.println("Found!");
					}
					else
					{
						System.out.println("Missmatch: File not found: " + temp.getPath());
						this.isBroken = true;
					}
			}
			
			if(!this.isBroken) System.out.println("First Level complete");

		/*
		 * Wenn der Pfad nicht vollständig ist wird er angelegt
		 */
		 if (this.isBroken) {
				rebase();
			}
		}
		else
		{
			System.out.println("exiting");
		}
	}
	
	

	
	/*
	 *  Obens a Dialog for the user to choose a directory
	 */
	public static File askForDirectory() {
		JFrame a = new JFrame();
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setAcceptAllFileFilterUsed(false);
		fc.showOpenDialog(null);
		return fc.getSelectedFile();
	}

	/*
	 * 
	 */
	private void rebase() {
		System.out.println("Directory: " + this.homePath + "  is broken \nRebasing");
		
		for(int i = 0; i < Paths.FIRST_LEVEL_CHILDS.length;i++)
		{
			if(new File(homePath + Paths.FIRST_LEVEL_CHILDS[i]).exists())
			{
				//Do Nothing ??
			}
			else
			{
				System.out.println("creating: " + Paths.FIRST_LEVEL_CHILDS[i]);
				File newHome = new File(this.homePath + Paths.FIRST_LEVEL_CHILDS[i]);
				try {
					if(Pattern.matches("(\\w{0,}\\.{1,}\\w{0,}){1,}",Paths.FIRST_LEVEL_CHILDS[i]))
					{
						newHome.createNewFile();
					} 
					else
					{
						newHome.mkdirs();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean accept(File dir, String name) {
		if (dir.isDirectory())
			return true;
		return false;
	}
}
