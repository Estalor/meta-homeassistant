SUMMARY = "Open-source home automation platform running on Python 3"
HOMEPAGE = "https://home-assistant.io/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=86d3f3a95c324c9479bd8986968f4327"

HOMEASSISTANT_CONFIG_DIR ?= "${localstatedir}/lib/homeassistant"
HOMEASSISTANT_CONFIG_DIR[doc] = "Configuration directory used by home-assistant."
HOMEASSISTANT_USER ?= "homeassistant"
HOMEASSISTANT_USER[doc] = "User the home-assistent service runs as."

SRC_URI = "\
    git://github.com/home-assistant/core.git;protocol=https;branch=master \
    file://homeassistant.service \
    file://0001-Allow-newer-version-of-setuptools.patch \
    file://run-ptest \
"
SRC_URI[sha256sum] = "f4181f4023feb78cef0be655234200966daa140aea4634dbf3def8b18fd21d48"
SRCREV = "2f476684224fffdbf216b469d60f6803c84e8e0a"

inherit python_setuptools_build_meta useradd systemd ptest

S = "${WORKDIR}/git"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "homeassistant"
USERADD_PARAM:${PN} = "\
    --system --home ${HOMEASSISTANT_CONFIG_DIR} \
    --no-create-home --shell /bin/false \
    --groups homeassistant,dialout --gid homeassistant ${HOMEASSISTANT_USER} \
"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "homeassistant.service"

do_install:append () {
    install -d -o ${HOMEASSISTANT_USER} -g homeassistant ${D}${HOMEASSISTANT_CONFIG_DIR}

    # Install systemd unit files and set correct config directory
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/homeassistant.service ${D}${systemd_unitdir}/system
    sed -i -e 's,@HOMEASSISTANT_CONFIG_DIR@,${HOMEASSISTANT_CONFIG_DIR},g' ${D}${systemd_unitdir}/system/homeassistant.service
    sed -i -e 's,@HOMEASSISTANT_USER@,${HOMEASSISTANT_USER},g' ${D}${systemd_unitdir}/system/homeassistant.service
}

# Home Assistant core
# Home Assistant contains thousands of integrations. These are subdivided in their own include file bases on the same division as HA itself uses
# at https://www.home-assistant.io/integrations/

require recipes-homeassistant/homeassistant/include/3d-printing.inc
require recipes-homeassistant/homeassistant/include/hacs.inc
require recipes-homeassistant/homeassistant/include/history.inc
require recipes-homeassistant/homeassistant/include/hub.inc
require recipes-homeassistant/homeassistant/include/image-processing.inc
require recipes-homeassistant/homeassistant/include/media-player.inc
require recipes-homeassistant/homeassistant/include/network.inc
require recipes-homeassistant/homeassistant/include/other.inc
require recipes-homeassistant/homeassistant/include/sensor.inc
require recipes-homeassistant/homeassistant/include/switch.inc
require recipes-homeassistant/homeassistant/include/system-monitor.inc
require recipes-homeassistant/homeassistant/include/text-to-speech.inc
require recipes-homeassistant/homeassistant/include/utility.inc
require recipes-homeassistant/homeassistant/include/voice.inc
require recipes-homeassistant/homeassistant/include/weather.inc

# There are two exceptions:
# - any integration which has multiple categories are grouped in multiple.inc
# - any integration which has no category assigned to it is in none.inc

require recipes-homeassistant/homeassistant/include/multiple.inc
require recipes-homeassistant/homeassistant/include/none.inc

RDEPENDS:${PN} += "\
    python3-aiodns (>=3.2.0) \
    python3-aiohttp (>=3.9.5) \
    python3-aiohttp-cors (=0.7.0) \
    python3-aiohttp-session (=2.12.0) \
    python3-aiohttp-fast-url-dispatcher (=0.3.0) \
    python3-aiohttp-isal (=0.2.0) \
    python3-astral (=2.2) \
    python3-async-interrupt (=1.1.1) \
    python3-attrs (>=23.2.0) \
    ${@bb.utils.contains("DISTRO_FEATURES", "ptest", "python3-atomicwrites", "python3-atomicwrites-homeassistant (=1.4.1)",d)} \
    python3-awesomeversion (>=24.2.0) \
    python3-bcrypt (>=4.1.2) \
    python3-certifi (>=2021.5.30) \
    python3-ciso8601 (=2.3.1) \
    python3-fnv-hash-fast (=0.5.0) \
    python3-hass-nabucasa (=0.78.0) \
    python3-httpx (>=0.27.0) \
    python3-home-assistant-bluetooth (=1.12.0) \
    python3-ifaddr (=0.2.0) \
    python3-jinja2 (>=3.1.3) \
    python3-lru-dict (>=1.3.0) \
    python3-pyjwt (=2.8.0) \
    python3-cryptography (>=42.0.5) \
    python3-pillow (>=10.3.0) \
    python3-pyopenssl (>=24.1.0) \
    python3-orjson (>=3.9.15) \
    python3-packaging (>=23.1) \
    python3-pip (>=21.3.1) \
    python3-psutil-home-assistant (=0.0.1) \
    python3-python-slugify (=8.0.4) \
    python3-pyyaml (=6.0.1) \
    python3-requests (=2.31.0) \
    python3-sqlalchemy (=2.0.29) \
    python3-typing-extensions (>=4.11.0) \
    python3-ulid-transform (=0.9.0) \
    python3-urllib3 (>=1.26.5) \
    python3-voluptuous (=0.13.1) \
    python3-voluptuous-serialize (=2.6.0) \
    python3-yarl (>=1.9.4) \
    \
    python3-statistics \
    python3-core (>=3.12.0) \
"

RDEPENDS:${PN}-ptest = "\
    python3-freezegun \
    python3-pytest \
    python3-pytest-asyncio \
    python3-pytest-cov \
    python3-pytest-socket \
    python3-pytest-timeout \
    python3-requests-mock \
    python3-syrupy \
    python3-unittest-automake-output \
"

do_install_ptest() {
    install -d ${D}${PTEST_PATH}/tests
    install ${S}/pyproject.toml ${D}${PTEST_PATH}/tests/
    cp -rf ${S}/tests/* ${D}${PTEST_PATH}/tests/
}
