SUMMARY = "Async UPnP Client for Python"
HOMEPAGE = "https://github.com/StevenLooman/async_upnp_client"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=f4eda51018051de136d3b3742e9a7a40"

inherit pypi setuptools3

PYPI_ARCHIVE_NAME = "async_upnp_client-${PV}.${PYPI_PACKAGE_EXT}"

S = "${WORKDIR}/async_upnp_client-${PV}"

SRC_URI[sha256sum] = "0ba5d0b2560ed81b9cbb2960ecef20a9b560d65d75b36f9ccfe4ac0090c10323"

RDEPENDS:${PN} = "\
	python3-core (>=3.8) \
	python3-async-timeout (>=3.0.0) \
	python3-aiohttp (>=3.8.1) \
	python3-python-didl-lite (=1.3.2) \
	python3-defusedxml (>=0.6.0) \
	python3-voluptuous (>=0.12.1) \
"
