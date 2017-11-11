import interpreter.SCLScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by James on 9/3/2017.
 */
public class SCLParser extends SCLScanner {

    private Scanner input;      // Separate Scanner used to build parse tree from scl program

    /**
     * Creates instance of interpreter.SCLScanner
     *
     * @param sclProgram - the SCL file to be read.
     * @throws FileNotFoundException if the SCL file does not exist
     */
    public SCLParser(File sclProgram) throws FileNotFoundException {
        super(sclProgram);
        input = new Scanner(sclProgram);
    }
}
