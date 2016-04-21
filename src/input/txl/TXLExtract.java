package input.txl;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TXLExtract implements ITXLCommand {

	@Override
	public String toString() {
		return script;
	}
	
	private String script;
	private String executable;
	
	private boolean existsExec;
	private boolean existsScript;
	
	public TXLExtract(String language, String block_granularity) {
		
		script = language + "-extract-" + block_granularity + "s.txl";
		executable = language + "-extract-" + block_granularity + "s.x";
		
		existsExec = Files.exists(Paths.get(TXLUtil.getTXLRoot() + "/" + executable));
		
		existsScript = Files.exists(Paths.get(TXLUtil.getTXLRoot() + "/" + script));
		
	}
	
	public boolean existsExec() {
		return this.existsExec;
	}
	
	public boolean existsScript() {
		return this.existsScript;
	}
	
	@Override
	public String getCommandExec() {
		return TXLUtil.getTXLRoot() + "/" + executable + " stdin";
	}

	@Override
	public String getCommandScript() {
		return "txl stdin " + TXLUtil.getTXLRoot() + "/" + script;
	}
	
}
