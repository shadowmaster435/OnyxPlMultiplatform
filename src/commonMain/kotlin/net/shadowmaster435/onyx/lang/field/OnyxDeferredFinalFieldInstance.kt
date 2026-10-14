package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl

class OnyxDeferredFinalFieldInstance(
    desc: OnyxFieldImpl,
    referenceIndex: Int,
): OnyxFieldInstance(desc, referenceIndex, true, true)