package sticklings.scene.sticklings;

import java.util.function.Supplier;

public enum SticklingType {
	Basic(BasicStickling::new),
	Miner(MinerStickling::new),
	Exploder(null),
	Swimmer(null),
	Floater(null),
	Blocker(BlockerStickling::new);
	
	private Supplier<Stickling> creator;
	
	private SticklingType(Supplier<Stickling> creator) {
		this.creator = creator;
	}
	
	public Stickling create() {
		return creator.get();
	}
}
