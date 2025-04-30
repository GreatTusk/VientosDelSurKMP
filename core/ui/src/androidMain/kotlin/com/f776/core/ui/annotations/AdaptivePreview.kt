package com.f776.core.ui.annotations

import androidx.compose.ui.tooling.preview.Preview

@UberPreview
@Preview(name = "landscape", device = "spec:width=640dp,height=360dp,dpi=480")
@Preview(name = "foldable", device = "spec:width=673dp,height=841dp,dpi=480")
@Preview(name = "tablet", device = "spec:width=1280dp,height=800dp,dpi=480")
@Preview(name = "desktop", device = "spec:width=1920dp,height=1080dp,dpi=160", showSystemUi = true)
annotation class AdaptivePreview
