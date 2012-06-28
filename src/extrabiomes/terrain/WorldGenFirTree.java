package extrabiomes.terrain;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import extrabiomes.api.TerrainGenManager;

public class WorldGenFirTree extends WorldGenerator {

	final int blockLeaf, metaLeaf, blockWood, metaWood;

	public WorldGenFirTree(boolean par1) {
		super(par1);
		blockLeaf = TerrainGenManager.blockFirLeaves.blockID;
		metaLeaf = TerrainGenManager.metaFirLeaves;
		blockWood = TerrainGenManager.blockFirWood.blockID;
		metaWood = TerrainGenManager.metaFirWood;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		int below = world.getBlockId(x, y - 1, z);
		final int i = rand.nextInt(8) + 24;

		if (!TerrainGenManager.treesCanGrowOnIDs.contains(Integer
				.valueOf(below)) || y >= 256 - i - 1) {
			return false;
		}

		if (y < 1 || y + i + 1 > 256) {
			return false;
		}

		final int j = 1 + rand.nextInt(12);
		final int l = 2 + rand.nextInt(6);

		for (int i1 = y; i1 <= y + 1 + i; i1++) {
			if (i1 < 0 || i1 >= 256)
				return false;

			int k1 = 1;

			if (i1 - y < j) {
				k1 = 0;
			} else {
				k1 = l;
			}

			for (int x1 = x - k1; x1 <= x + k1; x1++) {
				for (int z1 = z - k1; z1 <= z + k1; z1++) {
					final int id = world.getBlockId(x1, i1, z1);

					if (id != 0
							&& !TerrainGenManager.leafBlockIDs.contains(Integer
									.valueOf(id))) {
						return false;
					}
				}
			}
		}

		world.setBlock(x, y - 1, z, Block.dirt.blockID);
		int l1 = rand.nextInt(2);
		int j2 = 1;
		boolean flag1 = false;

		for (int i3 = 0; i3 <= i - j; i3++) {
			int k3 = (y + i) - i3;

			for (int i4 = x - l1; i4 <= x + l1; i4++) {
				int k4 = i4 - x;

				for (int l4 = z - l1; l4 <= z + l1; l4++) {
					int i5 = l4 - z;

					if ((Math.abs(k4) != l1 || Math.abs(i5) != l1 || l1 <= 0)
							&& !Block.opaqueCubeLookup[world.getBlockId(i4, k3,
									l4)]) {
						setBlockAndMetadata(world, i4, k3, l4, blockLeaf,
								metaLeaf);
					}
				}
			}

			if (l1 >= j2) {
				l1 = ((flag1) ? 1 : 0);
				flag1 = true;

				if (++j2 > l) {
					j2 = l;
				}
			} else {
				l1++;
			}
		}

		int j3 = rand.nextInt(3);

		for (int l3 = 0; l3 < i - j3; l3++) {
			int id = world.getBlockId(x, y + l3, z);

			if (id == 0 || TerrainGenManager.leafBlockIDs.contains(id)) {
				setBlockAndMetadata(world, x, y + l3, z, blockWood, metaWood);
			}
		}

		return true;
	}
}