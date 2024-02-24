SUMMARY = "A minimal HTTP client"
HOMEPAGE = "https://github.com/encode/httpcore"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=1c1f23b073da202e1f4f9e426490210c"

DEPENDS += "\
    python3-hatch-fancy-pypi-readme-native \
"

SRC_URI[sha256sum] = "13b5e5cd1dca1a6636a6aaea212b19f4f85cd88c366a2b82304181b769aab3c9"

inherit pypi python_hatchling

RDEPENDS:${PN} = "\
    python3-anyio (>=3.0) \
    python3-certifi \
    python3-h11 (>=0.13) \
    python3-sniffio (>=1.0.0) \
"
