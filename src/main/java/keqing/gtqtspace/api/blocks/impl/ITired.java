package keqing.gtqtspace.api.blocks.impl;

import net.minecraft.util.IStringSerializable;

public interface ITired extends IStringSerializable {

	default Object getInfo() {
		return null;
	}

	default String getTire() {
		return this.getName();
	}
}