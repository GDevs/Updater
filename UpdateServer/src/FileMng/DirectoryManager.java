package FileMng;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

/*
 * To improve: 
 * 
 * Dialog whether rebase should be done
 *
 */
public class DirectoryManager  {
	public static String FILE_HAS_TYP_PATTERN = "([^\\.]{0,}\\.{1,}[^\\.]{0,}){1,}"; //pattern that checks for any '.' in words
	private String rootPath = ""; 
	private File dir;
	private File root[];
	private boolean isBroken = false; // Represents the state of the directories integrity

	
	
	/*
	 * Checks the integrity of the directory and, if broken or incomplete 
	 * automatically creates all folders etc.
	 */
	public DirectoryManager() {
		System.out.println("waiting for user input...");
		
		dir = askForDirectory();         // User chosen directory
		if(dir != null)
		{
			this.rootPath = dir.getPath();

			System.out.println("Checking directory-Root-Path");
			File check = new File(this.rootPath);
		
			/*
			 *  If the chosen folder is the destination for the project instead of an instance  
			 *  create root;
			 */ 
			if(!check.getName().equals(Paths.ROOT_NAME) && !check.isDirectory())
			{
				File temp = new File(check.getPath() + Paths.sep + Paths.ROOT_NAME);
				temp.mkdirs();
				check = temp;
			}
		
			// If the home directory exists proceed to check the child files
			if (check.exists() && check.isDirectory()) 
			{
				root = check.listFiles();
				isRootComplete();    
			
				if(!this.isBroken) System.out.println("First Level complete");

				/*
				 * If the directory isn't complete, create it  
				 */
				if (this.isBroken) {
					rebase();
				}
			}
		}
		System.out.println("done");
	}
	
	/*
	 * Verifies whether root is complete and stores it in this.isBroken 
	 * 
	 * known Bug, if a file has the same name as a directory in the same path, the file won't be created. 
	 */
	private void isRootComplete()
	{
		System.out.println("Root exists \nChecking integrity of First-Level-Structures");
		if (root.length == 0) 
		{
			this.isBroken = true;
		}
		else for (int i = 0; i < Paths.FIRST_LEVEL_CHILDS.length; i++) 
		{
				System.out.println("Looking for File :" + Paths.FIRST_LEVEL_CHILDS[i]);
				
				File temp = new File(this.rootPath + Paths.FIRST_LEVEL_CHILDS[i]);
				if (	temp.exists() 
					&&  Pattern.matches(DirectoryManager.FILE_HAS_TYP_PATTERN ,Paths.FIRST_LEVEL_CHILDS[i]) 
					&& !temp.isDirectory()) 
				{
					System.out.println("Found!");
				}
				else if(! Pattern.matches(DirectoryManager.FILE_HAS_TYP_PATTERN ,Paths.FIRST_LEVEL_CHILDS[i]) && temp.isDirectory())
				{
					System.out.println("Found!");
				}
				else
				{
					System.out.println("Missmatch: File not found: " + temp.getPath());
					this.isBroken = true;
				}
		}
	}

	
	/*
	 *  Opens a dialog for the user to choose a directory
	 */
	public static File askForDirectory() 
	{
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setAcceptAllFileFilterUsed(false);
		fc.showOpenDialog(null);
		return fc.getSelectedFile();
	}

	/*
	 * Creates all missing files
	 */
	private void rebase() {
		System.out.println("Directory: " + this.rootPath + "  is broken \nRebasing");
		//Look for missing Files
		for(int i = 0; i < Paths.FIRST_LEVEL_CHILDS.length;i++)
		{
			File temp = new File(rootPath + Paths.FIRST_LEVEL_CHILDS[i]);
			if((temp.exists() && !temp.isDirectory() && Pattern.matches(DirectoryManager.FILE_HAS_TYP_PATTERN, Paths.FIRST_LEVEL_CHILDS[i])
			 ||(temp.exists() && temp.isDirectory() && !Pattern.matches(DirectoryManager.FILE_HAS_TYP_PATTERN, Paths.FIRST_LEVEL_CHILDS[i] ))))
			{
				//Do Nothing ??
			}
			else
			{
				//Create only the missing ones
				File newHome = temp;
				try {
					if(Pattern.matches(DirectoryManager.FILE_HAS_TYP_PATTERN, Paths.FIRST_LEVEL_CHILDS[i]))
					{
						System.out.println(("Creating File :") + Paths.FIRST_LEVEL_CHILDS[i]);
						newHome.createNewFile();
					} 
					else
					{
						System.out.println(("Creating Directory :") + Paths.FIRST_LEVEL_CHILDS[i]);
						newHome.mkdirs();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean isBroken()
	{
		return this.isBroken;
	}
	
	private boolean createFile(File f)
	{
		if(f.exists())
		{
			if(f.isDirectory())
			{
				return f.mkdirs();
			}
			else
			{
				try {
					return f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}
