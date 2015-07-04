package inputmodule;

import java.io.IOException;

public interface MyExtractor {
	
	public String nextRecord() throws IOException;
	
}
