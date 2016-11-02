package sticklings.scene.sticklings;

import java.util.function.Supplier;

/**
 * Represents each type of stickling and provides a simple way to
 * build sticklings from the type
 */
public enum SticklingType {
	Basic(BasicStickling::new),
	Miner(MinerStickling::new),
	Exploder(ExploderStickling::new),
	Floater(FloaterStickling::new),
	Swimmer(SwimmerStickling::new),
	Blocker(BlockerStickling::new);

	private Supplier<Stickling> creator;

	private SticklingType(Supplier<Stickling> creator) {
		this.creator = creator;
	}

	/**
	 * Creates a new instance of this stickling
	 * @return The instance
	 */
	public Stickling create() {
		return creator.get();
	}
}
