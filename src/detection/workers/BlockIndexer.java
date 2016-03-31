package detection.workers;

import java.util.Comparator;
import java.util.Objects;

import detection.block.Block;
import detection.index.IIndex;
import detection.prefixer.Prefixer;
import detection.requirements.Requirements;
import input.block.InputBlock;
import input.block.TermFrequency;
import util.blockingqueue.IReceiver;

public class BlockIndexer implements Runnable {

	private IReceiver<InputBlock> input;
	private IIndex index;
	private Prefixer prefixer;
	private Comparator<TermFrequency> sorter;
	private Requirements requirements;
	
	public BlockIndexer(IReceiver<InputBlock> input, IIndex index, Comparator<TermFrequency> sorter, Prefixer prefixer, Requirements requirements) {
		Objects.requireNonNull(input);
		Objects.requireNonNull(index);
		Objects.requireNonNull(prefixer);
		Objects.requireNonNull(requirements);
		
		this.input = input;
		this.index = index;
		this.sorter = sorter;
		this.prefixer = prefixer;
		this.requirements = requirements;
	}
	
	@Override
	public void run() {
		while(true) {
			
			// Get an Input Block
			InputBlock iblock = take();
			
			// Check Poison -- If poison, quit.
			if(input.isPoisoned())
				break;
			
			// Check Requirements  -- If fail, skip to the next block.
			if(!requirements.approve(iblock))
				continue;
			
			// Sort
			if(sorter != null)
				iblock.sort(sorter);
			
			// Convert to Detection Block
			Block dblock = prefixer.prefix(iblock);
			
			// Index
			index.put(dblock);
		}
	}
	
	private InputBlock take() {
		while(true) {
			try {
				return input.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
