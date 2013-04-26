package cminuscompiler;

import java.io.IOException;

/**
 * 
 * 
 * @author Paul Marshall
 * @version 1.0
 * File: Scanner.java
 * Created: Feb 3, 2012
 * Summery of Modifications:
 *      - None
 *
 * Description:
 *  
 */
public interface Scanner {
    public Token getToken() throws IOException;
    public Token viewToken ();
}
