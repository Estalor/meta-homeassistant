DESCRIPTION = "Intent parsing for Home Assistant"
HOMEPAGE = "https://github.com/home-assistant/hassil"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=86d3f3a95c324c9479bd8986968f4327"

inherit pypi python_setuptools_build_meta

SRC_URI[sha256sum] = "f215162a5bc34f6362cd3f8418c608826d3a7b641715677573ec96d15e431aa0"

RDEPENDS:${PN} = "\
    python3-pyyaml (>=6.0) \
    python3-unicode-rbnf (=1.1.0) \
"
