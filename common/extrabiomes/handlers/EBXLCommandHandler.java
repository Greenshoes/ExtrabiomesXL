package extrabiomes.handlers;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import extrabiomes.blocks.BlockCustomSapling;
import extrabiomes.blocks.BlockNewSapling;
import extrabiomes.lib.Element;
import extrabiomes.lib.Vector3;
import extrabiomes.module.summa.worldgen.WorldGenAcacia;
import extrabiomes.module.summa.worldgen.WorldGenAutumnTree;
import extrabiomes.module.summa.worldgen.WorldGenAutumnTree.AutumnTreeType;
import extrabiomes.module.summa.worldgen.WorldGenBaldCypressTree;
import extrabiomes.module.summa.worldgen.WorldGenBigAutumnTree;
import extrabiomes.module.summa.worldgen.WorldGenCypressTree;
import extrabiomes.module.summa.worldgen.WorldGenFirTree;
import extrabiomes.module.summa.worldgen.WorldGenFirTreeHuge;
import extrabiomes.module.summa.worldgen.WorldGenJapaneseMapleShrub;
import extrabiomes.module.summa.worldgen.WorldGenJapaneseMapleTree;
import extrabiomes.module.summa.worldgen.WorldGenLegendOak;
import extrabiomes.module.summa.worldgen.WorldGenRainbowEucalyptusTree;
import extrabiomes.module.summa.worldgen.WorldGenRedwood;
import extrabiomes.module.summa.worldgen.WorldGenSakuraBlossomTree;

public class EBXLCommandHandler extends CommandBase
{
    
