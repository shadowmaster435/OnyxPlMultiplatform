package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl

class OnyxDeferredMutableFieldInstance(
    desc: OnyxFieldImpl,
    referenceIndex: Int,
): OnyxFieldInstance(desc, referenceIndex, deferred = true)