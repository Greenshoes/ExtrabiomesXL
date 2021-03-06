/**
 * This work is licensed under the Creative Commons
 * Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.handlers;

import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import extrabiomes.blocks.BlockNewSapling;

public class SaplingBonemealNewEventHandler
{
    
    private final BlockNewSapling sapling;
    
    public SaplingBonemealNewEventHandler(BlockNewSapling sapling)
    {
        this.sapling = sapling;
    }
    
    @ForgeSubscribe
    public void onBonemealEvent(BonemealEvent e)
    {
        if (e.getResult() == Result.DEFAULT && e.ID == sapling.blockID)
        {
            if (!e.world.isRemote)
            {
                if (e.world.rand.nextFloat() < 0.45D)
                {
                    sapling.markOrGrowMarked(e.world, e.X, e.Y, e.Z, e.world.rand);
                }
                
                e.setResult(Result.ALLOW);
            }
        }
    }
    
}