    @Override
    public String getCommandName()
    {
        return "ebxl";
    }
    
    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/ebxl help";
    }
    
    @Override
    public void processCommand(ICommandSender icommandsender, String[] cmds)
    {
        if (icommandsender instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) icommandsender;
            
            if (cmds.length == 0)
            {
                player.addChatMessage("use \"/ebxl help\" for the list of valid commands.");
            }
            else
            {
                if (cmds[0].equals("help"))
                {
                    if (cmds.length == 1)
                    {
                        helpList(player);
                    }
                    else
                    {
                        // Give instructions about a command
                        if (cmds[1].equals("help"))
                        {
                            player.addChatMessage("\u00A72-ExtrabiomesXl help Command-\u00A7r");
                            player.addChatMessage("\u00A7o/ebxl help [command]\u00A7r");
                            player.addChatMessage("If [command] is blank or an invalid command then the list of");
                            player.addChatMessage("valid commands will be displayed. If [command] is a valid");
                            player.addChatMessage("command then details about that command will be dispalyed.");
                        }
                        else if (cmds[1].equals("spawntree"))
                        {
                            player.addChatMessage("\u00A72-ExtrabiomesXl spawntree Command-\u00A7r");
                            player.addChatMessage("\u00A7o/ebxl spawntree <treetype> <x> <y> <z> [seed]\u00A7r");
                            player.addChatMessage("Forces a tree of the specified <type> to spawn at");
                            player.addChatMessage("<x>,<y>,<z> in the world. [command] is optional and if a");
                            player.addChatMessage("number is provided will force the tree to use the same random");
                            player.addChatMessage("numbers for tree generation for any giver seed. For example");
                            player.addChatMessage("\"/ebxl spawntree fur 0 100 0 100\" will cause the exact same");
                            player.addChatMessage("fur tree to spawn at 0,100,0 no matter how many times you run");
                            player.addChatMessage("the command.");
                        }
                        else if (cmds[1].equals("version"))
                        {
                            player.addChatMessage("\u00A72-ExtrabiomesXl version Command-\u00A7r");
                            player.addChatMessage("\u00A7o/ebxl version\u00A7r");
                            player.addChatMessage("Displays the change log for the current");
                            player.addChatMessage("version of ExtrabiomesXL.");
                        }
                        else if (cmds[1].equals("lastseed"))
                        {
                            player.addChatMessage("\u00A72-ExtrabiomesXl lastseed Command-\u00A7r");
                            player.addChatMessage("\u00A7o/ebxl lastseed <treetype>\u00A7r");
                            player.addChatMessage("Displays the last random number that was used to generate");
                            player.addChatMessage("the specified tree type for use with the spawntree command.");
                        }
                        else if (cmds[1].equals("saplingdespawntime"))
                        {
                            player.addChatMessage("\u00A72-ExtrabiomesXl saplingdespawntime Command-\u00A7r");
                            player.addChatMessage("\u00A7o/ebxl saplingdespawntime [ticks]\u00A7r");
                            player.addChatMessage("Display/set the number of ticks that a sapling will exist");
                            player.addChatMessage("on the ground before it despawns.");
                        }
                        else if (cmds[1].equals("killtree"))
                        {
                            player.addChatMessage("\u00A72-ExtrabiomesXl killtree Command-\u00A7r");
                            player.addChatMessage("\u00A7o/ebxl killtree <x> <y> <z>\u00A7r");
                            player.addChatMessage("Kills the tree at the specified coords.");
                        }
                        else
                        {
                            helpList(player);
                        }
                    }
                }
                else if (cmds[0].equals("kill"))
                {
                    killTree(player, 0, 4, 0);
                }
                else if (cmds[0].equals("spawn"))
                {
                    (new WorldGenSakuraBlossomTree(true)).generate(player.worldObj, player.worldObj.rand, 0, 4, 0);
                }
                else if (cmds[0].equals("killtree"))
                {
                    if (cmds.length == 4)
                    {
                        try
                        {
                            int x = Integer.parseInt(cmds[1]);
                            int y = Integer.parseInt(cmds[2]);
                            int z = Integer.parseInt(cmds[3]);
                            
                            killTree(player, x, y, z);
                        }
                        catch (Exception e)
                        {
                            player.addChatMessage("X, Y and Z must be valid numbers.");
                        }
                        
                    }
                    else if (cmds.length == 2 && cmds[1].equals("here"))
                    {
                        killTree(player, (int) player.posX, (int) player.posY - 1, (int) player.posZ);
                    }
                    else
                    {
                        player.addChatMessage("Incorrect format. /ebxl killtree <x> <y> <z>");
                    }
                }
                else if (cmds[0].equals("saplingdespawntime"))
                {
                    if (cmds.length == 1)
                    {
                        player.addChatMessage("Sapling despawn time is currently: " + Integer.toString(BlockCustomSapling.getSaplingLifespan()) + " ticks");
                    }
                    else if (cmds.length == 2)
                    {
                        int newTime = Integer.parseInt(cmds[1]);
                        
                        if (newTime >= 0 && newTime <= 10000)
                        {
                            BlockCustomSapling.setSaplingLifespan(newTime);
                            BlockNewSapling.setSaplingLifespan(newTime);
                            player.addChatMessage("Sapling despawn time set to: " + cmds[1] + " ticks");
                        }
                        else
                        {
                            player.addChatMessage("Sapling despawn time must be between 0 and 10000.");
                        }
                    }
                    else
                    {
                        player.addChatMessage("Incorrect format. /ebxl saplingDespawn [newtime]");
                    }
                }
                else if (cmds[0].equals("lastseed"))
                {
                    if (cmds.length == 1)
                    {
                        treeNames(player);
                    }
                    else
                    {
                        if (cmds[1].equals("acacia"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenAcacia.getLastSeed()));
                        }
                        else if (cmds[1].equals("cypress"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenCypressTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("baldcypress"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenBaldCypressTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("rainbow"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenRainbowEucalyptusTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("japanesemaple"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenJapaneseMapleTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("japanesemapleshrub"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenJapaneseMapleShrub.getLastSeed()));
                        }
                        else if (cmds[1].equals("fir"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenFirTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("redwood"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenRedwood.getLastSeed()));
                        }
                        else if (cmds[1].equals("largefir"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenFirTreeHuge.getLastSeed()));
                        }
                        else if (cmds[1].equals("brown"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenAutumnTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("orange"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenAutumnTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("red"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenAutumnTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("yellow"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenAutumnTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("largebrown"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenBigAutumnTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("largeorange"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenBigAutumnTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("largered"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenBigAutumnTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("largeyellow"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenBigAutumnTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("baldcypress"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenBaldCypressTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("sakura"))
                        {
                            player.addChatMessage("The last seed used was: " + Long.toString(WorldGenSakuraBlossomTree.getLastSeed()));
                        }
                        else if (cmds[1].equals("legend"))
                        {
                            player.addChatMessage("The Legend Oak does not currently support seeding.");
                        }
                        else
                        {
                            treeNames(player);
                        }
                    }
                }
                else if (cmds[0].equals("spawntree"))
                {
                    if (cmds.length == 5)
                    {
                        try
                        {
                            int x = Integer.parseInt(cmds[2]);
                            int y = Integer.parseInt(cmds[3]);
                            int z = Integer.parseInt(cmds[4]);
                            
                            if (cmds[1].equals("acacia"))
                            {
                                (new WorldGenAcacia(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("cypress"))
                            {
                                (new WorldGenCypressTree(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("baldcypress"))
                            {
                                (new WorldGenBaldCypressTree(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("rainbow"))
                            {
                                (new WorldGenRainbowEucalyptusTree(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("japanesemaple"))
                            {
                                (new WorldGenJapaneseMapleTree(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("japanesemapleshrub"))
                            {
                                (new WorldGenJapaneseMapleShrub(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("fir"))
                            {
                                (new WorldGenFirTree(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("redwood"))
                            {
                                (new WorldGenRedwood(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("largeFir"))
                            {
                                (new WorldGenFirTreeHuge(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("brown"))
                            {
                                WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.BROWN);
                                WorldGenAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("orange"))
                            {
                                WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.ORANGE);
                                WorldGenAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("red"))
                            {
                                WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.PURPLE);
                                WorldGenAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("yellow"))
                            {
                                WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.YELLOW);
                                WorldGenAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("largebrown"))
                            {
                                WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(true, AutumnTreeType.BROWN);
                                WorldGenBigAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("largeorange"))
                            {
                                WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(true, AutumnTreeType.ORANGE);
                                WorldGenBigAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("largered"))
                            {
                                WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(true, AutumnTreeType.PURPLE);
                                WorldGenBigAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("largeyellow"))
                            {
                                WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(true, AutumnTreeType.YELLOW);
                                WorldGenBigAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("sakura"))
                            {
                                (new WorldGenSakuraBlossomTree(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else if (cmds[1].equals("legend"))
                            {
                                (new WorldGenLegendOak(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                            }
                            else
                            {
                                treeNames(player);
                            }
                        }
                        catch (Exception e)
                        {
                            player.addChatMessage("X, Y and Z must be valid numbers.");
                        }
                    }
                    else if (cmds.length == 6)
                    {
                        try
                        {
                            int x = Integer.parseInt(cmds[2]);
                            int y = Integer.parseInt(cmds[3]);
                            int z = Integer.parseInt(cmds[4]);
                            long seed = Long.parseLong(cmds[5]);
                            
                            if (cmds[1].equals("acacia"))
                            {
                                (new WorldGenAcacia(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("cypress"))
                            {
                                (new WorldGenCypressTree(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("baldcypress"))
                            {
                                (new WorldGenBaldCypressTree(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("rainbow"))
                            {
                                (new WorldGenRainbowEucalyptusTree(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("japanesemaple"))
                            {
                                (new WorldGenJapaneseMapleTree(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("japanesemapleshrub"))
                            {
                                (new WorldGenJapaneseMapleShrub(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("fir"))
                            {
                                (new WorldGenFirTree(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("redwood"))
                            {
                                (new WorldGenRedwood(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("largeFir"))
                            {
                                (new WorldGenFirTreeHuge(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("brown"))
                            {
                                WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.BROWN);
                                WorldGenAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("orange"))
                            {
                                WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.ORANGE);
                                WorldGenAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("red"))
                            {
                                WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.PURPLE);
                                WorldGenAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("yellow"))
                            {
                                WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.YELLOW);
                                WorldGenAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("largeBrown"))
                            {
                                WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(true, AutumnTreeType.BROWN);
                                WorldGenBigAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("largeorange"))
                            {
                                WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(true, AutumnTreeType.ORANGE);
                                WorldGenBigAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("largered"))
                            {
                                WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(true, AutumnTreeType.PURPLE);
                                WorldGenBigAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("largeyellow"))
                            {
                                WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(true, AutumnTreeType.YELLOW);
                                WorldGenBigAutumnTree.setTrunkBlock(Element.LOG_AUTUMN.get().itemID, Element.LOG_AUTUMN.get().getItemDamage());
                                tree.generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("sakura"))
                            {
                                (new WorldGenSakuraBlossomTree(true)).generate(player.worldObj, seed, x, y, z);
                            }
                            else if (cmds[1].equals("legend"))
                            {
                                (new WorldGenLegendOak(true)).generate(player.worldObj, player.worldObj.rand, x, y, z);
                                player.addChatMessage("The Legend Oak does not currently support seeding.");
                            }
                            else
                            {
                                treeNames(player);
                            }
                        }
                        catch (Exception e)
                        {
                            player.addChatMessage("X, Y, Z and seed must be valid numbers.");
                        }
                    }
                    else
                    {
                        player.addChatMessage("Incorrect format. /ebxl spawntree <treetype> <x> <y> <z> [seed]");
                    }
                }
                else
                {
                    player.addChatMessage("\"/ebxl " + cmds[0] + "\" is not a valid command.");
                    player.addChatMessage("Use \"/ebxl help\" for the list of valid commands.");
                }
            }
        }
        
    }
    
    private void treeNames(EntityPlayer player)
    {
        player.addChatMessage("Only the following tree names are supported:");
        player.addChatMessage("acacia, cypress, fir, redwood, largeFir,");
        player.addChatMessage("brown, orange, red, yellow, largebrown,");
        player.addChatMessage("largeorange, largered, largeyellow, baldcypress,");
        player.addChatMessage("rainbow, japanesemaple, japanesemaple, japanesemapleshrub,");
        player.addChatMessage("sakura, legend");
    }
    
    private boolean killTree(EntityPlayer player, int x, int y, int z)
    {
        Queue<Vector3> killList = new LinkedList<Vector3>();
        
        killList.add(new Vector3(x, y, z));
        Vector3 currentBlock;
        
        while (killList.size() > 0)
        {
            currentBlock = killList.remove();
            int blockId = player.worldObj.getBlockId(currentBlock.x(), currentBlock.y(), currentBlock.z());
            int damage = player.worldObj.getBlockId(currentBlock.x(), currentBlock.y(), currentBlock.z());
            String blockType = OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(blockId, 1, damage)));
            
            // shorten the coords
            int x1 = currentBlock.x();
            int y1 = currentBlock.y();
            int z1 = currentBlock.z();
            
            if (blockType.equals("logWood") || blockType.equals("treeLeaves"))
            {
                // Add extra blocks to the list
                killList.add(new Vector3(x1, y1 + 1, z1));
                killList.add(new Vector3(x1, y1 - 1, z1));
                killList.add(new Vector3(x1 + 1, y1, z1));
                killList.add(new Vector3(x1 - 1, y1, z1));
                killList.add(new Vector3(x1, y1, z1 + 1));
                killList.add(new Vector3(x1, y1, z1 - 1));
                
                // Remove the block
                player.worldObj.setBlockToAir(x1, y1, z1);
            }
        }
        
        return true;
    }
    
    private void helpList(EntityPlayer player)
    {
        // List the available commands
        player.addChatMessage("\u00A72-ExtrabiomesXl Commands-\u00A7r");
        player.addChatMessage("/ebxl help [command]");
        player.addChatMessage("/ebxl lastseed <treetype>");
        player.addChatMessage("/ebxl killtree <x> <y> <z>");
        player.addChatMessage("/ebxl saplingdespawntime [ticks]");
        player.addChatMessage("/ebxl spawntree <treetype> <x> <y> <z> [seed]");
        player.addChatMessage("/ebxl version");
    }
}
