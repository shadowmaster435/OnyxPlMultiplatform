package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock

interface OnyxCodeBlockContainer {
    fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit)

    fun setCodeBlockParents(parent: OnyxCodeBlock) = forEachBlock { it.parent = parent }

}