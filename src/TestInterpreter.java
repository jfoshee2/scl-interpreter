import scl.interpreter.SCLInterpreter;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by James on 11/26/2017.
 */
public class TestInterpreter {
    public static void main(String[] args) throws FileNotFoundException {
        SCLInterpreter interpreter = new SCLInterpreter(new File("sclex2.scl"));

        interpreter.interpret();
    }
}
