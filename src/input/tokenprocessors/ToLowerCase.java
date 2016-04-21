package input.tokenprocessors;

import java.util.LinkedList;
import java.util.List;

public class ToLowerCase implements ITokenProcessor {

	public String toString() {
		return this.getClass().getName();
	}
	
	@Override
	public List<String> process(List<String> tokens) {
		List<String> retval = new LinkedList<String>();
		
		for(String str : tokens) {
			retval.add(str.toLowerCase());
		}
		
		return retval;
	}

}
