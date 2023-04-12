SUMMARY = "A cross platform Bluetooth Low Energy Client for Python using asyncio"
HOMEPAGE = "https://github.com/hbldh/bleak"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bcbc2069a86cba1b5e47253679f66ed7"

SRC_URI[md5sum] = "d038483ec145c3d32c1ef03e04c42621"
SRC_URI[sha256sum] = "87845a96453c58c19031c735444a7b3156800534bcd3f23ba74e119e9ae3cd88"

inherit pypi python_setuptools_build_meta python_poetry_core

# Upstream typing-extensions version = 4.5
RDEPENDS:${PN} = "\
    ${PYTHON_PN}-async-timeout (>=3.0.0) \
    ${PYTHON_PN}-typing-extensions (>=4.2.0) \
    ${PYTHON_PN}-dbus-fast (>=1.22.0) \
"
