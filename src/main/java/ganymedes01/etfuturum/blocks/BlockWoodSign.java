package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockWoodSign extends BlockSign {

	protected static BlockWoodSign prevSign;
	private BlockWoodSign wallSign;
	private final BlockWoodSign standingSign;

	private final Block baseBlock;
	private final int meta;
	public final String type;
	public final String signTexture;
	public final boolean standing;

	String mod;
	String item;

	public BlockWoodSign(Class<? extends TileEntity> p_i45426_1_, boolean p_i45426_2_, String type, Block block, int meta) {
		super(p_i45426_1_, p_i45426_2_);
		this.meta = meta;
		this.type = type;
		baseBlock = block;
		if(type.split("_")[0].equals("bop")) {
			mod = "biomesoplenty:";
			item = type.substring(4, type.length());
		} else {
			mod = "";
			item = type;
		}
		this.signTexture = mod + "textures/entity/sign/" + item;
		standing = p_i45426_2_;
		if (standing) {
			prevSign = this;
			standingSign = this;
		} else {
			standingSign = prevSign;
			wallSign = this;
			prevSign.wallSign = this;
		}
		setHardness(1.0F);
		disableStats();
		setBlockName(Utils.getUnlocalisedName(type + "_sign"));
		if (type.equals("crimson") || type.equals("warped")) {
			Utils.setBlockSound(this, ModSounds.soundNetherWood);
		} else if (type.equals("cherry")) {
			Utils.setBlockSound(this, ModSounds.soundCherryWood);
		} else if (type.equals("bamboo")) {
			Utils.setBlockSound(this, ModSounds.soundBambooWood);
		} else {
			setStepSound(Block.soundTypeWood);
		}
		if (block != Blocks.planks && standing) { //Only apply this logic to new signs; old ones use a separate item.
			setCreativeTab(EtFuturum.creativeTabBlocks);
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return baseBlock.getIcon(side, this.meta);
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		if (baseBlock == Blocks.planks) {
			return ModItems.OLD_SIGN_ITEMS[this.meta - 1].get();
		}
		//Only apply this logic to new signs; old ones use a separate item.
		return Item.getItemFromBlock(standingSign);
	}


	@Override
	public Item getItem(World worldIn, int x, int y, int z) {
		return getItemDropped(0, null, 0);
	}

	public BlockWoodSign getWallSign() {
		return wallSign;
	}

	@Override
	public String getItemIconName() {
		return mod + item + "_sign";
	}
}
